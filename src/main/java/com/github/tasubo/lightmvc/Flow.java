package com.github.tasubo.lightmvc;

/**
 *
 * @author Tadas
 */
public interface Flow {

    void requireValidUser();

    void requireAdmin();

    void redirect(String url);
}
