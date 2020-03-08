package com.sxzhongf.extension.dubbo.common.compiler;

import com.sxzhongf.extension.dubbo.common.extension.Adaptive;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;

/**
 * AdaptiveCompiler. (SPI, Singleton, ThreadSafe)
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/8
 **/
@Adaptive
public class AdaptiveCompiler implements Compiler {

    private static volatile String DEFAULT_COMPILER;

    public static void setDefaultCompiler(String compiler) {
        DEFAULT_COMPILER = compiler;
    }

    @Override
    public Class<?> compile(String code, ClassLoader classLoader) {
        Compiler compiler;
        ExtensionServiceLoader<Compiler> loader = ExtensionServiceLoader.getExtensionServiceLoader(Compiler.class);
        String name = DEFAULT_COMPILER; // copy reference
        if (name != null && name.length() > 0) {
            compiler = loader.getExtension(name);
        } else {
            compiler = loader.getDefaultExtension();
        }
        return compiler.compile(code, classLoader);
    }

}
