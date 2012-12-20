package com.github.tasubo.lightmvc.view;

import com.github.tasubo.lightmvc.view.View;
import com.google.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tadas
 */
class SimpleView implements View {

    @Inject
    private Map<String, Object> map = new HashMap<>();
    private String viewPath;

    @Override
    public Object getPart(String key) {
        return map.get(key);
    }

    @Override
    public void setPart(String key, Object object) {
        map.put(key, object);
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
