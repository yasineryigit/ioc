package org.ossovita.ioc.test;

import org.ossovita.ioc.annotations.*;

@Service
public class TestServiceTwo {

    private final TestServiceOne serviceOne;


    public TestServiceTwo(TestServiceOne serviceOne) {
        this.serviceOne = serviceOne;

    }

    @Autowired
    public TestServiceTwo() {
        this.serviceOne=null;
    }

    @PostConstruct
    private void onInit(){
        System.out.println("testing post for service 2 ");
    }

    @PreDestroy
    public String onDestroy(){
        return null;
    }

    @Bean
    public OtherService otherservice(){
        return new OtherService();
    }

}
