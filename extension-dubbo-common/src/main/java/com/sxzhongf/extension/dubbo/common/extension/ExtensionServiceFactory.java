package com.sxzhongf.extension.dubbo.common.extension;

/**
 * ExtensionServiceFactory
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/5
 **/
@SPI("spi")
public interface ExtensionServiceFactory {

    /**
     * Get extension.
     * <T> T表示返回值是一个泛型，传递啥，就返回啥类型的数据，而单独的T就是表示限制你传递的参数类型
     * 这个<T> T 可以传入任何类型的List
     * 参数T
     *     第一个 表示是泛型
     *     第二个 表示返回的是T类型的数据
     *     第三个 限制参数类型为T
     * @param type object type.
     * @param name object name.
     * @return object instance.
     */
    <T> T getExtensionService(Class<T> type, String name);
}
