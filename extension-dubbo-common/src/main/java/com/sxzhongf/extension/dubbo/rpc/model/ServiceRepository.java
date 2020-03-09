package com.sxzhongf.extension.dubbo.rpc.model;

import com.sxzhongf.extension.dubbo.common.context.FrameworkExt;
import com.sxzhongf.extension.dubbo.common.context.LifecycleAdapter;
import com.sxzhongf.extension.dubbo.common.extension.ExtensionServiceLoader;
import com.sxzhongf.extension.dubbo.common.utils.CollectionUtils;
import com.sxzhongf.extension.dubbo.config.ReferenceConfigBase;
import com.sxzhongf.extension.dubbo.config.ServiceConfigBase;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ServiceRepository for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ServiceRepository extends LifecycleAdapter implements FrameworkExt {

    public static final String NAME = "repository";

    // services
    private ConcurrentMap<String, ServiceDescriptor> services = new ConcurrentHashMap<>();

    // consumers
    private ConcurrentMap<String, ConsumerModel> consumers = new ConcurrentHashMap<>();

    // providers
    private ConcurrentMap<String, ProviderModel> providers = new ConcurrentHashMap<>();

    public ServiceRepository() {
        Set<BuiltinServiceDetector> builtinServices
                = ExtensionServiceLoader.getExtensionServiceLoader(BuiltinServiceDetector.class).getSupportedExtensionInstances();
        if (CollectionUtils.isNotEmpty(builtinServices)) {
            for (BuiltinServiceDetector service : builtinServices) {
                registerService(service.getService());
            }
        }
    }

    public ServiceDescriptor registerService(Class<?> interfaceClazz) {
        return services.computeIfAbsent(interfaceClazz.getName(),
                _k -> new ServiceDescriptor(interfaceClazz));
    }

    /**
     * See {@link #registerService(Class)}
     * <p>
     * we assume:
     * 1. services with different interfaces are not allowed to have the same path.
     * 2. services share the same interface but has different group/version can share the same path.
     * 3. path's default value is the name of the interface.
     *
     * @param path
     * @param interfaceClass
     * @return
     */
    public ServiceDescriptor registerService(String path, Class<?> interfaceClass) {
        ServiceDescriptor serviceDescriptor = registerService(interfaceClass);
        // if path is different with interface name, add extra path mapping
        if (!interfaceClass.getName().equals(path)) {
            services.putIfAbsent(path, serviceDescriptor);
        }
        return serviceDescriptor;
    }

    public void registerConsumer(String serviceKey,
                                 Map<String, Object> attributes,
                                 ServiceDescriptor serviceModel,
                                 ReferenceConfigBase<?> rc,
                                 Object proxy,
                                 ServiceMetadata serviceMetadata) {
        consumers.computeIfAbsent(
                serviceKey,
                _k -> new ConsumerModel(
                        serviceMetadata.getServiceKey(),
                        proxy,
                        serviceModel,
                        rc,
                        attributes,
                        serviceMetadata
                )
        );
    }

    public void registerProvider(String serviceKey,
                                 Object serviceInstance,
                                 ServiceDescriptor serviceModel,
                                 ServiceConfigBase<?> serviceConfig,
                                 ServiceMetadata serviceMetadata) {
        providers.computeIfAbsent(
                serviceKey,
                _k -> new ProviderModel(
                        serviceKey,
                        serviceInstance,
                        serviceModel,
                        serviceConfig,
                        serviceMetadata
                )
        );
    }

    public List<ServiceDescriptor> getAllServices() {
        return Collections.unmodifiableList(new ArrayList<>(services.values()));
    }

    public ServiceDescriptor lookupService(String interfaceName) {
        return services.get(interfaceName);
    }

    public MethodDescriptor lookupMethod(String interfaceName, String methodName) {
        ServiceDescriptor serviceDescriptor = lookupService(interfaceName);
        if (serviceDescriptor == null) {
            return null;
        }
        Set<MethodDescriptor> methods = serviceDescriptor.getMethods(methodName);
        if (CollectionUtils.isEmpty(methods)) {
            return null;
        }
        return methods.iterator().next();
    }

    public List<ProviderModel> getExportedServices() {
        return Collections.unmodifiableList(new ArrayList<>(providers.values()));
    }

    public ProviderModel lookupExportedService(String serviceKey) {
        return providers.get(serviceKey);
    }

    public List<ConsumerModel> getReferredServices() {
        return Collections.unmodifiableList(new ArrayList<>(consumers.values()));
    }

    public ConsumerModel lookupReferredService(String serviceKey) {
        return consumers.get(serviceKey);
    }

    @Override
    public void destroy() throws IllegalStateException {
        // currently works for unit test
        services.clear();
        consumers.clear();
        providers.clear();
    }
}
