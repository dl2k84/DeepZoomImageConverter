package liang.don.dzimageconverter.format

/**
 * PNG format definition with magic number.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object PngFormat extends ImageFormat {

  override def FileFormat: String = "png"

  override def MagicNumber: Array[Int] = Array[Int](0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)

}
