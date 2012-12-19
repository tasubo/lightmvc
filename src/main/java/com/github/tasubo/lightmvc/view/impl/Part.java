package com.github.tasubo.lightmvc.view.impl;

/**
 *
 * @author Tadas
 */
interface Part {

    Object getPart(String name);

    void setPart(String name, Object content);
}
