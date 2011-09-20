package liang.don.dzimageconverter.io

/**
 * Writes the converted Deep Zoom tiles.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
abstract class TileWriter {

  /**
   * Writes a tile.
   *
   * @param tile The tile to write out.
   * @param column The column position of the tile to write.
   * @param row The row position of the tile to write.
   * @param fileFormat The file extension of this image.
   */
  def writeTile(tile: AnyRef, column: Int, row: Int, fileFormat: String)

}
