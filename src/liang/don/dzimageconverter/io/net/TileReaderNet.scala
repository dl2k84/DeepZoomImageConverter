package liang.don.dzimageconverter.io.net

import liang.don.dzimageconverter.io.TileReader

/**
 * Reads a tile from the original image using .Net(C#) specific API(s).
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait TileReaderNet extends TileReader {

  override def readTile(image: AnyRef, column: Int, row: Int, preserveAlphaChannel: Boolean): AnyRef = {
    // TODO
    sys.error("[" + getClass.getName + "#readTile] Not implemented.")
  }

}
