package com.sxzhongf.extension.dubbo.common.config.configcenter;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;
import com.sxzhongf.extension.dubbo.common.extension.SPI;

import static com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader.getExtensionServiceLoader;

/**
 * DynamicConfigurationFactory
 * The factory interface to create the instance of {@link DynamicConfiguration}
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
@SPI("nop") // 2.7.5 change the default SPI implementation
public interface DynamicConfigurationFactory {

    DynamicConfiguration getDynamicConfiguration(URL url);

    /**
     * Get an instance of {@link DynamicConfigurationFactory} by the specified name. If not found, take the default
     * extension of {@link DynamicConfigurationFactory}
     *
     * @param name the name of extension of {@link DynamicConfigurationFactory}
     * @return non-null
     * @see 2.7.4
     */
    static DynamicConfigurationFactory getDynamicConfigurationFactory(String name) {
        Class<DynamicConfigurationFactory> factoryClass = DynamicConfigurationFactory.class;
        ExtensionServiceLoader<DynamicConfigurationFactory> loader = getExtensionServiceLoader(factoryClass);
        return loader.getOrDefaultExtension(name);
    }
}
