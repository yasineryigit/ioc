package org.ossovita.ioc.config;

import org.ossovita.ioc.config.configurations.CustomAnnotationsConfiguration;
import org.ossovita.ioc.config.configurations.InstantiationConfiguration;

public class MagicConfiguration {

    private final CustomAnnotationsConfiguration annotations;

    private final InstantiationConfiguration instantiations;

    public MagicConfiguration() {
        this.annotations = new CustomAnnotationsConfiguration(this);
        this.instantiations=new InstantiationConfiguration(this);
    }

    public CustomAnnotationsConfiguration annotations(){
        return this.annotations;
    }

    public InstantiationConfiguration instantiations() {
        return this.instantiations;
    }

    public MagicConfiguration build(){
        return this;
    }


}
