package com.github.tasubo.lightmvc.view;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import javax.el.*;

/**
 *
 * @author Tadas
 */
public class ViewELResolver extends ELResolver {

    private final BeanELResolver beanELResolver;

    public ViewELResolver(BeanELResolver beanELResolver) {
        this.isReadOnly = false;
        this.beanELResolver = beanELResolver;
    }

    public ViewELResolver(BeanELResolver beanELResolver, boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
        this.beanELResolver = beanELResolver;
    }

    @Override
    public Class<?> getType(ELContext context,
            Object base,
            Object property) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (base != null && base instanceof View) {
            context.setPropertyResolved(true);
            return String.class;
        }
        return null;
    }

    @Override
    public Object getValue(ELContext context,
            Object base,
            Object property) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (base != null && base instanceof View) {
            context.setPropertyResolved(true);

            View view = (View) base;
            Object part = view.getPart(property.toString());

            if (part == null) {
                try {
                    return beanELResolver.getValue(context, base, property);
                } catch (PropertyNotFoundException ex) {
                    return null;
                }
            }

            return part;
        }
        return null;
    }

    @Override
    public void setValue(ELContext context,
            Object base,
            Object property,
            Object val) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (base != null && base instanceof View) {
            context.setPropertyResolved(true);
            View view = (View) base;
            if (isReadOnly) {
                throw new PropertyNotWritableException();
            }

            view.setPart(property.toString(), val.toString());

        }
    }

    @Override
    public boolean isReadOnly(ELContext context,
            Object base,
            Object property) {

        if (context == null) {
            throw new NullPointerException();
        }

        if (base != null && base instanceof View) {
            context.setPropertyResolved(true);
            View map = (View) base;
            return isReadOnly;
        }
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(
            ELContext context,
            Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context,
            Object base) {
        if (base != null && base instanceof View) {
            return Object.class;
        }
        return null;
    }
    private boolean isReadOnly;
}
