package liang.don.dzimageconverter.format.test

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import liang.don.dzimageconverter.format.PngFormat
import util.Random

/**
 * Spec for PngFormat.
 *
 * @author Don Liang
 * @Version 0.2.1, 06/10/2011
 */
class PngFormatSpec extends Spec with ShouldMatchers {

  describe("An image file of the PNG format") {

    it("should have file extension 'png'") {
      PngFormat.FileFormat should be ("png")
    }

    it("should begin with the magic number: 0xff, 0xd8") {
      PngFormat.MagicNumber should be (Array[Int](0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a))
    }

    it("should be recognizable if the magic number is correct") {
      val startOfFileBytes = Array[Int](0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)
      PngFormat.isEqual(startOfFileBytes) should be (true)
    }

    it("should be recognizable if the magic number is correct (even if the given magic number size is larger than expected)") {
      val startOfFileBytes = Array[Int](0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a, 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a)
      PngFormat.isEqual(startOfFileBytes) should be (true)
    }

    it("should not be recognizable if the magic number is incorrect") {
      val startOfFileBytes = Array.ofDim[Int](8)
      val r = new Random
      for (i <- 0 until 8) {
        startOfFileBytes(i) = r.nextInt()
      }
      PngFormat.isEqual(startOfFileBytes) should be (false)
    }

    it("should not be recognizable if the magic number is incorrect (even if the given magic number size is larger than expected)") {
      val startOfFileBytes = Array.ofDim[Int](16)
      val r = new Random
      for (i <- 0 until 16) {
        startOfFileBytes(i) = r.nextInt()
      }
      PngFormat.isEqual(startOfFileBytes) should be (false)
    }

  }

}
