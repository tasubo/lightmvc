package com.github.tasubo.lightmvc;

import com.github.tasubo.lightmvc.user.User;
import com.google.inject.servlet.ServletModule;

public class CoreServletModule extends ServletModule {

    @Override
    protected void configureServlets() {

        bind(User.class).toProvider(UserProvider.class);
        bind(Config.class);

    }
}