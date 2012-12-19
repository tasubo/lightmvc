package com.github.tasubo.lightmvc;

/**
 *
 * @author Tadas
 */
public class NonAdminUserException extends RuntimeException {

    public NonAdminUserException(Throwable cause) {
        super(cause);
    }

    public NonAdminUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonAdminUserException(String message) {
        super(message);
    }

    public NonAdminUserException() {
    }
}
