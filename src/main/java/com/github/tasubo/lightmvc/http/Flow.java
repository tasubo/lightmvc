package com.github.tasubo.lightmvc.http;

/**
 *
 * @author Tadas
 */
public interface Flow {

    void requireValidUser() throws NoValidUserException;

    void requireAdmin() throws NonAdminUserException;

    void redirect(String url);
}
