package org.ossovita.ioc.models;

import org.ossovita.ioc.enums.DirectoryType;

public class Directory {

    private final String directory;

    private final DirectoryType directoryType;

    public Directory(String directory, DirectoryType directoryType) {
        this.directory = directory;
        this.directoryType = directoryType;
    }

    public String getDirectory() {
        return directory;
    }

    public DirectoryType getDirectoryType() {
        return directoryType;
    }
}
