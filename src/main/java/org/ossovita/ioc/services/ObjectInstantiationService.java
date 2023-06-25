package org.ossovita.ioc.services;

import org.ossovita.ioc.exceptions.BeanInstantiationException;
import org.ossovita.ioc.exceptions.ServiceInstantiationException;
import org.ossovita.ioc.exceptions.PreDestroyExecutionException;
import org.ossovita.ioc.models.ServiceBeanDetails;
import org.ossovita.ioc.models.ServiceDetails;

public interface ObjectInstantiationService {

    void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException;

    void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws BeanInstantiationException;

    void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyExecutionException;


}
