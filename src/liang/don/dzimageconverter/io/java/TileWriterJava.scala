package liang.don.dzimageconverter.io.java

import liang.don.dzimageconverter.io.TileWriter

/**
 * Writes the converted Deep Zoom tiles using Java specific API(s).
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
trait TileWriterJava extends TileWriter {

  override def writeTile(tile: AnyRef, column: Int, row: Int, fileFormat: String) {
    // TODO
    sys.error("[" + getClass.getName + "#writeTile] Not implemented.")
  }

}
