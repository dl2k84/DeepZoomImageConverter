package liang.don.dzimageconverter.io.java

import liang.don.dzimageconverter.io.ImageFetcher
import javax.imageio.ImageIO
import java.net.URL
import java.awt.image.BufferedImage
import java.io.OutputStream
import liang.don.dzimageconverter.config.ConverterProperties

/**
 * Fetches an image using Java specific API(s)
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
trait ImageFetcherJava extends ImageFetcher {

  override def fetchImage(uri: String): AnyRef = {
    ImageIO.read(new URL(uri))
  }

  override def getImageSize(image: AnyRef): (Int, Int) = {
    val sourceImage = image.asInstanceOf[BufferedImage]
    (sourceImage.getWidth, sourceImage.getHeight)
  }

  override def saveImage(image: AnyRef, fileFormat: String, outputStream: AnyRef) {
    ImageIO.write(image.asInstanceOf[BufferedImage], fileFormat, outputStream.asInstanceOf[OutputStream])
  }

}
