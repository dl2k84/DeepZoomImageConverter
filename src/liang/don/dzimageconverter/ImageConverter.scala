package liang.don.dzimageconverter

/**
 * Convert images to the Microsoft Deep Zoom format.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
abstract class ImageConverter {

  /**
   * Converts the specified image(s).
   *
   * @param uri The location of the image (HTTP, HTTPS, FILE etc).
   * @param fileFormat Image format.
   */
  def convert(uri: String, fileFormat: String)

}
