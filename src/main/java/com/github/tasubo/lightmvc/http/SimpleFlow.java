package com.github.tasubo.lightmvc.http;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.github.tasubo.lightmvc.user.User;
import com.github.tasubo.lightmvc.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tadas
 */
class SimpleFlow implements Flow {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleFlow.class);
    @Inject
    private UserManager userManager;
    @Inject
    private HttpServletRequest request;
    @Inject
    private HttpServletResponse response;

    @Override
    public void requireValidUser() {
        if (!userManager.isValidUserAvailable()) {
            try {
                response.sendRedirect(userManager.getLoginUrl(
                        request.getRequestURI()));
                throw new NoValidUserException();
            } catch (IOException ex) {
                LOG.error("Could not redirect", ex);
            }
        }
    }

    @Override
    public void redirect(String url) {
        try {

            if (LOG.isDebugEnabled()) {
                LOG.debug("Redirecting to: " + url);
            }

            response.sendRedirect(url);
            throw new ServletRedirectException();
        } catch (IOException ex) {
            LOG.error("Could not redirect", ex);
        }
    }

    @Override
    public void requireAdmin() {
        if (!userManager.isValidUserAvailable()) {
            throw new NonAdminUserException();
        }
        User currentUser = userManager.getCurrentUser();
        boolean admin = currentUser.isAdmin();

        if (!admin) {
            throw new NonAdminUserException();
        }
    }
}
