package com.sxzhongf.extension.dubbo.common.config.configcenter;

/**
 * ConfigChangeType
 * Config change event type
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public enum ConfigChangeType {
    /**
     * A config is created.
     */
    ADDED,

    /**
     * A config is updated.
     */
    MODIFIED,

    /**
     * A config is deleted.
     */
    DELETED
}
