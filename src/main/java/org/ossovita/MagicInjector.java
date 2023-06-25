package org.ossovita;

import org.ossovita.ioc.config.MagicConfiguration;
import org.ossovita.ioc.enums.DirectoryType;
import org.ossovita.ioc.models.Directory;
import org.ossovita.ioc.services.ClassLocator;
import org.ossovita.ioc.services.ClassLocatorForDirectory;
import org.ossovita.ioc.services.ClassLocatorForJarFile;
import org.ossovita.ioc.services.DirectoryResolverImpl;
import java.util.Set;


public class MagicInjector {

    public static void main(String[] args) {
        run(MagicInjector.class);

    }

    public static void run(Class<?> startupClass) {
        run(startupClass, new MagicConfiguration());
    }

    public static void run(Class<?> startupClass, MagicConfiguration configuration) {
        System.out.println("Dir is ");
        Directory directory = new DirectoryResolverImpl().resolveDirectory(startupClass);

        ClassLocator classLocator = new ClassLocatorForDirectory();
        if(directory.getDirectoryType() == DirectoryType.JAR_FILE){
            classLocator = new ClassLocatorForJarFile();
        }

        Set<Class<?>> classes = classLocator.locateClasses(directory.getDirectory());
        System.out.println(classes);
    }
}