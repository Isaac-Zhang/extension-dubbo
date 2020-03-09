package com.sxzhongf.extension.dubbo.common.Infra;

import com.sxzhongf.extension.dubbo.common.extension.SPI;

import java.util.Map;

/**
 * Used to interact with other systems. Typical use cases are:
 * 1. get extra attributes from underlying infrastructures related to the instance on which Dubbo is currently deploying.
 * 2. get configurations from third-party systems which maybe useful for a specific component.
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
@SPI
public interface InfraAdapter {

    /**
     * get extra attributes
     *
     * @param params application name or hostname are most likely to be used as input params.
     * @return
     */
    Map<String, String> getExtraAttributes(Map<String, String> params);

    /**
     * @param key
     * @return
     */
    String getAttribute(String key);

}
