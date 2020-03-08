package com.sxzhongf.extension.dubbo.common;

/**
 * Resetable
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/8
 **/
public interface Resetable {

    /**
     * reset.
     *
     * @param url
     */
    void reset(URL url);

}
