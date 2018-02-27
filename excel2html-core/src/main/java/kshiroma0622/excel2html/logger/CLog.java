package kshiroma0622.excel2html.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ログ
 */
public class CLog {

	/**
	 * ログ
	 */
	private static Logger log = Logger.getLogger(java.util.logging.Logger.class.getName());

	/**
	 * コンストラクタ
	 */
	private CLog() {
	}

	// /**
	// * トレース
	// *
	// * @return true/false
	// */
	// public static final boolean isDebugTrace() {
	// return log.isTraceEnabled();
	// }
	//
	// /**
	// * デバッグ
	// *
	// * @return true/false
	// */
	// public static final boolean isDebugEnabled() {
	// return log.isDebugEnabled();
	// }
	//
	// /**
	// * INFO
	// *
	// * @return true/false
	// */
	// public static final boolean isInfoEnabled() {
	// return log.isInfoEnabled();
	// }
	//
	// /**
	// * 警告
	// *
	// * @return true/false
	// */
	// public static final boolean isWarnEnabled() {
	// return log.isWarnEnabled();
	// }
	//
	// /**
	// * エラー
	// *
	// * @return true/false
	// */
	// public static final boolean isErrorEnabled() {
	// return log.isErrorEnabled();
	// }
	//
	// /**
	// * 致命的
	// *
	// * @return true/false
	// */
	// public static final boolean isFatalEnabled() {
	// return log.isFatalEnabled();
	// }

	/**
	 * debug
	 * 
	 * @param message
	 *            message
	 */
	public static final void debug(Object message) {
		log.log(Level.FINE, String.valueOf(message));
	}

	/**
	 * info
	 * 
	 * @param message
	 *            message
	 */
	public static final void info(Object message) {
		log.log(Level.INFO, String.valueOf(message));
	}

	/**
	 * warn
	 * 
	 * @param message
	 *            message
	 */
	public static final void warn(Object message) {
		log.log(Level.WARNING, String.valueOf(message));
	}

	/**
	 * error
	 * 
	 * @param message
	 *            message
	 */
	public static final void error(Object message) {
		log.log(Level.SEVERE, String.valueOf(message));
	}

	/**
	 * trace
	 * 
	 * @param message
	 *            message
	 */
	public static final void trace(Object message) {
		log.log(Level.FINER, String.valueOf(message));
	}
}
