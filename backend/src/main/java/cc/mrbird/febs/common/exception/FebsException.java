package cc.mrbird.febs.common.exception;

/**
 * FEBS 系统内部异常
 */
public class FebsException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public FebsException(String message) {
        super(message);
    }
}
