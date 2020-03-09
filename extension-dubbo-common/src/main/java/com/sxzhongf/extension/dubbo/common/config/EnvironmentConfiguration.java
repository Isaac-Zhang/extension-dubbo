package com.sxzhongf.extension.dubbo.common.config;

import com.sxzhongf.extension.dubbo.common.utils.StringUtils;

/**
 * Configuration from system environment
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class EnvironmentConfiguration extends AbstractPrefixConfiguration {

    public EnvironmentConfiguration(String prefix, String id) {
        super(prefix, id);
    }

    public EnvironmentConfiguration() {
        this(null, null);
    }

    @Override
    public Object getInternalProperty(String key) {
        String value = System.getenv(key);
        if (StringUtils.isEmpty(value)) {
            value = System.getenv(StringUtils.toOSStyleKey(key));
        }
        return value;
    }

}
