package liang.don.dzimageconverter.format.test

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import liang.don.dzimageconverter.format.JpegFormat
import util.Random

/**
 * Spec for JpegFormat.
 *
 * @author Don Liang
 * @Version 0.2.1, 06/10/2011
 */
class JpegFormatSpec extends Spec with ShouldMatchers {

  describe("An image file of the JPEG format") {

    it("should have file extension 'jpg' or 'jpeg'") {
      JpegFormat.FileFormat should (equal ("jpg") or equal ("jpeg"))
    }

    it("should begin with the magic number: 0xff, 0xd8") {
      JpegFormat.MagicNumber should be (Array[Int](0xff, 0xd8))
    }

    it("should be recognizable if the magic number is correct") {
      val startOfFileBytes = Array[Int](0xff, 0xd8)
      JpegFormat.isEqual(startOfFileBytes) should be (true)
    }

    it("should be recognizable if the magic number is correct (even if the given magic number size is larger than expected)") {
      val startOfFileBytes = Array[Int](0xff, 0xd8, 0xff, 0xd8)
      JpegFormat.isEqual(startOfFileBytes) should be (true)
    }

    it("should not be recognizable if the magic number is incorrect") {
      val startOfFileBytes = Array.ofDim[Int](2)
      val r = new Random
      for (i <- 0 until 2) {
        startOfFileBytes(i) = r.nextInt()
      }
      JpegFormat.isEqual(startOfFileBytes) should be (false)
    }

    it("should not be recognizable if the magic number is incorrect (even if the given magic number size is larger than expected)") {
      val startOfFileBytes = Array.ofDim[Int](8)
      val r = new Random
      for (i <- 0 until 8) {
        startOfFileBytes(i) = r.nextInt()
      }
      JpegFormat.isEqual(startOfFileBytes) should be (false)
    }

  }

}
