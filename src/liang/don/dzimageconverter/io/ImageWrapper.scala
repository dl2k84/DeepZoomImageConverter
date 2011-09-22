package liang.don.dzimageconverter.io

/**
 * Image manipulation related functions whose implementation differs
 * depending on build language (Java/.NET)
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
abstract class ImageWrapper {

  /**
   * Returns the image size as (width, height).
   *
   * @return The image size as (width, height)
   */
  def getImageSize(image: AnyRef): (Int, Int)

  /**
   * Scale (resize) an image with a specified scaleFactor.
   *
   * @param image The image to scale (resize).
   * @param scaleFactor The scaling factor.
   *         A value of less than 1 will scale(resize) the image down while
   *         a value of greater than 1 will scale(resize) the image up.
   * @param preserveAlphaChannel If you are converting PNG images which support
   *                              transparent or translucent (opaque/non-opague),
   *                              set this true, else false (for JPEG images).
   */
  def scaleImage(image: AnyRef, scaleFactor: Double, preserveAlphaChannel: Boolean): AnyRef

}
