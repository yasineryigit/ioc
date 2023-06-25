package org.ossovita;

import org.ossovita.ioc.annotations.Service;
import org.ossovita.ioc.config.MagicConfiguration;
import org.ossovita.ioc.enums.DirectoryType;
import org.ossovita.ioc.models.Directory;
import org.ossovita.ioc.models.ServiceDetails;
import org.ossovita.ioc.services.*;

import java.util.Set;

@Service
public class MagicInjector {

    public static void main(String[] args) {
        run(MagicInjector.class);

    }

    public static void run(Class<?> startupClass) {
        run(startupClass, new MagicConfiguration());
    }

    public static void run(Class<?> startupClass, MagicConfiguration configuration) {
        ServicesScanningService scanningService = new ServiceScanningServiceImpl(configuration.annotations());
        Directory directory = new DirectoryResolverImpl().resolveDirectory(startupClass);

        ClassLocator classLocator = new ClassLocatorForDirectory();
        if(directory.getDirectoryType() == DirectoryType.JAR_FILE){
            classLocator = new ClassLocatorForJarFile();
        }

        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());
        Set<ServiceDetails<?>> serviceDetails = scanningService.mapServices(locatedClasses);
        System.out.println(serviceDetails.toString());


    }
}