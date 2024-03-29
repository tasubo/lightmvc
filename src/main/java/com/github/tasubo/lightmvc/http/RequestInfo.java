package com.github.tasubo.lightmvc.http;

/**
 *
 * @author Tadas
 */
public interface RequestInfo {

    String getUrlParam(int index);

    Long getLongUrlParam(int index);

    boolean isPost();

    String getPostParam(String key);

    String getFeature();

    String getAction();
}
