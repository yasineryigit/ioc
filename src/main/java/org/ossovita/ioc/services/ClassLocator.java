package org.ossovita.ioc.services;

import org.ossovita.ioc.exceptions.ClassLocationException;

import java.util.Set;

public interface ClassLocator {

    Set<Class<?>> locateClasses(String directory) throws ClassLocationException;
}
