package org.ossovita.ioc.services;

import org.ossovita.ioc.exceptions.AlreadyInitializedException;
import org.ossovita.ioc.models.ServiceDetails;

import java.lang.annotation.Annotation;
import java.util.List;

public interface DependencyContainer {

    void init(List<ServiceDetails<?>> serviceAndBeans, ObjectInstantiationService instantiationService) throws AlreadyInitializedException;

    <T> void reload(ServiceDetails<T> serviceDetails, boolean reloadDependentServices);

    <T> T reload(T service);

    <T> T reload(T service, boolean reloadDependentServices);

    <T> T getService(Class<T> serviceType);

    <T> ServiceDetails<T> getServiceDetails(Class<T> serviceType);

    List<ServiceDetails<?>> getServicesByAnnotation(Class<? extends Annotation> annotationType);

    List<Object> getAllServices();

    List<ServiceDetails<?>> getAllServiceDetails();


}
