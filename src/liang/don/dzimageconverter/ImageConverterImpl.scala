package liang.don.dzimageconverter

import config.{FileProperties, ConverterProperties}
import format.{PngFormat, JpegFormat}
import io.TileFactory
import xml.XML

/**
 * Convert images to the Microsoft Deep Zoom format.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
class ImageConverterImpl extends ImageConverter {

  private val imageFetcher = TileFactory.getImageFetcher
  private val imageWrapper = TileFactory.getImageWrapper
  private val tileWriter = TileFactory.getTileWriter

  override def convert(uri: String) {
    val sourceImage = imageFetcher.fetchImage(uri)
    val fileFormat = {
      val fileExtensionIndex = uri.lastIndexOf(".")
      if (fileExtensionIndex == -1 || fileExtensionIndex < uri.lastIndexOf("/")) {
        sys.error("[" + getClass.getName + "#convert] Could not find a valid file extension for this image.")
      }
      uri.substring(fileExtensionIndex + 1)
    }
    // TODO Make this file format verification work.
//    val imageHeader: Array[Int] = {
//      // Of the supported image formats, 8 bytes (PNG) is the largest header so
//      // reading a max of 8 bytes.
//      val a1 = Array.ofDim[Byte](8)
//      val baos = new ByteArrayOutputStream
//      imageFetcher.saveImage(sourceImage, fileFormat, baos)
//      val bais = new ByteArrayInputStream(baos.toByteArray)
//      bais.read(a1, 0, a1.length)
////      baos.write(a1, 0, a1.length)
//      val a2 = Array.ofDim[Int](8)
//      a1.zipWithIndex.foreach(f => a2(f._2) = f._1)
//      a2
//    }

//    if (isCorrectImageFormat(imageHeader, fileFormat)) {
    outputTiles(sourceImage, fileFormat, uri)
    // TODO call outputCollectionDescriptor for collection of images.
    val imageSize = imageWrapper.getImageSize(sourceImage)
    val width = imageSize._1
    val height = imageSize._2
    outputSingleDescriptor(uri, fileFormat, width, height)
//    }
  }

  override def convert(uriCollection: Array[String]) {
    // TODO
    sys.error("[" + getClass.getName + "#convert] Not implemented.")
  }

  /**
   * Verifies the magic number in the header matches the specified file format.
   *
   * @param header Byte header containing the magic number.
   * @param fileFormat The image format to verify against.
   *
   * @return True if the magic number in the header matches
   *          the specified file format, else false.
   */
  private def isCorrectImageFormat(header: Array[Int], fileFormat: String): Boolean = {
    if (JpegFormat.FileFormat == fileFormat) {
      JpegFormat.isEqual(header)
    } else if (PngFormat.FileFormat == fileFormat) {
      PngFormat.isEqual(header)
    } else {
      // Should not reach here.
//      sys.error("[" + getClass.getName + "#isCorrectImageFormat] Unknown image format.")
      false
    }
  }

  private def outputTiles(sourceImage: AnyRef, fileFormat: String, originalFilename: String) {
    val imageSize = imageWrapper.getImageSize(sourceImage)
    val width = imageSize._1
    val height = imageSize._2
    val maxZoomLevel = calculateMaximumZoomLevel(width, height)

    val preservaAlpha = if (PngFormat.FileFormat == fileFormat) true else false
    var image = sourceImage
    // for loop from maxZoomLevel to 0 calling tileWriter.writeTiles(sourceImage)
    for (level <- maxZoomLevel to 0 by -1) {
      tileWriter.writeTiles(image, fileFormat, originalFilename)
      if (-1 < level) {
        image = imageWrapper.scaleImage(image, 0.5, preservaAlpha)
      }
    }
    // TODO
  }

  /**
   * Writes out the XML descriptor for the converted Deep Zoom image.
   *
   * @param originalFilename The image's original filename.
   * @param fileFormat The image's file format.
   * @param width The original image's width.
   * @param height The original image's height.
   */
  private def outputSingleDescriptor(originalFilename: String, fileFormat: String, width: Int, height: Int) {
    val descriptorFilename = originalFilename.substring(originalFilename.lastIndexOf("/") + 1) + ".xml"
    val xmlDoc =
      <Image TileSize={ConverterProperties.tileSize.toString}
        Overlap={ConverterProperties.overlapSize.toString}
        Format={fileFormat}
        ServerFormat={"Default"}
        xmlns={FileProperties.deepZoomNamespace}>
        <Size Width={width.toString}
          Height={height.toString} />
      </Image>

    XML.save(descriptorFilename, xml.Utility.trim(xmlDoc), "utf-8", true, null)
  }

  private def outputCollectionDescriptor(originalFilename: String) {
    // TODO
  }

  // TODO Below methods copied from liang.don.dzviewer.ImageFetcher of the
  // DeepZoomViewer project. Once I have refactored out that project,
  // referencing that library would be ideal but for now, copy/pasting it here for convenience.
  def calculateMaximumZoomLevel(width: Int, height: Int): Int = {
    math.ceil(math.log(math.max(width, height)) / math.log(2)).toInt
  }
}
