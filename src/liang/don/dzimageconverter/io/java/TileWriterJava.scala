package liang.don.dzimageconverter.io.java

import java.awt.image.BufferedImage
import java.lang.StringBuffer
import liang.don.dzimageconverter.io.{TileFactory, TileWriter}
import javax.imageio.ImageIO
import java.io.{FileOutputStream, BufferedOutputStream, File}
import liang.don.dzimageconverter.format.PngFormat
import liang.don.dzimageconverter.config.{FileProperties, ConverterProperties}
import liang.don.dzimageconverter.log.{LogLevel, Logger}

/**
 * Writes the converted Deep Zoom tiles using Java specific API(s).
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait TileWriterJava extends TileWriter {

  private val tileReader = TileFactory.getTileReader

  override def writeTile(image: AnyRef, column: Int, row: Int, fileFormat: String, originalFilename: String) {
    val sourceImage = image.asInstanceOf[BufferedImage]
    val zoomLevel = calculateMaximumZoomLevel(sourceImage.getWidth, sourceImage.getHeight)
    val tile = tileReader.readTile(image, column, row, (PngFormat.FileFormat == fileFormat))

    // N.B. Keeping the file extension when creating the generated base directory (unlike Microsoft Deep Zoom Composer).
    // This was decided so that files with the same name will generate different base directories which Deep Zoom Composer could not.
    // i.e.
    // This converter: foo.jpg => foo.jpg_files
    //                 foo.png => foo.png_files
    // Deep Zoom Converter: foo.jpg => foo_files
    //                      foo.png => foo_files
    val directoryName = originalFilename.substring(originalFilename.lastIndexOf(File.separator) + 1) + FileProperties.imageBaseDirectorySuffix + File.separator + zoomLevel
//    val directoryName = {
//      val pathSeparatorIndex = originalFilename.lastIndexOf("/")
//      val fileExtensionIndex = originalFilename.lastIndexOf(".")
//      if (pathSeparatorIndex != -1) {
//        var directory = originalFilename.substring(pathSeparatorIndex + 1)
//        if (pathSeparatorIndex < fileExtensionIndex) {
//          directory = directory.substring(0, fileExtensionIndex)
//        }
//        directory
//      } else {
//        if (fileExtensionIndex != -1) {
//          originalFilename.substring(0, fileExtensionIndex)
//        } else {
//          originalFilename
//        }
//      }
//    } + File.separator + zoomLevel
    val filenameBuffer = new StringBuffer
    filenameBuffer.append(column).append("_").append(row).append(".").append(fileFormat)

    val directory = new File(FileProperties.baseDirectory + File.separator + directoryName)
    if (!directory.exists()) {
      directory.mkdirs()
    }

    val file = new File(directory, filenameBuffer.toString)
    if (!file.exists()) {
      file.createNewFile()
    }

    Logger.instance.log("[" + getClass.getName + "#writeTile] Writing tile: " + file.getAbsolutePath, LogLevel.Debug)
    ImageIO.write(tile.asInstanceOf[BufferedImage], fileFormat, new BufferedOutputStream(new FileOutputStream(file)))
  }

  override def writeTiles(image: AnyRef, fileFormat: String, originalFilename: String) {
    val sourceImage = image.asInstanceOf[BufferedImage]
    val maxCols = math.ceil(sourceImage.getWidth / ConverterProperties.tileSize.toDouble).toInt
    val maxRows = math.ceil(sourceImage.getHeight / ConverterProperties.tileSize.toDouble).toInt

    for(col <- 0 until maxCols) {
      for (row <- 0 until maxRows) {
        writeTile(sourceImage, col, row, fileFormat, originalFilename)
      }
    }
  }


  // TODO Below methods copied from liang.don.dzviewer.ImageFetcher of the
  // DeepZoomViewer project. Once I have refactored out that project,
  // referencing that library would be ideal but for now, copy/pasting it here for convenience.
  def calculateMaximumZoomLevel(width: Int, height: Int): Int = {
    math.ceil(math.log(math.max(width, height)) / math.log(2)).toInt
  }

}
