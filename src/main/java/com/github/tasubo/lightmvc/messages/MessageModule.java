package com.github.tasubo.lightmvc.messages;

import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;

/**
 *
 * @author Tadas Subonis <tadas.subonis@gmail.com>
 */
public class MessageModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(MessageManager.class).to(SessionMessageManager.class).in(ServletScopes.SESSION);
    }
}
