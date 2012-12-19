package com.github.tasubo.lightmvc;

import com.google.inject.Inject;
import com.google.inject.Injector;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.el.BeanELResolver;
import javax.el.ResourceBundleELResolver;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;
import com.github.tasubo.lightmvc.view.PartELResolver;
import com.github.tasubo.lightmvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontController extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4403943253268166062L;
    private static final Logger LOG = LoggerFactory.getLogger(
            FrontController.class);

    private static class ActionNotFoundException extends Exception {

        public ActionNotFoundException() {
        }

        public ActionNotFoundException(Throwable cause) {
            super(cause);
        }
    }

    private static class ControllerLogicException extends Exception {

        public ControllerLogicException(Throwable cause) {
            super(cause);
        }
    }

    private static class FeatureNotFoundException extends Exception {

        public FeatureNotFoundException() {
        }

        public FeatureNotFoundException(Throwable cause) {
            super(cause);
        }
    }
    private Injector injector;
    @Inject
    private Config config;

    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
        } catch (ServletException e1) {
            LOG.error("e", e1);
        }
        ServletContext context = config.getServletContext();
        try {
            /*
             * This shit is needed because of GAE bug
             * http://stackoverflow.com/questions/2551517/gae-j-unable-to-register-a-custom-elresolver
             * http://code.google.com/p/googleappengine/issues/detail?id=3034#makechanges
             */
            Class.forName("org.apache.jasper.compiler.JspRuntimeContext");
        } catch (ClassNotFoundException ex) {
            LOG.error("e", ex);
        }
        JspApplicationContext jspApplicationContext =
                JspFactory.getDefaultFactory().getJspApplicationContext(context);

        jspApplicationContext.addELResolver(new PartELResolver(
                new BeanELResolver(), true));

        jspApplicationContext.addELResolver(new ResourceBundleELResolver());

        LOG.info("Added PartELResolver");

        injector = (Injector) context.getAttribute(Injector.class.getName());
        if (injector == null) {
            throw new IllegalStateException("Guice Injector not found");
        }
        injector.injectMembers(this);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        _handle(req, rsp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {
        _handle(req, rsp);
    }

    private void _handle(HttpServletRequest req, HttpServletResponse rsp)
            throws ServletException, IOException {

        RequestInfo requestInfo = injector.getInstance(RequestInfo.class);

        String feature = requestInfo.getFeature();
        String action = requestInfo.getAction();

        View view = injector.getInstance(View.class);
        User user = injector.getInstance(User.class);

        req.setAttribute("view", view);
        req.setAttribute("config", config.getBundle());
        req.setAttribute("user", user);

        _handleFlow(feature, action);


        if (rsp.isCommitted()) {
            LOG.info("Deny request dispatch");
            return;
        }


        getServletContext().getRequestDispatcher(
                view.getViewPath()).forward(req, rsp);

    }

    private void _handleFlow(String feature, String action)
            throws ServletException, IOException {

        View view = injector.getInstance(View.class);

        try {
            _handlePage(feature, action);
        } catch (FeatureNotFoundException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Feature", ex);
            }
            throw new PageNotFoundException(ex);
        } catch (ActionNotFoundException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Action", ex);
            }
            throw new PageNotFoundException(ex);
        } catch (ControllerLogicException ex) {
            LOG.error("Error", ex);
            throw new PageNotFoundException(ex);
        } catch (NoValidUserException ex) {
            LOG.debug("detected valid user requirement");
        } catch (ServletRedirectException ex) {
            LOG.debug("redirecting");
        }

    }

    private void _handlePage(String feature, String action)
            throws FeatureNotFoundException, ActionNotFoundException, ControllerLogicException {


        View view = injector.getInstance(View.class);
        final String viewPath = "/views/" + feature + "/"
                + action + ".jsp";


        view.setViewPath(viewPath);

        String name = "lt.ntec.danblog.server." + feature + ".Controller";

        String methodName = action + "Action";

        LOG.debug("Opening: /{}/{}", feature, action);

        Object controllerInstance;
        Class<?> cl;
        try {

            cl = Class.forName(name);
            Constructor<?> constructor = cl.getConstructor();
            controllerInstance = constructor.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            throw new FeatureNotFoundException(ex);
        } catch (InvocationTargetException ex) {
            throw new ControllerLogicException(ex);
        }

        injector.injectMembers(controllerInstance);

        Class<?> arguments[] = new Class[]{};
        Method controllerAction = null;
        try {
            controllerAction = cl.getMethod(methodName, arguments);
        } catch (NoSuchMethodException ex) {
            throw new ActionNotFoundException(ex);
        }

        try {
            controllerAction.invoke(controllerInstance,
                    (Object[]) new Class[]{});
        } catch (NoValidUserException | ServletRedirectException e) {
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            throw new ActionNotFoundException(ex);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause.getClass().equals(ServletRedirectException.class)) {
                throw (ServletRedirectException) cause;
            }
            if (cause.getClass().equals(NoValidUserException.class)) {
                throw (NoValidUserException) cause;
            }
            throw new ControllerLogicException(ex);
        }

        LOG.info("Forward to layout");

    }
}
