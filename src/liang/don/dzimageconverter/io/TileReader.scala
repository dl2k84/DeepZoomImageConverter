package liang.don.dzimageconverter.io

/**
 * Reads a tile from the original image.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
abstract class TileReader {

  /**
   * Returns a tile.
   *
   * @param image The original image.
   * @param column The column position of the tile to read.
   * @param row The row position of the tile to read.
   */
  def readTile(image: AnyRef, column: Int, row: Int): AnyRef

}
