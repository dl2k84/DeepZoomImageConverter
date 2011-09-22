package liang.don.dzimageconverter.io.net

import liang.don.dzimageconverter.io.ImageWrapper

/**
 * ImageWrapper method implementation using .NET(C#) specific API(s).
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait ImageWrapperNet extends ImageWrapper {

  override def getImageSize(image: AnyRef): (Int, Int) = {
    // TODO
    sys.error("[" + getClass.getName + "#getImageSize] Not implemented.")
  }

  override def scaleImage(image: AnyRef, scaleFactor: Double, preserveAlphaChannel: Boolean): AnyRef = {
    // TODO
    sys.error("[" + getClass.getName + "#scaleImage] Not implemented.")
  }

}
