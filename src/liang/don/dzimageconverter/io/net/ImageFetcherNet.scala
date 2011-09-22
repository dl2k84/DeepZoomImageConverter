package liang.don.dzimageconverter.io.net

import liang.don.dzimageconverter.io.ImageFetcher

/**
 * Fetches an image using .Net(C#) specific API(s)
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait ImageFetcherNet extends ImageFetcher {

  override def fetchImage(uri: String): AnyRef = {
    // TODO
    sys.error("[" + getClass.getName + "#fetchImage] Not implemented.")
  }

  override def saveImage(image: AnyRef, fileFormat: String, outputStream: AnyRef) {
    // TODO
    sys.error("[" + getClass.getName + "#saveImage] Not implemented.")
  }

}
