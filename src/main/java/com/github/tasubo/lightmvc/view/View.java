package com.github.tasubo.lightmvc.view;

/**
 *
 * @author Tadas
 */
public interface View {

    Object getPart(String key);

    void setPart(String key,
            Object object);

    void setViewPath(String viewPath);

    String getViewPath();
}
