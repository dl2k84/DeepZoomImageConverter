package liang.don.dzimageconverter.io.java

import java.awt.image.BufferedImage
import liang.don.dzimageconverter.io.ImageWrapper
import java.awt.{RenderingHints, AlphaComposite}

/**
 * ImageWrapper method implementation using Java specific API(s).
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait ImageWrapperJava extends ImageWrapper {

  override def getImageSize(image: AnyRef): (Int, Int) = {
    val sourceImage = image.asInstanceOf[BufferedImage]
    (sourceImage.getWidth, sourceImage.getHeight)
  }

  override def scaleImage(image: AnyRef, scaleFactor: Double, preserveAlphaChannel: Boolean): AnyRef = {
    val sourceImage = image.asInstanceOf[BufferedImage]
    val scaledWidth: Int = (math.ceil(sourceImage.getWidth * scaleFactor)).toInt
    val scaledHeight: Int = (math.ceil(sourceImage.getHeight * scaleFactor)).toInt

    val scaledImage = new BufferedImage(scaledWidth, scaledHeight, (if (preserveAlphaChannel) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB))
    val g = scaledImage.createGraphics()
    if (preserveAlphaChannel) {
      g.setComposite(AlphaComposite.Src)
    }
    // Use Bicubic Interpolation rendering for scaled images.
    // TODO Investigate whether there are better image quality algorithms when scaling..
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
    g.drawImage(sourceImage, 0, 0, scaledWidth, scaledHeight, null)
    g.dispose()

    scaledImage
  }
}
