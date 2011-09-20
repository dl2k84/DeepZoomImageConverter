package liang.don.dzimageconverter.io

/**
 * Retrieves an image from supported
 * HTTP, HTTPS, or FILE protocol scheme.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
abstract class ImageFetcher {

  /**
   * Fetches an image.
   *
   * @param uri The location of the image (HTTP, HTTPS, FILE etc).
   * @return The image of type depending on the build language:<br>
   *          Java: java.awt.image.BufferedImage<br>
   *          .NET(C#): System.Drawing.Bitmap<br>
   */
  def fetchImage(uri: String): AnyRef

  /**
   * Returns the image size as (width, height).
   *
   * @return The image size as (width, height)
   */
  def getImageSize(image: AnyRef): (Int, Int)

  /**
   * Writes the image to the target outputStream.
   *
   * @param image The image to write out.Depending on build language, the type is:<br>
   *               Java: java.awt.image.BufferedImage<br>
   *               .NET(C#): System.Drawing.Bitmap<br>
   * @param fileFormat The file format.
   * @param outputStream Where to write out the image.
   */
  def saveImage(image: AnyRef, fileFormat: String, outputStream: AnyRef)

}
