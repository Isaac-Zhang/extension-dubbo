package com.sxzhongf.extension.dubbo.common.extension;

import com.sxzhongf.extension.dubbo.common.logger.Logger;
import com.sxzhongf.extension.dubbo.common.logger.LoggerFactory;

import java.util.regex.Pattern;

/**
 * ExtensionServiceLoader for Loading extension class
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/5
 **/
public class ExtensionServiceLoader<T> {

    private static final Logger logger = LoggerFactory.getLogger(ExtensionServiceLoader.class);

    private static final String SERVICES_DIRECTORY = "META-INF/services/";

    private static final String DUBBO_DIRECTORY = "META-INF/extension/dubbo/";

    private static final String DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");
}
