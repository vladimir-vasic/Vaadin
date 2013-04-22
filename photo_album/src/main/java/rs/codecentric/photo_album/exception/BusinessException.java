package rs.codecentric.photo_album.exception;

import java.io.Serializable;

/**
 * @author vladimir.vasic@codecentric.de
 *
 */
public class BusinessException extends Exception implements Serializable {

	private static final long serialVersionUID = 2215596183884288744L;
	
	private BusinessErrorCode errorCode;
    private Object[] params;

    public BusinessException(BusinessErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(BusinessErrorCode errorCode, String string, Throwable thrwbl, Object... params) {
        super(string, thrwbl);
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessException(BusinessErrorCode errorCode, String string, Object... params) {
        super(string);
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessException(BusinessErrorCode errorCode, Throwable thrwbl, Object... params) {
        super(thrwbl);
        this.errorCode = errorCode;
        this.params = params;
    }

    public BusinessErrorCode getErrorCode() {
        return errorCode;
    }

    public Object[] getParams() {
        return params;
    }

//    public String getFormattedMessage() {
//        return MessageFormatter.arrayFormat(super.getMessage(), params).getMessage();
//    }
}