package liang.don.dzimageconverter.io.net

import liang.don.dzimageconverter.io.TileWriter

/**
 * Writes the converted Deep Zoom tiles using .Net(C#) specific API(s).
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
trait TileWriterNet extends TileWriter {

  override def writeTile(tile: AnyRef, column: Int, row: Int, fileFormat: String) {
    // TODO
    sys.error("[" + getClass.getName + "#writeTile] Not implemented.")
  }

}
