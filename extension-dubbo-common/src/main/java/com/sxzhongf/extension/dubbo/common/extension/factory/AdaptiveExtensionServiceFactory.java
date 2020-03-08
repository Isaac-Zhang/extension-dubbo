package com.sxzhongf.extension.dubbo.common.extension.factory;

import com.sxzhongf.extension.dubbo.common.extension.Adaptive;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceFactory;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdaptiveExtensionServiceFactory
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/6
 **/
@Adaptive
public class AdaptiveExtensionServiceFactory implements ExtensionServiceFactory {

    private final List<ExtensionServiceFactory> factories;

    public AdaptiveExtensionServiceFactory() {
        ExtensionServiceLoader<ExtensionServiceFactory> loader = ExtensionServiceLoader
                .getExtensionServiceLoader(ExtensionServiceFactory.class);
        List<ExtensionServiceFactory> list = new ArrayList<>();
        loader.getSupportedExtensions().forEach(s -> {
            list.add(loader.getExtension(s));
        });
        this.factories = Collections.unmodifiableList(list);
    }

    @Override
    public <T> T getExtensionService(Class<T> type, String name) {
        for (ExtensionServiceFactory factory : factories) {
            // 根据类型和名称获取扩展类
            T extension = factory.getExtensionService(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }
}
