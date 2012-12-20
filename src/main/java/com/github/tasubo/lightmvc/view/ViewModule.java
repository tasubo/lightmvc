package com.github.tasubo.lightmvc.view;

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
        bind(View.class).to(SimpleView.class).in(ServletScopes.REQUEST);
    }
}
