package liang.don.dzimageconverter.io.java

import liang.don.dzimageconverter.io.TileReader

/**
 * Reads a tile from the original image using Java specific API(s).
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
trait TileReaderJava extends TileReader {

  override def readTile(image: AnyRef, column: Int, row: Int): AnyRef = {
    // TODO
    sys.error("[" + getClass.getName + "#readTile] Not implemented.")
  }

}
