package liang.don.dzimageconverter

import config.ConverterProperties.OutputType
import config.{FileProperties, ConverterProperties}
import format.{PngFormat, JpegFormat}
import io.TileFactory
import java.io.{File, FilenameFilter}
import collection.mutable.ArrayBuffer
import log.Logger
import xml.{Elem, XML}

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
    val fileFormat = {
      val fileExtensionIndex = uri.lastIndexOf(".")
      if (fileExtensionIndex == -1 || fileExtensionIndex < uri.lastIndexOf(File.separator)) {
        sys.error("[" + getClass.getName + "#convert] Could not find a valid file extension for this image.")
      }
      uri.substring(fileExtensionIndex + 1)
    }
    val sourceImage = imageFetcher.fetchImage(uri)
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
    Logger.instance.log("Converting image: " + uri + " ...")
    outputTiles(sourceImage, fileFormat, uri)
    // TODO call outputCollectionDescriptor for collection of images.
    val imageSize = imageWrapper.getImageSize(sourceImage)
    val width = imageSize._1
    val height = imageSize._2

    outputSingleDescriptor(uri, fileFormat, width, height)
//    }
  }

  override def convert(uriCollection: Array[String]) {
    // TODO Platform independent impl (currently relies on java.io)
    val jpgFilter = getFilenameFilter(JpegFormat.FileFormat)
    val pngFilter = getFilenameFilter(PngFormat.FileFormat)
    val fileCollection = new ArrayBuffer[String]

    uriCollection.foreach {
      uri => {
        val f = new File(uri)
        if (f.exists) {
          if (f.isFile) {
            // Treat as single file
            fileCollection += getFilename(uri)
            convert(uri)
          } else {
            // Treat as directory
            f.listFiles(jpgFilter).foreach(file => if(file.isFile) { fileCollection += getFilename(file.getCanonicalPath); convert(file.getCanonicalPath) })
            f.listFiles(pngFilter).foreach(file => if(file.isFile) { fileCollection += getFilename(file.getCanonicalPath); convert(file.getCanonicalPath) })
          }
        } else {
          sys.error("[" + getClass.getName + "#convert] " + f + " does not exist.")
        }
      }
    }

    val outputType = ConverterProperties.outputType
    if (OutputType.Collection == outputType) {
      outputCollectionDescriptor(fileCollection.toArray)
    }
  }

  private def getFilenameFilter(fileFormat: String): FilenameFilter = {
    new FilenameFilter {
      def accept(dir: File, name: String): Boolean = {
        name.toLowerCase.endsWith("." + fileFormat)
      }
    }
  }

//  /**
//   * Verifies the magic number in the header matches the specified file format.
//   *
//   * @param header Byte header containing the magic number.
//   * @param fileFormat The image format to verify against.
//   *
//   * @return True if the magic number in the header matches
//   *          the specified file format, else false.
//   */
//  private def isCorrectImageFormat(header: Array[Int], fileFormat: String): Boolean = {
//    if (JpegFormat.FileFormat == fileFormat) {
//      JpegFormat.isEqual(header)
//    } else if (PngFormat.FileFormat == fileFormat) {
//      PngFormat.isEqual(header)
//    } else {
//      // Should not reach here.
//      sys.error("[" + getClass.getName + "#isCorrectImageFormat] Unknown image format.")
//      false
//    }
//  }

  private def outputTiles(sourceImage: AnyRef, fileFormat: String, originalFilename: String) {
    val imageSize = imageWrapper.getImageSize(sourceImage)
    val width = imageSize._1
    val height = imageSize._2
    val maxZoomLevel = calculateMaximumZoomLevel(width, height)

    val preserveAlpha = (PngFormat.FileFormat == fileFormat)
    var image = sourceImage
    // for loop from maxZoomLevel to 0 calling tileWriter.writeTiles(sourceImage)
    for (level <- maxZoomLevel to 0 by -1) {
      tileWriter.writeTiles(image, fileFormat, originalFilename)
      if (-1 < level) {
        image = imageWrapper.scaleImage(image, 0.5, preserveAlpha)
      }
    }
    // TODO
  }

  /**
   * Writes out the XML descriptor for the converted Deep Zoom image.
   *
   * @param uri The image's original filename (with absolute path where applicable).
   * @param fileFormat The image's file format.
   * @param width The original image's width.
   * @param height The original image's height.
   */
  private def outputSingleDescriptor(uri: String, fileFormat: String, width: Int, height: Int) {
    val descriptorFilename = getFilename(uri) + ".xml"
    val xmlDoc =
      <Image TileSize={ConverterProperties.tileSize.toString}
        Overlap={ConverterProperties.overlapSize.toString}
        Format={fileFormat}
        ServerFormat={"Default"}
        xmlns={FileProperties.deepZoomNamespace}>
        <Size Width={width.toString}
          Height={height.toString} />
      </Image>

    XML.save(FileProperties.baseDirectory + File.separator + descriptorFilename, xml.Utility.trim(xmlDoc), "utf-8", true, null)
  }

  /**
   * Writes out the collection XML descriptor for the converted Deep Zoom image(s).
   *
   * @param fileCollection Collection of files to generate Image Collection details from.
   */
  private def outputCollectionDescriptor(fileCollection: Array[String]) {
    val fileFormat = JpegFormat.FileFormat // TODO Decide what to do with collections of mixed jpg/png images.
    val xmlDoc =
      <Collection MaxLevel={"8"/*TODO*/}
        TileSize={ConverterProperties.tileSize.toString}
        Format={fileFormat}
        NextItemId={fileCollection.length.toString}
        ServerFormat={"Default"}
        xmlns={FileProperties.deepZoomNamespace}>
        <Items>
        {
          generateImageCollectionXml(fileCollection)
        }
        </Items>
      </Collection>

    XML.save(FileProperties.collectionDescriptorFilename, xml.Utility.trim(xmlDoc), "utf-8", true, null)
  }

  /**
   * Outputs the Image Collection Xml.
   *
   * @param fileCollection Collection of files to generate Image Collection details from.
   */
  private def generateImageCollectionXml(fileCollection: Array[String]): Array[Elem] = {
    val imageCollection = new ArrayBuffer[Elem]
    fileCollection.zipWithIndex.foreach {
      file => {
        val fileDescriptor = file._1 + ".xml"
        val doc = XML.loadFile(FileProperties.baseDirectory + File.separator + fileDescriptor)
        imageCollection +=
          <I Id={file._2.toString}
            N={file._2.toString}
            Source={FileProperties.baseDirectory + File.separator + fileDescriptor}>
            { doc \\ "Size" }
          </I>
      }
    }
    imageCollection.toArray
  }

  /**
   * Gets the filename from the specified absolute path.
   *
   * @param path Filename with absolute path.
   */
  private def getFilename(path: String): String = {
    path.substring(path.lastIndexOf(File.separator) + 1)
  }

  // TODO Below methods copied from liang.don.dzviewer.ImageFetcher of the
  // DeepZoomViewer project. Once I have refactored out that project,
  // referencing that library would be ideal but for now, copy/pasting it here for convenience.
  def calculateMaximumZoomLevel(width: Int, height: Int): Int = {
    math.ceil(math.log(math.max(width, height)) / math.log(2)).toInt
  }
}
