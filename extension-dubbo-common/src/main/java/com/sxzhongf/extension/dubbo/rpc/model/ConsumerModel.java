package com.sxzhongf.extension.dubbo.rpc.model;

import com.sxzhongf.extension.dubbo.common.utils.Assert;
import com.sxzhongf.extension.dubbo.common.utils.CollectionUtils;
import com.sxzhongf.extension.dubbo.config.ReferenceConfigBase;

import java.lang.reflect.Method;
import java.util.*;

/**
 * This model is bound to your reference's configuration,
 * for example, group, version or method level configuration.
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ConsumerModel {
    private final String serviceKey;
    private final ServiceDescriptor serviceModel;
    private final ReferenceConfigBase<?> referenceConfig;

    private Object proxyObject;

    private final Map<String, AsyncMethodInfo> methodConfigs = new HashMap<>();

    /**
     *  This constructor create an instance of ConsumerModel and passed objects should not be null.
     *  If service name, service instance, proxy object,methods should not be null. If these are null
     *  then this constructor will throw {@link IllegalArgumentException}
     * @param serviceKey Name of the service.
     * @param proxyObject  Proxy object.
     * @param attributes Attributes of methods.
     */
    public ConsumerModel(String serviceKey
            , Object proxyObject
            , ServiceDescriptor serviceModel
            , ReferenceConfigBase<?> referenceConfig
            , Map<String, Object> attributes) {

        Assert.notEmptyString(serviceKey, "Service name can't be null or blank");
//        Assert.notNull(proxyObject, "Proxy object can't be null");

        this.serviceKey = serviceKey;
        this.proxyObject = proxyObject;
        this.serviceModel = serviceModel;
        this.referenceConfig = referenceConfig;

        if (CollectionUtils.isNotEmptyMap(attributes)) {
            attributes.forEach((method, object) -> {
                methodConfigs.put(method, (AsyncMethodInfo) object);
            });
        }
    }

    /**
     * Return the proxy object used by called while creating instance of ConsumerModel
     * @return
     */
    public Object getProxyObject() {
        return proxyObject;
    }

    public void setProxyObject(Object proxyObject) {
        this.proxyObject = proxyObject;
    }

    /**
     * Return all method models for the current service
     *
     * @return method model list
     */
    public Set<MethodDescriptor> getAllMethods() {
        return serviceModel.getAllMethods();
    }

    public Class<?> getServiceInterfaceClass() {
        return serviceModel.getServiceInterfaceClass();
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public AsyncMethodInfo getMethodConfig(String methodName) {
        return methodConfigs.get(methodName);
    }

    public ServiceDescriptor getServiceModel() {
        return serviceModel;
    }

    public ReferenceConfigBase getReferenceConfig() {
        return referenceConfig;
    }

    public static class AsyncMethodInfo {
        // callback instance when async-call is invoked
        private Object oninvokeInstance;

        // callback method when async-call is invoked
        private Method oninvokeMethod;

        // callback instance when async-call is returned
        private Object onreturnInstance;

        // callback method when async-call is returned
        private Method onreturnMethod;

        // callback instance when async-call has exception thrown
        private Object onthrowInstance;

        // callback method when async-call has exception thrown
        private Method onthrowMethod;

        public Object getOninvokeInstance() {
            return oninvokeInstance;
        }

        public void setOninvokeInstance(Object oninvokeInstance) {
            this.oninvokeInstance = oninvokeInstance;
        }

        public Method getOninvokeMethod() {
            return oninvokeMethod;
        }

        public void setOninvokeMethod(Method oninvokeMethod) {
            this.oninvokeMethod = oninvokeMethod;
        }

        public Object getOnreturnInstance() {
            return onreturnInstance;
        }

        public void setOnreturnInstance(Object onreturnInstance) {
            this.onreturnInstance = onreturnInstance;
        }

        public Method getOnreturnMethod() {
            return onreturnMethod;
        }

        public void setOnreturnMethod(Method onreturnMethod) {
            this.onreturnMethod = onreturnMethod;
        }

        public Object getOnthrowInstance() {
            return onthrowInstance;
        }

        public void setOnthrowInstance(Object onthrowInstance) {
            this.onthrowInstance = onthrowInstance;
        }

        public Method getOnthrowMethod() {
            return onthrowMethod;
        }

        public void setOnthrowMethod(Method onthrowMethod) {
            this.onthrowMethod = onthrowMethod;
        }
    }


    /* *************** Start, metadata compatible **************** */

    private ServiceMetadata serviceMetadata;
    private Map<Method, ConsumerMethodModel> methodModels = new IdentityHashMap<Method, ConsumerMethodModel>();

    public ConsumerModel(String serviceKey
            , Object proxyObject
            , ServiceDescriptor serviceModel
            , ReferenceConfigBase<?> referenceConfig
            , Map<String, Object> attributes
            , ServiceMetadata metadata) {

        this(serviceKey, proxyObject, serviceModel, referenceConfig, attributes);
        this.serviceMetadata = metadata;

        for (Method method : metadata.getServiceType().getMethods()) {
            methodModels.put(method, new ConsumerMethodModel(method, attributes));
        }
    }

    public ClassLoader getClassLoader() {
        return serviceMetadata.getServiceType().getClassLoader();
    }

    /**
     * @return serviceMetadata
     */
    public ServiceMetadata getServiceMetadata() {
        return serviceMetadata;
    }

    /**
     * Return method model for the given method on consumer side
     *
     * @param method method object
     * @return method model
     */
    public ConsumerMethodModel getMethodModel(Method method) {
        return methodModels.get(method);
    }

    /**
     * Return method model for the given method on consumer side
     *
     * @param method method object
     * @return method model
     */
    public ConsumerMethodModel getMethodModel(String method) {
        Optional<Map.Entry<Method, ConsumerMethodModel>> consumerMethodModelEntry = methodModels.entrySet().stream().filter(entry -> entry.getKey().getName().equals(method)).findFirst();
        return consumerMethodModelEntry.map(Map.Entry::getValue).orElse(null);
    }

    /**
     * @param method   metodName
     * @param argsType method arguments type
     * @return
     */
    public ConsumerMethodModel getMethodModel(String method, String[] argsType) {
        Optional<ConsumerMethodModel> consumerMethodModel = methodModels.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(method))
                .map(Map.Entry::getValue).filter(methodModel -> Arrays.equals(argsType, methodModel.getParameterTypes()))
                .findFirst();
        return consumerMethodModel.orElse(null);
    }

    /**
     * Return all method models for the current service
     *
     * @return method model list
     */
    public List<ConsumerMethodModel> getAllMethodModels() {
        return new ArrayList<>(methodModels.values());
    }

    public String getServiceName() {
        return this.serviceMetadata.getServiceKey();
    }


}
