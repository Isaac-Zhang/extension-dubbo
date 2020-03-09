package com.sxzhongf.extension.dubbo.config;

import com.sxzhongf.extension.dubbo.config.support.Parameter;

import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.METRICS_PORT;
import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.METRICS_PROTOCOL;

/**
 * MetricsConfig
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class MetricsConfig extends AbstractConfig {

    private static final long serialVersionUID = -9089919311611546383L;

    private String port;
    private String protocol;

    public MetricsConfig() {
    }

    @Parameter(key = METRICS_PORT)
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Parameter(key = METRICS_PROTOCOL)
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
