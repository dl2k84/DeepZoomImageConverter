package liang.don.dzimageconverter.log

import liang.don.dzimageconverter.config.ConverterProperties
import liang.don.dzimageconverter.config.ConverterProperties.{BuildTarget, LogType}

/**
 * Logs information.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
object Logger {

  protected val baseFolder = "log"

  private val _logger: LoggerInterface = {
    val buildTarget = ConverterProperties.buildTarget
    val logType = ConverterProperties.logType
    if (LogType.Console == logType) {
      if (BuildTarget.Java == buildTarget) {
        new LoggerInterface with java.ConsoleLogger
      } else if (BuildTarget.Net == buildTarget) {
        // TODO .Net impl
        new LoggerInterface with net.ConsoleLogger
      } else {
        sys.error("[" + getClass.getName + "] Invalid buildTarget.")
      }
    } else if (LogType.File == logType) {
      if (BuildTarget.Java == buildTarget) {
        new LoggerInterface with java.FileLogger
      } else if (BuildTarget.Net == buildTarget) {
        // TODO .Net impl
        new LoggerInterface with net.FileLogger
      } else {
        sys.error("[" + getClass.getName + "] Invalid buildTarget.")
      }
    } else {
      // no logging.
      new LoggerInterface with DummyLogger
    }
  }

  def instance: LoggerInterface = _logger
}
