package com.github.tasubo.lightmvc.view.impl;

import com.github.tasubo.lightmvc.view.View;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;

/**
 *
 * @author Tadas
 */
public class ViewModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(Part.class).to(PartImpl.class).in(ServletScopes.REQUEST);
        bind(View.class).to(SimpleView.class).in(ServletScopes.REQUEST);
    }
}
