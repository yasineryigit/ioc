package org.ossovita.ioc.test;

import org.ossovita.ioc.annotations.Bean;
import org.ossovita.ioc.annotations.PostConstruct;
import org.ossovita.ioc.annotations.Service;

@CustomService
public class TestServiceOne {

    public TestServiceOne() {
    }

    @PostConstruct
    public void onInit(){
        System.out.println("creating service one");
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
