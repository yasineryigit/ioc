package org.ossovita.ioc.config.configurations;

import org.ossovita.ioc.config.BaseSubConfiguration;
import org.ossovita.ioc.config.MagicConfiguration;
import org.ossovita.ioc.constants.Constants;

public class InstantiationConfiguration extends BaseSubConfiguration {

    private int maximumAllowedIterations;


    public InstantiationConfiguration(MagicConfiguration parentConfig) {
        super(parentConfig);
        this.maximumAllowedIterations = Constants.MAX_NUMBER_OF_INSTANTIATION_ITERATIONS;
    }

    public InstantiationConfiguration setMaximumNumberOfAllowedIteration(int num){
        this.maximumAllowedIterations =num;
        return this;
    }

    public int getMaximumAllowedIterations() {
        return maximumAllowedIterations;
    }
}
