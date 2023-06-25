package org.ossovita;

import org.ossovita.ioc.annotations.StartsUp;
import org.ossovita.ioc.config.MagicConfiguration;
import org.ossovita.ioc.enums.DirectoryType;
import org.ossovita.ioc.models.Directory;
import org.ossovita.ioc.models.ServiceDetails;
import org.ossovita.ioc.services.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class MagicInjector {

    public static final DependencyContainer dependencyContainer;

    static {
        dependencyContainer = new DependencyContainerImpl();
    }


    public static void run(Class<?> startupClass) {
        run(startupClass, new MagicConfiguration());
    }

    public static void run(Class<?> startupClass, MagicConfiguration configuration) {
        ServicesScanningService scanningService = new ServiceScanningServiceImpl(configuration.annotations());
        ObjectInstantiationServiceImpl objectInstantiationService = new ObjectInstantiationServiceImpl();
        ServicesInstantiationService instantiationService = new ServiceInstantiationServiceImpl(
                configuration.instantiations(),
                objectInstantiationService);

        Directory directory = new DirectoryResolverImpl().resolveDirectory(startupClass);

        ClassLocator classLocator = new ClassLocatorForDirectory();
        if (directory.getDirectoryType() == DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFile();
        }

        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());

        Set<ServiceDetails<?>> mappedServices = scanningService.mapServices(locatedClasses);
        List<ServiceDetails<?>> serviceDetails = instantiationService.instantiateServiceAndBeans(mappedServices);

        dependencyContainer.init(serviceDetails, objectInstantiationService);
        runStartUpMethod(startupClass);
    }


    private static void runStartUpMethod(Class<?> startupClass) {
        ServiceDetails<?> serviceDetails = dependencyContainer.getServiceDetails(startupClass);
        for (Method declaredMethod : serviceDetails.getServiceType().getDeclaredMethods()) {
            if (declaredMethod.getParameterCount() != 0 ||
                    (declaredMethod.getReturnType() != void.class &&
                            declaredMethod.getReturnType() != Void.class)
                    || !declaredMethod.isAnnotationPresent(StartsUp.class)
            ) {
                continue;
            }
            declaredMethod.setAccessible(true);
            try {
                declaredMethod.invoke(serviceDetails.getInstance());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            return;
        }
    }
}