package com.sxzhongf.extension.dubbo.common.config.configcenter;

import java.util.EventListener;

/**
 * ConfigurationListener for
 * Config listener, will get notified when the config it listens on changes.
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public interface ConfigurationListener extends EventListener {

    /**
     * Listener call back method. Listener gets notified by this method once there's any change happens on the config
     * the listener listens on.
     *
     * @param event config change event
     */
    void process(ConfigChangedEvent event);
}
