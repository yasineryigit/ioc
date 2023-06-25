package org.ossovita.ioc.services;

import org.ossovita.ioc.models.ServiceDetails;

import java.util.Set;

public interface ServicesScanningService {

    Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses);


}
