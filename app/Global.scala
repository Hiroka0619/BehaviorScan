import play.api._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    try {
      System.loadLibrary("MeCab")
    } catch {
      case e: UnsatisfiedLinkError => Logger.error("Not found libMeCab.so." + e)
      case _: Throwable => Logger.error("unknown error.")
    }
  }

  override def onStop(app: Application) {
    //TODO if need
  }

}