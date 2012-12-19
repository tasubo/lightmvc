package com.github.tasubo.lightmvc.view.impl;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tadas
 */
class PartImpl implements Part {

    private Map<String, Object> part = new HashMap<>();

    @Override
    public Object getPart(String name) {
        return part.get(name);
    }

    @Override
    public void setPart(String name, Object content) {
        part.put(name, content);
    }
}
