package com.sxzhongf.extension.dubbo.rpc.model;

import com.sxzhongf.extension.dubbo.common.extension.SPI;

/**
 * ApplicationInitListener for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
@SPI
public interface ApplicationInitListener {
    /**
     * init the application
     */
    void init();
}