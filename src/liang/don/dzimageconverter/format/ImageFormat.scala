package liang.don.dzimageconverter.format

/**
 * Image format(s) with their defined magic number.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
abstract class ImageFormat {

  /**
   * Defines the string literal representation of this file format.
   */
  def FileFormat: String

  /**
   * Defines the magic number for this image format.
   */
  def MagicNumber: Array[Int]

  /**
   * Compares magic numbers for image format equality.
   *
   * @param magicNumber The magic number to compare to.
   *
   * @return True if the magic numbers match, else false.
   */
  def isEqual(magicNumber: Array[Int]): Boolean = {
    MagicNumber.zipWithIndex.forall(p => p._1 == magicNumber(p._2))
  }

}
