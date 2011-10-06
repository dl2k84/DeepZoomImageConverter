package liang.don.dzimageconverter.format

/**
 * JPEG format definition with magic number.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object JpegFormat extends ImageFormat {

  override def FileFormat: String = "jpg" // TODO handle jpeg extensions.

  override def MagicNumber: Array[Int] = Array[Int](0xff, 0xd8)

}
