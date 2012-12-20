package com.github.tasubo.lightmvc.http;

import com.google.inject.servlet.ServletModule;

/**
 *
 * @author Tadas
 */
public class HttpModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(RequestInfo.class).to(HttpRequestInfo.class);
        bind(Flow.class).to(SimpleFlow.class);
    }
}
