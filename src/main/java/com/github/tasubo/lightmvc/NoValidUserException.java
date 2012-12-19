package com.github.tasubo.lightmvc;

/**
 *
 * @author Tadas
 */
public class NoValidUserException extends RuntimeException {

    public NoValidUserException(Throwable cause) {
        super(cause);
    }

    public NoValidUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoValidUserException(String message) {
        super(message);
    }

    public NoValidUserException() {
    }
}
