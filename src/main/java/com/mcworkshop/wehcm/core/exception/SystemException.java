package com.mcworkshop.wehcm.core.exception;

/**
 * Created by markfredchen on 3/26/15.
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SystemException() {
        super();
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

}
