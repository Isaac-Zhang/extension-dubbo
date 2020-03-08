package com.sxzhongf.extension.dubbo.common.extension.factory;

import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceFactory;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;
import com.sxzhongf.extension.dubbo.common.extension.SPI;

/**
 * SpiExtensionServiceFactory
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/6
 **/
public class SpiExtensionServiceFactory implements ExtensionServiceFactory {
    @Override
    public <T> T getExtensionService(Class<T> type, String name) {
        // 判断传入的类型为SPI
        if (type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            // 根据传入类型获取扩展类
            ExtensionServiceLoader<T> loader = ExtensionServiceLoader.getExtensionServiceLoader(type);
            if (!loader.getSupportedExtensions().isEmpty()) {
                return loader.getAdaptiveExtension();
            }
        }
        return null;
    }
}
