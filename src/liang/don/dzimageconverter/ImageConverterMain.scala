package liang.don.dzimageconverter

/**
 * Main class for the image io application.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object ImageConverterMain {

  def main(args: Array[String]) {
    if (args.length < 1) {
      showHelp()
      sys.exit()
    }

    new ImageConverterImpl().convert(args)
  }

  private def showHelp() {
    println("Invalid or insufficient arguments provided.")
    println("Usage: ImageConverterMain [ImageURL]")
    println("Options:")
    println("ImageURL - The location of the image. This can be a single image location, an arbitrary list of URI(s) or a directory containing the images to convert.")
  }

}
