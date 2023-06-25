package org.ossovita.ioc.config;

import org.ossovita.ioc.config.configurations.CustomAnnotationsConfiguration;

public class MagicConfiguration {

    private final CustomAnnotationsConfiguration annotations;

    public MagicConfiguration() {
        this.annotations = new CustomAnnotationsConfiguration(this);
    }

    public CustomAnnotationsConfiguration annotations(){
        return this.annotations;
    }

    public MagicConfiguration build(){
        return this;
    }


}
