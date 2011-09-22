package liang.don.dzimageconverter.io.java

import liang.don.dzimageconverter.io.ImageFetcher
import javax.imageio.ImageIO
import java.net.URL
import java.awt.image.BufferedImage
import java.io.{File, OutputStream}

/**
 * Fetches an image using Java specific API(s)
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait ImageFetcherJava extends ImageFetcher {

  override def fetchImage(uri: String): AnyRef = {
    if (uri.contains("://")) {
      // TODO Naive way to match if the uri parameter contains a protocol
      ImageIO.read(new URL(uri))
    } else {
      // Assumes it's a local file
      ImageIO.read(new File(uri))
    }
  }

  override def saveImage(image: AnyRef, fileFormat: String, outputStream: AnyRef) {
    ImageIO.write(image.asInstanceOf[BufferedImage], fileFormat, outputStream.asInstanceOf[OutputStream])
  }

}
