package liang.don.dzimageconverter

import format.{PngFormat, JpegFormat}

/**
 * Main class for the image io application.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
object ImageConverterMain {

  def main(args: Array[String]) {
    if (args.length != 2) {
      showHelp()
      sys.exit()
    }

    val fileFormat = args(1).toLowerCase
    if (!isSupportedImageFormat(fileFormat)) {
      showHelp()
      sys.exit()
    }

    new ImageConverterImpl().convert(args(0), fileFormat)
  }

  private def isSupportedImageFormat(fileFormat: String): Boolean = {
    (fileFormat == JpegFormat.FileFormat || fileFormat == PngFormat.FileFormat)
  }

  private def showHelp() {
    println("Invalid or insufficient arguments provided.")
    println("Usage: ImageConverterMain ImageURL FileFormat")
    println("Options:")
    println("ImageURL - The location of the image")
    println("FileFormat - The image format. Acceptable values: jpg, png")
  }

}
