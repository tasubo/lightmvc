package com.github.tasubo.lightmvc.user;

import java.util.Set;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
public interface User {

    boolean isAdmin();

    public Set<String> getRoles();

    public String getName();
}
