package com.github.tasubo.lightmvc;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 *
 * @author Tadas
 */
class UserProvider implements Provider<User> {

    private final UserManager userManager;

    @Inject
    public UserProvider(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public User get() {
        return userManager.getCurrentUser();
    }
}
