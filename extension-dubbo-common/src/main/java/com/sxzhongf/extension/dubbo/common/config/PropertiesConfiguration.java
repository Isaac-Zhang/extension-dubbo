package com.sxzhongf.extension.dubbo.common.config;

import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;
import com.sxzhongf.extension.dubbo.common.utils.ConfigUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * PropertiesConfiguration for
 * Configuration from system properties and dubbo.properties
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class PropertiesConfiguration extends AbstractPrefixConfiguration{

    public PropertiesConfiguration(String prefix, String id) {
        super(prefix, id);

        ExtensionServiceLoader<OrderedPropertiesProvider> propertiesProviderExtensionLoader = ExtensionServiceLoader.getExtensionServiceLoader(OrderedPropertiesProvider.class);
        Set<String> propertiesProviderNames = propertiesProviderExtensionLoader.getSupportedExtensions();
        if (propertiesProviderNames == null || propertiesProviderNames.isEmpty()) {
            return;
        }
        List<OrderedPropertiesProvider> orderedPropertiesProviders = new ArrayList<>();
        for (String propertiesProviderName : propertiesProviderNames) {
            orderedPropertiesProviders.add(propertiesProviderExtensionLoader.getExtension(propertiesProviderName));
        }

        //order the propertiesProvider according the priority descending
        orderedPropertiesProviders.sort((OrderedPropertiesProvider a, OrderedPropertiesProvider b) -> {
            return b.priority() - a.priority();
        });

        //load the default properties
        Properties properties = ConfigUtils.getProperties();

        //override the properties.
        for (OrderedPropertiesProvider orderedPropertiesProvider :
                orderedPropertiesProviders) {
            properties.putAll(orderedPropertiesProvider.initProperties());
        }

        ConfigUtils.setProperties(properties);
    }

    public PropertiesConfiguration() {
        this(null, null);
    }

    @Override
    public Object getInternalProperty(String key) {
        return ConfigUtils.getProperty(key);
    }
}
