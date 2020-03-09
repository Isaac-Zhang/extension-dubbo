package com.sxzhongf.extension.dubbo.common.config;

/**
 * FIXME: is this really necessary? PropertiesConfiguration should have already covered this:
 * @see PropertiesConfiguration
 * @See ConfigUtils#getProperty(String)
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class SystemConfiguration extends AbstractPrefixConfiguration {

    public SystemConfiguration(String prefix, String id) {
        super(prefix, id);
    }

    public SystemConfiguration() {
        this(null, null);
    }

    @Override
    public Object getInternalProperty(String key) {
        return System.getProperty(key);
    }

}
