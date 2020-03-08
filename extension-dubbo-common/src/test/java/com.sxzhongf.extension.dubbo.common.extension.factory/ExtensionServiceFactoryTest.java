package com.sxzhongf.extension.dubbo.common.extension.factory;

import com.sxzhongf.extension.dubbo.common.compiler.Compiler;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceFactory;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;
import org.junit.Test;

/**
 * ExtensionServiceFactoryTest
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/6
 **/
public class ExtensionServiceFactoryTest {

    @Test
    public void getExtensionFactoryTest(){
        ExtensionServiceLoader<ExtensionServiceFactory> extensionServiceLoader = ExtensionServiceLoader
                .getExtensionServiceLoader(ExtensionServiceFactory.class);
        System.out.println(extensionServiceLoader.getAdaptiveExtension());
        System.out.println(extensionServiceLoader.getDefaultExtension());
        System.out.println(extensionServiceLoader.getExtension("spi"));
    }

    @Test
    public void getExtensionCompiler(){
        ExtensionServiceLoader<Compiler> extensionServiceLoader = ExtensionServiceLoader
                .getExtensionServiceLoader(Compiler.class);
        System.out.println(extensionServiceLoader.getAdaptiveExtension());
        System.out.println(extensionServiceLoader.getDefaultExtension());
        System.out.println(extensionServiceLoader.getExtension("jdk"));
    }
}
