package org.ossovita.ioc.models;

import java.lang.reflect.Method;

public class ServiceBeanDetails<T> extends ServiceDetails<T> {

    private final Method originMethod;

    private final ServiceDetails<T> rootService;

    public ServiceBeanDetails(Class<T> beanType, Method originMethod, ServiceDetails<T> rootService) {
        this.setServiceType(beanType);
        this.setBeans(new Method[0]);
        this.originMethod = originMethod;
        this.rootService = rootService;
    }

    public Method getOriginMethod() {
        return this.originMethod;
    }

    public ServiceDetails<T> getRootService() {
        return this.rootService;
    }
}
