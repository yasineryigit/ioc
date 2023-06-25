package org.ossovita.ioc.services;

import org.ossovita.ioc.enums.DirectoryType;
import org.ossovita.ioc.models.Directory;

import java.io.File;

public class DirectoryResolverImpl implements DirectoryResolver {

    private static final String JAR_FILE_EXTENSION = ".jar";

    @Override
    public Directory resolveDirectory(Class<?> startupClass) {
        final String directory = this.getDirectory(startupClass);

        return new Directory(directory, this.getDirectoryType(directory));
    }

    private String getDirectory(Class<?> cls) {
        return cls.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private DirectoryType getDirectoryType(String directory) {
        File file = new File(directory);

        if (!file.isDirectory() && directory.endsWith(JAR_FILE_EXTENSION)) {
            return DirectoryType.DIRECTORY;
        }
        return DirectoryType.DIRECTORY;
    }
}
