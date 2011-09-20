package liang.don.dzimageconverter

import config.ConverterProperties
import format.{PngFormat, JpegFormat}
import io.TileFactory
import java.io.ByteArrayOutputStream

/**
 * Convert images to the Microsoft Deep Zoom format.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
class ImageConverterImpl extends ImageConverter {

  private val imageFetcher = TileFactory.getImageFetcher
  private val tileReader = TileFactory.getTileReader
  private val tileWriter = TileFactory.getTileWriter

  override def convert(uri: String, fileFormat: String) {
    val sourceImage = imageFetcher.fetchImage(uri)
    val imageHeader: Array[Int] = {
      // Of the supported image formats, 8 bytes (PNG) is the largest header so
      // reading a max of 8 bytes.
      val a1 = Array.ofDim[Byte](8)
      val baos = new ByteArrayOutputStream
      imageFetcher.saveImage(sourceImage, fileFormat, baos)
      baos.write(a1, 0, a1.length)
      val a2 = Array.ofDim[Int](8)
      a1.zipWithIndex.foreach(f => a2(f._2) = f._1)
      a2
    }

    if (isCorrectImageFormat(imageHeader, fileFormat)) {
      outputTiles(sourceImage)
    }
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

  private def outputTiles(sourceImage: AnyRef) {
    val imageSize = imageFetcher.getImageSize(sourceImage)
    val width = imageSize._1
    val height = imageSize._2
    val maxZoomLevel = calculateMaximumZoomLevel(width, height)
    val maxCols = math.ceil(width / ConverterProperties.tileSize.toDouble).toInt
    val maxRows = math.ceil(height / ConverterProperties.tileSize.toDouble).toInt
    // TODO
  }


  // TODO Below methods copied from liang.don.dzviewer.ImageFetcher of the
  // DeepZoomViewer project. Once I have refactored out that project,
  // referencing that library would be ideal but for now, copy/pasting it here for convenience.
  def calculateMaximumZoomLevel(width: Int, height: Int): Int = {
    math.ceil(math.log(math.max(width, height)) / math.log(2)).toInt
  }
}
