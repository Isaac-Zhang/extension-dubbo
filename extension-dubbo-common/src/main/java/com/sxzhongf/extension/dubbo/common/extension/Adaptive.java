package com.sxzhongf.extension.dubbo.common.extension;

/**
 * Adaptive
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/5
 **/

import com.sxzhongf.extension.dubbo.common.URL;

import java.lang.annotation.*;

/**
 * Provide helpful information for {@link ExtensionServiceLoader} to inject dependency extension instance.
 *
 * 当 {@link Adaptive} 注解在类上时，Dubbo 不会为该类生成代理类。
 * 注解在方法（接口方法）上时，Dubbo 则会为该方法生成代理逻辑。
 * 在 Dubbo 中，仅有两个类被 Adaptive 注解了，分别是 AdaptiveCompiler 和 AdaptiveExtensionFactory。
 * 此种情况，表示拓展的加载逻辑由人工编码完成。更多时候，Adaptive 是注解在接口方法上的，表示拓展的加载逻辑需由框架自动生成。
 * @see ExtensionServiceLoader
 * @see URL
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Adaptive {
    /**
     * Decide which target extension to be injected. The name of the target extension is decided by the parameter passed
     * in the URL, and the parameter names are given by this method.
     * <p>
     * If the specified parameters are not found from {@link URL}, then the default extension will be used for
     * dependency injection (specified in its interface's {@link SPI}).
     * <p>
     * For example, given <code>String[] {"key1", "key2"}</code>:
     * <ol>
     * <li>find parameter 'key1' in URL, use its value as the extension's name</li>
     * <li>try 'key2' for extension's name if 'key1' is not found (or its value is empty) in URL</li>
     * <li>use default extension if 'key2' doesn't exist either</li>
     * <li>otherwise, throw {@link IllegalStateException}</li>
     * </ol>
     * If the parameter names are empty, then a default parameter name is generated from interface's
     * class name with the rule: divide classname from capital char into several parts, and separate the parts with
     * dot '.', for example, for {@code org.apache.dubbo.xxx.YyyInvokerWrapper}, the generated name is
     * <code>String[] {"yyy.invoker.wrapper"}</code>.
     *
     * @return parameter names in URL
     */
    String[] value() default {};

}