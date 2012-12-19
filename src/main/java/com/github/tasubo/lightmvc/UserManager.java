package com.github.tasubo.lightmvc;

public interface UserManager {

    User getCurrentUser();

    boolean isAvailableValidUser();

    User getUser(Long id);

    String getLoginUrl(String destination);

    String getLogoutUrl(String destination);

    Long registerCurrentUser();
}
