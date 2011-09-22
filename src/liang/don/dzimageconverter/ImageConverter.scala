package liang.don.dzimageconverter

/**
 * Convert images to the Microsoft Deep Zoom format.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
abstract class ImageConverter {

  /**
   * Converts the specified image(s).
   *
   * @param uri The location of the image (HTTP, HTTPS, FILE etc).
   */
  def convert(uri: String)

  /**
   * Converts a collection of image(s).
   *
   * @param uriCollection A collection of image URIs (HTTP, HTTPS, FILE etc)
   */
  def convert(uriCollection: Array[String])

}
