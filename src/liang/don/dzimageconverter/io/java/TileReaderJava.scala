package liang.don.dzimageconverter.io.java

import liang.don.dzimageconverter.io.TileReader
import java.awt.image.BufferedImage
import liang.don.dzimageconverter.config.ConverterProperties
import java.awt.AlphaComposite

/**
 * Reads a tile from the original image using Java specific API(s).
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait TileReaderJava extends TileReader {

  override def readTile(image: AnyRef, column: Int, row: Int, preserveAlphaChannel: Boolean): AnyRef = {
    val sourceImage = image.asInstanceOf[BufferedImage]

    val sourceCoordinates = getCoordinates(column, row)
    val sx1 = sourceCoordinates._1
    val sy1 = sourceCoordinates._2

    val tileWidth = getTileLength(sx1, sourceImage.getWidth, column)
    val tileHeight = getTileLength(sy1, sourceImage.getHeight, row)

    val sx2 = sx1 + tileWidth
    val sy2 = sy1 + tileHeight

    val dx1 = 0
    val dy1 = 0
    val dx2 = tileWidth
    val dy2 = tileHeight

    val newTile = new BufferedImage(tileWidth, tileHeight, (if (preserveAlphaChannel) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB))
    val g = newTile.createGraphics()
    if (preserveAlphaChannel) {
      g.setComposite(AlphaComposite.Src)
    }
    g.drawImage(sourceImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null)
    g.dispose()

    newTile
  }

  /**
   * Gets the (x, y) coordinates given the image grid column and row value
   * as well as set overlap size value.
   *
   * @param col Column.
   * @param row Row.
   *
   * @return Coordinates in (x, y) order.
   */
  private def getCoordinates(col: Int, row: Int): (Int, Int) = {
    (getCoordinate(col), getCoordinate(row))
  }

  /**
   * Returns the x coordinate if the pos parameter is a column position else
   * y coordinate if the pos parameter is a row position.
   *
   * @return x coordinate if the pos parameter is a column position, else
   *          y coordinate if the pos parameter is a row value.
   */
  private def getCoordinate(pos: Int): Int = {
    pos * ConverterProperties.tileSize - (if (0 < pos) ConverterProperties.overlapSize else 0)
  }

  /**
   * Returns the length of a tile with adjusted overlapSize if necessary.
   *
   * @param coord The x or y coordinate.
   * @param imageLength Width or height length of an image.
   * @param pos The column or row position of the target tile.
   */
  private def getTileLength(coord: Int, imageLength: Int, pos: Int): Int = {
    val maxLength = imageLength - coord
    if (maxLength <= ConverterProperties.tileSize) {
      // Edge tile that is smaller than tileSize
      return maxLength// + ConverterProperties.overlapSize
    }

    // 1 addition of overlapSize for edge tiles, 2 additions for non-edge tiles.
    var tileLength = ConverterProperties.tileSize + ConverterProperties.overlapSize
    if (0 < pos) {
      val endCoord = coord + tileLength + ConverterProperties.overlapSize
      if (endCoord <= imageLength) {
        // Non-edge tile.
       tileLength += 1
      }
    }
    tileLength
  }
}
