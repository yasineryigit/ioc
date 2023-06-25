package org.ossovita.ioc.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ServiceDetails<T> {

    private Class<T> serviceType;

    private Annotation annotation;

    private Constructor<T> targetConstructor;

    private T instance;

    private Method postConstructMethod;

    private Method preDestroyMethod;

    private Method[] beans;






}
