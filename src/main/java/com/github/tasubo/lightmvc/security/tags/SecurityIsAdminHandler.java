package com.github.tasubo.lightmvc.security.tags;

import com.github.tasubo.lightmvc.User;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * @author Tadas
 */
public class SecurityIsAdminHandler extends BodyTagSupport {

    private boolean admin = true;

    private boolean isUserAdmin() {

        Object object = pageContext.getRequest().getAttribute("user");

        if (object == null) {
            return false;
        }

        if (!(object instanceof User)) {
            return false;
        }

        User user = (User) object;

        return user.isAdmin();

    }

    @Override
    public int doStartTag() throws JspException {

        if (!isUserAdmin() && admin) {
            return SKIP_BODY;
        }

        if (!admin && isUserAdmin()) {
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

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
