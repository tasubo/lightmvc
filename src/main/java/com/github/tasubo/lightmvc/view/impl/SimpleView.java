package com.github.tasubo.lightmvc.view.impl;

import com.github.tasubo.lightmvc.view.View;
import com.google.inject.Inject;

/**
 *
 * @author Tadas
 */
class SimpleView implements View {

    @Inject
    private Part part;
    private String viewPath;

    @Override
    public Object getPart(String key) {
        return part.getPart(key);
    }

    @Override
    public void setPart(String key, Object object) {
        part.setPart(key, object);
    }

    @Override
    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    @Override
    public String getViewPath() {
        return viewPath;
    }
}
