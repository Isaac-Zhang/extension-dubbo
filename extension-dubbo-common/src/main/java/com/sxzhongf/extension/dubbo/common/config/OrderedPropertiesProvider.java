package com.sxzhongf.extension.dubbo.common.config;

import com.sxzhongf.extension.dubbo.common.extension.SPI;

import java.util.Properties;

/**
 * OrderedPropertiesProvider for
 * The smaller value, the higher priority
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
@SPI
public interface OrderedPropertiesProvider {
    /**
     * order
     *
     * @return
     */
    int priority();

    /**
     * load the properties
     *
     * @return
     */
    Properties initProperties();
}
