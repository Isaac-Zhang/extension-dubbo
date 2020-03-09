package com.sxzhongf.extension.dubbo.config.context;

import com.sxzhongf.extension.dubbo.common.config.Configuration;
import com.sxzhongf.extension.dubbo.config.AbstractConfig;

import java.util.Map;

/**
 * This class receives an {@link AbstractConfig} and exposes its attributes through {@link Configuration}
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ConfigConfigurationAdapter implements Configuration {

    private Map<String, String> metaData;

    public ConfigConfigurationAdapter(AbstractConfig config) {
        this.metaData = config.getMetaData();
    }

    @Override
    public Object getInternalProperty(String key) {
        return metaData.get(key);
    }

}

