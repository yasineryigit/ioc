package org.ossovita.ioc.test;

import org.ossovita.ioc.annotations.Bean;
import org.ossovita.ioc.annotations.PostConstruct;
import org.ossovita.ioc.annotations.Service;

@CustomService
public class TestServiceOne {

    public TestServiceOne() {
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
