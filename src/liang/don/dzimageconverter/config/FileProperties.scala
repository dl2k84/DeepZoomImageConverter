package liang.don.dzimageconverter.config

/**
 * Contains settings related to the generated Deep Zoom file structure.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object FileProperties {

  def baseDirectory: String = "dzc_output_images"
  def collectionDescriptorFilename: String = "dzc_output.xml"
  def deepZoomNamespace: String = "http://schemas.microsoft.com/deepzoom/2009"
  def imageBaseDirectorySuffix: String = "_files"
}
