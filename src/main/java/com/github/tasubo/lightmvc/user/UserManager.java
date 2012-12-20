package com.github.tasubo.lightmvc.user;

public interface UserManager {

    User getCurrentUser();

    boolean isValidUserAvailable();

    String getLoginUrl(String destinationAfterLogin);

    String getLogoutUrl(String destinationAfterLogout);
}
