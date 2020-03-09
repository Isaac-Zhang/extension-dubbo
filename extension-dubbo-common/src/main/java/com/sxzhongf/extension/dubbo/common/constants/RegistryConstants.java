package com.sxzhongf.extension.dubbo.common.constants;

/**
 * RegistryConstants
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public interface RegistryConstants {

    String REGISTRY_KEY = "registry";

    String REGISTRY_PROTOCOL = "registry";

    String DYNAMIC_KEY = "dynamic";

    String CATEGORY_KEY = "category";

    String PROVIDERS_CATEGORY = "providers";

    String CONSUMERS_CATEGORY = "consumers";

    String ROUTERS_CATEGORY = "routers";

    String DYNAMIC_ROUTERS_CATEGORY = "dynamicrouters";

    String DEFAULT_CATEGORY = PROVIDERS_CATEGORY;

    String CONFIGURATORS_CATEGORY = "configurators";

    String DYNAMIC_CONFIGURATORS_CATEGORY = "dynamicconfigurators";

    String APP_DYNAMIC_CONFIGURATORS_CATEGORY = "appdynamicconfigurators";

    String ROUTERS_SUFFIX = ".routers";

    String EMPTY_PROTOCOL = "empty";

    String ROUTE_PROTOCOL = "route";

    String OVERRIDE_PROTOCOL = "override";

    String COMPATIBLE_CONFIG_KEY = "compatible_config";

    /**
     * The parameter key of Dubbo Registry type
     *
     * @since 2.7.5
     */
    String REGISTRY_TYPE_KEY = "registry-type";

    /**
     * The parameter value of Service-Oriented Registry type
     *
     * @since 2.7.5
     */
    String SERVICE_REGISTRY_TYPE = "service";

    /**
     * The protocol for Service Discovery
     *
     * @since 2.7.5
     */
    String SERVICE_REGISTRY_PROTOCOL = "service-discovery-registry";

    /**
     * The parameter key of the subscribed service names for Service-Oriented Registry
     *
     * @since 2.7.5
     */
    String SUBSCRIBED_SERVICE_NAMES_KEY = "subscribed-services";

    String PROVIDED_BY = "provided-by";

    /**
     * The request size of service instances
     *
     * @since 2.7.5
     */
    String INSTANCES_REQUEST_SIZE_KEY = "instances-request-size";

    /**
     * The default request size of service instances
     */
    int DEFAULT_INSTANCES_REQUEST_SIZE = 100;

    String ACCEPTS_KEY = "accepts";

    String REGISTRY_ZONE = "registry_zone";
    String REGISTRY_ZONE_FORCE = "registry_zone_force";
    String ZONE_KEY = "zone";
}
