package com.github.tasubo.lightmvc.impl;

import com.google.inject.servlet.ServletModule;
import com.github.tasubo.lightmvc.Flow;
import com.github.tasubo.lightmvc.RequestInfo;

/**
 *
 * @author Tadas
 */
public class CoreHttpModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(RequestInfo.class).to(HttpRequestInfo.class);
        bind(Flow.class).to(SimpleFlow.class);
    }
}
