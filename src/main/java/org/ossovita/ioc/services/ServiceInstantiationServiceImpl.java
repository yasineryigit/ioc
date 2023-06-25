package org.ossovita.ioc.services;

import org.ossovita.ioc.annotations.Service;
import org.ossovita.ioc.config.configurations.InstantiationConfiguration;
import org.ossovita.ioc.exceptions.ServiceInstantiationException;
import org.ossovita.ioc.models.EnqueuedServiceDetails;
import org.ossovita.ioc.models.ServiceBeanDetails;
import org.ossovita.ioc.models.ServiceDetails;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceInstantiationServiceImpl implements ServicesInstantiationService {

    private static final String MAX_NUMBER_OF_ALLOWED_ITERATION_REACHED = "Maximum number of allowed iterations was reached '%s'. ";

    public static final String COULD_NOT_FIND_CONSTRUCTOR_PARAM_MSG = "Could not create instance of '%s'. Parameter '%s' implementation was not found";

    private final InstantiationConfiguration configuration;

    private final ObjectInstantiationService instantiationService;

    private final LinkedList<EnqueuedServiceDetails> enqueuedServiceDetails;

    private final List<Class<?>> allAvailableClasses;

    private final List<ServiceDetails<?>> instantiatedServices;

    public ServiceInstantiationServiceImpl(InstantiationConfiguration configuration, ObjectInstantiationService instantiationService) {
        this.configuration = configuration;
        this.instantiationService = instantiationService;
        this.enqueuedServiceDetails = new LinkedList<>();
        this.allAvailableClasses = new ArrayList<>();
        this.instantiatedServices= new ArrayList<>();
    }

    @Override
    public List<ServiceDetails<?>> instantiateServiceAndBeans(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException {
        this.init(mappedServices);

        int counter = 0;
        int maxNumberOfIterations = this.configuration.getMaximumAllowedIterations();
        while (this.enqueuedServiceDetails.isEmpty()) {
            if (counter > maxNumberOfIterations) {
                throw new ServiceInstantiationException(String.format(MAX_NUMBER_OF_ALLOWED_ITERATION_REACHED, maxNumberOfIterations));
            }

            EnqueuedServiceDetails enqueuedServiceDetails = this.enqueuedServiceDetails.removeFirst();

            if (enqueuedServiceDetails.isResolved()) {
                ServiceDetails<?> serviceDetails = enqueuedServiceDetails.getServiceDetails();
                Object[] dependencyInstances = enqueuedServiceDetails.getDependencyInstances();

                this.instantiationService.createInstance(serviceDetails, dependencyInstances);
                this.registerInstantiatedService(serviceDetails);
                this.registerBeans(serviceDetails);

            } else {
                this.enqueuedServiceDetails.addLast(enqueuedServiceDetails);
                counter++;
            }
        }
        return this.instantiatedServices;
    }

    private void registerBeans(ServiceDetails<?> serviceDetails) {
        for (Method beanMethod : serviceDetails.getBeans()) {
            //TODO: fix
            ServiceBeanDetails<?> beanDetails = new ServiceBeanDetails<>(beanMethod.getReturnType(), beanMethod,serviceDetails);
            this.instantiationService.createBeanInstance(beanDetails);
            this.registerInstantiatedService(beanDetails);
        }
    }

    private void registerInstantiatedService(ServiceDetails<?> serviceDetails) {

        if(!(serviceDetails instanceof ServiceBeanDetails)){
            this.updateDependentServices(serviceDetails);
        }

        this.instantiatedServices.add(serviceDetails);

        for (EnqueuedServiceDetails enqueuedService : this.enqueuedServiceDetails) {
            if(enqueuedService.isDependencyRequired(serviceDetails.getServiceType())){
                enqueuedService.addDependencyInstance(serviceDetails.getInstance());
            }
        }
        
    }

    private void updateDependentServices(ServiceDetails<?> newService){
        for (Class<?> parameterType : newService.getTargetConstructor().getParameterTypes()) {
            for (ServiceDetails<?> serviceDetails : this.instantiatedServices) {
                if(parameterType.isAssignableFrom(serviceDetails.getServiceType())){
                    serviceDetails.addDependentService(newService);
                }
            }
        }
    }

    private void checkForMissingServices(Set<ServiceDetails<?>> mappedClasses) throws ServiceInstantiationException{
        for (ServiceDetails<?> serviceDetails : mappedClasses) {
            for (Class<?> parameterType : serviceDetails.getTargetConstructor().getParameterTypes()) {
                if(!this.isAssignableTypePresent(parameterType)){
                    throw new ServiceInstantiationException(
                            String.format(COULD_NOT_FIND_CONSTRUCTOR_PARAM_MSG,
                            serviceDetails.getServiceType().getName(),
                            parameterType.getName()
                    ));
                }
            }
        }
    }

    private boolean isAssignableTypePresent(Class<?> cls){
        for (Class<?> serviceType : this.allAvailableClasses) {
            if(cls.isAssignableFrom(serviceType)){
                return true;
            }
        }

        return false;
    }

    private void init(Set<ServiceDetails<?>> mappedServices) {
        this.enqueuedServiceDetails.clear();
        this.allAvailableClasses.clear();
        this.instantiatedServices.clear();

        for (ServiceDetails<?> serviceDetails : mappedServices) {
            this.enqueuedServiceDetails.add(new EnqueuedServiceDetails(serviceDetails));
            this.allAvailableClasses.add(serviceDetails.getServiceType());
            this.allAvailableClasses.addAll(Arrays.stream(serviceDetails.getBeans())
                    .map(Method::getReturnType)
                    .collect(Collectors.toList()));
        }
    }
}
