package org.ossovita;

import org.ossovita.ioc.annotations.PostConstruct;
import org.ossovita.ioc.annotations.Service;
import org.ossovita.ioc.config.MagicConfiguration;
import org.ossovita.ioc.enums.DirectoryType;
import org.ossovita.ioc.models.Directory;
import org.ossovita.ioc.models.ServiceDetails;
import org.ossovita.ioc.services.*;
import org.ossovita.ioc.test.*;

import java.util.List;
import java.util.Set;

@Service
public class MagicInjector {

    private final OtherServiceContract otherService;
    private final ModelMapper modelMapper;

    public MagicInjector(OtherServiceContract otherService, ModelMapper modelMapper) {
        this.otherService = otherService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init(){
        System.out.println("hello. " + this.otherService.getClass().getName());
        System.out.println(this.modelMapper.map());
    }

    public static void main(String[] args) {
        run(MagicInjector.class, new MagicConfiguration()
                .annotations()
                .addCustomServiceAnnotation(CustomService.class)
                .addCustomBeanAnnotation(CustomBean.class)
                .and()
        );
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
        if(directory.getDirectoryType() == DirectoryType.JAR_FILE){
            classLocator = new ClassLocatorForJarFile();
        }

        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());

        Set<ServiceDetails<?>> mappedServices = scanningService.mapServices(locatedClasses);
        List<ServiceDetails<?>> serviceDetails = instantiationService.instantiateServiceAndBeans(mappedServices);


    }
}