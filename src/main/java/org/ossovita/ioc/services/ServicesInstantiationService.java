package org.ossovita.ioc.services;

import org.ossovita.ioc.exceptions.ServiceInstantiationException;
import org.ossovita.ioc.models.ServiceDetails;

import java.util.List;
import java.util.Set;

public interface ServicesInstantiationService {

    List<ServiceDetails<?>> instantiateServiceAndBeans(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException;


}
