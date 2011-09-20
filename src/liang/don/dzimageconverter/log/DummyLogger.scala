package liang.don.dzimageconverter.log

/**
 * Dummy Logger.
 *
 * @author Don Liang
 * @Version 0.0.1, 20/09/2011
 */
trait DummyLogger extends LoggerInterface {

  override def log(message: String) { }

  override def log(message: String, logLevel: LogLevel.Value) { }

  override def log(message: String, logLevel: LogLevel.Value, exception: Exception) { }

}
