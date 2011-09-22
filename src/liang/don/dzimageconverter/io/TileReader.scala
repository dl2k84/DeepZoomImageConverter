package liang.don.dzimageconverter.io

/**
 * Reads a tile from the original image.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
abstract class TileReader {

  /**
   * Returns a tile.
   *
   * @param image The original image.
   * @param column The column position of the tile to read.
   * @param row The row position of the tile to read.
   * @param preserveAlphaChannel If you are converting PNG images which support
   *                              transparent or translucent (opaque/non-opague),
   *                              set this true, else false (for JPEG images).
   */
  def readTile(image: AnyRef, column: Int, row: Int, preserveAlphaChannel: Boolean): AnyRef

}
