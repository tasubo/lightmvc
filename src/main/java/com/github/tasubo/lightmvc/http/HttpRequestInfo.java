package com.github.tasubo.lightmvc.http;

import com.github.tasubo.lightmvc.Config;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tadas
 */
class HttpRequestInfo implements RequestInfo {

    @Inject
    private HttpServletRequest request;
    @Inject
    private Config config;

    @Override
    public String getUrlParam(int index) {
        String uri = request.getRequestURI();
        String[] parts = uri.split("\\/");
        // TODO hardcoded place for param start
        int part = 3 + index;
        if (part > parts.length - 1) {
            return null;
        }
        return parts[part];
    }

    @Override
    public String getFeature() {

        String feature = config.get("default.feature");

        String uri = (request.getRequestURI());
        String[] tokens = uri.split("/");


        if (tokens.length > 1) {

            if (tokens[1] != null && tokens[1].length() != 0) {
                feature = tokens[1];
            }
        }

        return feature;
    }

    @Override
    public String getAction() {

        String action = config.get("default.action");

        String uri = (request.getRequestURI());
        String[] tokens = uri.split("/");

        if (tokens.length > 2) {
            if (tokens[2] != null && tokens[2].length() != 0) {
                action = tokens[2];
            }
        }

        return action;
    }

    @Override
    public boolean isPost() {
        if (request.getMethod().contentEquals("POST")) {
            return true;
        }
        return false;
    }

    @Override
    public String getPostParam(String key) {
        return request.getParameter(key);
    }

    @Override
    public Long getLongUrlParam(int index) {
        String value = getUrlParam(index);
        return Long.parseLong(value);
    }
}
