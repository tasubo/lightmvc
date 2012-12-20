package com.github.tasubo.lightmvc.http;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
public class PageNotFoundException extends RuntimeException {

    PageNotFoundException(Exception ex) {
        super(ex);
    }
}
