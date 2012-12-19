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
public class SecurityIsLoggedHandler extends BodyTagSupport {

    private boolean logged = true;

    private boolean isUserLogged() {

        Object object = pageContext.getRequest().getAttribute("user");

        if (object == null) {
            return false;
        }

        if (!(object instanceof User)) {
            return false;
        }

        return true;

    }

    @Override
    public int doStartTag() throws JspException {

        if (!isUserLogged() && logged) {
            return SKIP_BODY;
        }

        if (!logged && isUserLogged()) {
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

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
