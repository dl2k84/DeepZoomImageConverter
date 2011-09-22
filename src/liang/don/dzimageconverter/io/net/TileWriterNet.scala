package liang.don.dzimageconverter.io.net

import liang.don.dzimageconverter.io.TileWriter

/**
 * Writes the converted Deep Zoom tiles using .Net(C#) specific API(s).
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait TileWriterNet extends TileWriter {

  override def writeTile(image: AnyRef, column: Int, row: Int, fileFormat: String, originalFilename: String) {
    // TODO
    sys.error("[" + getClass.getName + "#writeTile] Not implemented.")
  }

  override def writeTiles(image: AnyRef, fileFormat: String, originalFilename: String) {
    // TODO
    sys.error("[" + getClass.getName + "#writeTiles] Not implemented.")
  }

}
