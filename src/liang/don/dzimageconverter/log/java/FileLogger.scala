package liang.don.dzimageconverter.log.java

import liang.don.dzimageconverter.log.{LoggerInterface, LogLevel}

/**
 * File Logger using Java I/O libraries.
 *
 * @author Don Liang
 * @Version 0.1, 22/09/2011
 */
trait FileLogger extends LoggerInterface {

  override def log(message: String) {
    // TODO
  }

  override def log(message: String, logLevel: LogLevel.Value) {
    // TODO
  }

  override def log(message: String, logLevel: LogLevel.Value, exception: Exception) {
    // TODO
  }

}
