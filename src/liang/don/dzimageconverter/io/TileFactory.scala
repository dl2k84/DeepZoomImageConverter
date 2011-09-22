package liang.don.dzimageconverter.io

import java.{ImageWrapperJava, ImageFetcherJava, TileWriterJava, TileReaderJava}
import liang.don.dzimageconverter.config.ConverterProperties
import liang.don.dzimageconverter.config.ConverterProperties.BuildTarget
import net.{ImageWrapperNet, ImageFetcherNet, TileWriterNet, TileReaderNet}

/**
 * Creates tile reader and writer instances based on the
 * build language (Java/.NET) setting in Converter.properties.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object TileFactory {

  /**
   * Creates a new instance of ImageFetcher.
   *
   * @return New instance of ImageFetcher based on build language.
   */
  def getImageFetcher: ImageFetcher = {
    // TODO make singleton?
    val buildTarget = ConverterProperties.buildTarget
    if (BuildTarget.Java == buildTarget) {
      new ImageFetcher with ImageFetcherJava
    } else if (BuildTarget.Net == buildTarget) {
      new ImageFetcher with ImageFetcherNet
    } else {
      sys.error("[" + getClass.getName + "#getImageFetcher] Invalid buildTarget.")
    }
  }

  /**
   * Creates a new instance of ImageWrapper.
   *
   * @return New instance of ImageWrapper based on build language.
   */
  def getImageWrapper: ImageWrapper = {
    // TODO make singleton?
    val buildTarget = ConverterProperties.buildTarget
    if (BuildTarget.Java == buildTarget) {
      new ImageWrapper with ImageWrapperJava
    } else if (BuildTarget.Net == buildTarget) {
      new ImageWrapper with ImageWrapperNet
    } else {
      sys.error("[" + getClass.getName + "#getImageWrapper] Invalid buildTarget.")
    }
  }

  /**
   * Creates a new instance of TileReader.
   *
   * @return New instance of TileReader based on build language.
   */
  def getTileReader: TileReader = {
    // TODO make singleton?
    val buildTarget = ConverterProperties.buildTarget
    if (BuildTarget.Java == buildTarget) {
      new TileReader with TileReaderJava
    } else if (BuildTarget.Net == buildTarget) {
      new TileReader with TileReaderNet
    } else {
      sys.error("[" + getClass.getName + "#getTileReader] Invalid buildTarget.")
    }
  }

  /**
   * Creates a new instance of TileWriter.
   *
   * @return New instance of TileWriter based on build language.
   */
  def getTileWriter: TileWriter = {
    // TODO make singleton?
    val buildTarget = ConverterProperties.buildTarget
    if (BuildTarget.Java == buildTarget) {
      new TileWriter with TileWriterJava
    } else if (BuildTarget.Net == buildTarget) {
      new TileWriter with TileWriterNet
    } else {
      sys.error("[" + getClass.getName + "#getTileWriter] Invalid buildTarget.")
    }
  }

}
