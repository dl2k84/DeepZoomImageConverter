package liang.don.dzimageconverter.log

import liang.don.dzimageconverter.config.ConverterProperties

/**
 * Logs information.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
abstract class LoggerInterface {

  /**
   * Sets the minimum log level to output.
   * Log message with levels below the specified logLevel will not be outputted.
   */
  protected val _minimumOutputLevel: LogLevel.Value = ConverterProperties.minimumLogLevel

  /**
   * Logs a message with a default log level.
   *
   * @param message The message to log.
   */
  def log(message: String)

  /**
   * Logs a message with the specified log level.
   *
   * @param message The message to log.
   * @param logLevel The log level of this message.
   */
  def log(message: String, logLevel: LogLevel.Value)

  /**
   * Logs a message with the specified log level and exception.
   *
   * @param message The message to log.
   * @param logLevel The log level of this message.
   * @param exception The exception instance to log with this message.
   */
  def log(message: String, logLevel: LogLevel.Value, exception: Exception)

}
