package com.github.tasubo.lightmvc.security.tags;

import com.github.tasubo.lightmvc.user.User;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Tadas
 */
public class SecurityUserHandler extends BodyTagSupport {

    private final static Logger LOG = LoggerFactory.getLogger(SecurityUserHandler.class);
    private String[] users;

    private boolean hasValidUser() {

        Object object = pageContext.getRequest().getAttribute("user");

        if (object == null) {
            return false;
        }

        if (!(object instanceof User)) {
            return false;
        }


        User user = (User) object;
        if (Arrays.binarySearch(users, user.getName()) >= 0) {
            return true;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Denying access for {} user", user.getName());
        }



        return false;

    }

    @Override
    public int doStartTag() throws JspException {

        if (!hasValidUser()) {
            return SKIP_BODY;
        }

        return EVAL_BODY_BUFFERED;

    }

    @Override
    public int doEndTag() throws JspException {

        return EVAL_PAGE;

    }

    @Override
    public int doAfterBody() throws JspException {

        BodyContent bodyCont = getBodyContent();
        JspWriter out = bodyCont.getEnclosingWriter();
        try {
            bodyCont.writeOut(out);
        } catch (IOException ex) {
            throw new JspException(ex);
        }

        bodyCont.clearBody();

        return SKIP_BODY;

    }

    public void setUsers(String users) {

        if (users == null || users.isEmpty()) {
            throw new IllegalStateException("Names cannot be empty");
        }

        String[] split = users.split(",");

        Arrays.sort(split);

        this.users = split;
    }
}
