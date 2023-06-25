package org.ossovita.ioc.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceDetails<T> {

    private Class<T> serviceType;

    private Annotation annotation;

    private Constructor<T> targetConstructor;

    private T instance;

    private Method postConstructMethod;

    private Method preDestroyMethod;

    private Method[] beans;

    private final List<ServiceDetails<T>> dependentServices;

    public ServiceDetails() {
        this.dependentServices = new ArrayList<>();
    }

    public ServiceDetails(Class<T> serviceType,
                          Annotation annotation,
                          Constructor<T> targetConstructor,
                          Method postConstructMethod,
                          Method preDestroyMethod,
                          Method[] beans
    ) {
        this();
        this.setAnnotation(annotation);
        this.setTargetConstructor(targetConstructor);
        this.setPostConstructMethod(postConstructMethod);
        this.setPreDestroyMethod(preDestroyMethod);
        this.setBeans(beans);

    }

    public Class<T> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Class<T> serviceType) {
        this.serviceType = serviceType;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public Constructor<T> getTargetConstructor() {
        return targetConstructor;
    }

    public void setTargetConstructor(Constructor<T> targetConstructor) {
        this.targetConstructor = targetConstructor;
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public Method getPostConstructMethod() {
        return postConstructMethod;
    }

    public void setPostConstructMethod(Method postConstructMethod) {
        this.postConstructMethod = postConstructMethod;
    }

    public Method getPreDestroyMethod() {
        return preDestroyMethod;
    }

    public void setPreDestroyMethod(Method preDestroyMethod) {
        this.preDestroyMethod = preDestroyMethod;
    }

    public Method[] getBeans() {
        return beans;
    }

    public void setBeans(Method[] beans) {
        this.beans = beans;
    }

    public List<ServiceDetails<T>> getDependentServices() {
        return Collections.unmodifiableList(this.dependentServices);
    }

    public void addDependentService(ServiceDetails<T> serviceDetails) {
        this.dependentServices.add(serviceDetails);
    }

    @Override
    public int hashCode() {
        if (this.serviceType == null) {
            return super.hashCode();
        }

        return this.serviceType.hashCode();
    }
}
