package com.sxzhongf.extension.dubbo.config;

/**
 * Constants
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public interface Constants {

    String STATUS_KEY = "status";

    String CONTEXTPATH_KEY = "contextpath";

    String LISTENER_KEY = "listener";

    String LAYER_KEY = "layer";

    /**
     * General
     */
    /**
     * Application name;
     */
    String NAME = "name";

    /**
     * Application owner name;
     */
    String OWNER = "owner";

    /**
     * Running application organization name.
     */
    String ORGANIZATION = "organization";

    /**
     * Application architecture name.
     */
    String ARCHITECTURE = "architecture";

    /**
     * Environment name
     */
    String ENVIRONMENT = "environment";

    /**
     * Test environment key.
     */
    String TEST_ENVIRONMENT = "test";

    /**
     * Development environment key.
     */
    String DEVELOPMENT_ENVIRONMENT = "develop";

    /**
     * Production environment key.
     */
    String PRODUCTION_ENVIRONMENT = "product";

    String CONFIG_CONFIGFILE_KEY = "config-file";
    String CONFIG_ENABLE_KEY = "highest-priority";
    String CONFIG_APP_CONFIGFILE_KEY = "app-config-file";

    String MULTICAST = "multicast";


    String DUBBO_IP_TO_REGISTRY = "DUBBO_IP_TO_REGISTRY";

    String DUBBO_PORT_TO_REGISTRY = "DUBBO_PORT_TO_REGISTRY";


    String DUBBO_PORT_TO_BIND = "DUBBO_PORT_TO_BIND";

    String SCOPE_NONE = "none";


    String ON_INVOKE_METHOD_KEY = "oninvoke.method";

    String ON_RETURN_METHOD_KEY = "onreturn.method";

    String ON_THROW_METHOD_KEY = "onthrow.method";

    String ON_INVOKE_INSTANCE_KEY = "oninvoke.instance";

    String ON_RETURN_INSTANCE_KEY = "onreturn.instance";

    String ON_THROW_INSTANCE_KEY = "onthrow.instance";


    // FIXME: is this still useful?
    String SHUTDOWN_TIMEOUT_KEY = "shutdown.timeout";


    String PROTOCOLS_SUFFIX = "dubbo.protocols.";


    String REGISTRIES_SUFFIX = "dubbo.registries.";

    String ZOOKEEPER_PROTOCOL = "zookeeper";

    String REGISTER_KEY = "register";
}
