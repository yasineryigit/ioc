package org.ossovita.ioc.services;

import org.ossovita.ioc.models.Directory;

public interface DirectoryResolver {

    Directory resolveDirectory(Class<?> startupClass);

}
