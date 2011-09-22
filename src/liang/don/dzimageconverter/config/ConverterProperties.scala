package liang.don.dzimageconverter.config

import java.util.Properties
import io.Source
import liang.don.dzimageconverter.log.LogLevel

/**
 * Contains settings related to the application.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object ConverterProperties {

  private val PROPERTIES_FILE = "Converter.properties"

  private val BUILD_TARGET_KEY = "buildTarget"
  private val LOG_TYPE_KEY = "logType"
  private val OVERLAP_SIZE_KEY = "overlapSize"
  private val TILE_SIZE_KEY = "tileSize"
  private val MINIMUM_LOG_LEVEL_KEY = "minimumLogLevel"

  private val PROPERTIES = getProperties

  /**
   *
   * @author Don Liang
   * @Version 0.1, 22/09/2011
   */
  object BuildTarget {
    def Java = "java"
    def Net = "net"
  }

  /**
   *
   * @author Don Liang
   * @Version 0.1, 22/09/2011
   */
  object LogType {
    def Console = "Console"
    def File = "File"
  }

  def buildTarget: String = PROPERTIES.getProperty(BUILD_TARGET_KEY)
  def logType: String = PROPERTIES.getProperty(LOG_TYPE_KEY)
  def minimumLogLevel: LogLevel.Value = {
    val level = PROPERTIES.getProperty(MINIMUM_LOG_LEVEL_KEY).toLowerCase
    if (LogLevel.Info.toString.toLowerCase == level) {
      LogLevel.Info
    } else if (LogLevel.Error.toString.toLowerCase == level) {
      LogLevel.Error
    } else if (LogLevel.Fatal.toString.toLowerCase == level) {
      LogLevel.Fatal
    } else {
      LogLevel.Debug
    }
  }
  def overlapSize: Int = PROPERTIES.getProperty(OVERLAP_SIZE_KEY).toInt
  def tileSize: Int = PROPERTIES.getProperty(TILE_SIZE_KEY).toInt

  private def getProperties: Properties = {
    val p = new Properties()
    val source = Source.fromFile(PROPERTIES_FILE, "UTF-8")
    p.load(source.bufferedReader())
    source.close()
    p
  }
}
