package liang.don.dzimageconverter.io

/**
 * Writes the converted Deep Zoom tiles.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
abstract class TileWriter {

  /**
   * Writes out a tile.
   *
   * @param image The source image to write out tiles from.
   * @param column The column position of the tile to write.
   * @param row The row position of the tile to write.
   * @param fileFormat The file extension of this image.
   * @param originalFilename The original file name of the source image.
   *                          This can be a normal filename, URL, and so on.
   *                          This is used to determine directory structure.
   */
  def writeTile(image: AnyRef, column: Int, row: Int, fileFormat: String, originalFilename: String)

  /**
   * Writes out all tiles for the image.
   *
   * @param image The source image to divide and write out tiles from.
   * @param fileFormat The file extension of this image.
   * @param originalFilename The original file name of the source image.
   *                          This can be a normal filename, URL, and so on.
   *                          This is used to determine directory structure.
   */
  def writeTiles(image: AnyRef, fileFormat: String, originalFilename: String)

}
