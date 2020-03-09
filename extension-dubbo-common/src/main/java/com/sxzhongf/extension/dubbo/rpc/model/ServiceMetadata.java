package com.sxzhongf.extension.dubbo.rpc.model;

import com.sxzhongf.extension.dubbo.common.ServiceDescriptor;
import com.sxzhongf.extension.dubbo.common.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Notice, this class currently has no usage inside Dubbo.
 *
 * data related to service level such as name, version, classloader of business service,
 * security info, etc. Also with a AttributeMap for extension.
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ServiceMetadata extends ServiceDescriptor {

    private String defaultGroup;
    private Class<?> serviceType;

    private Object target;

    /* will be transferred to remote side */
    private final Map<String, Object> attachments = new ConcurrentHashMap<String, Object>();
    /* used locally*/
    private final Map<String, Object> attributeMap = new ConcurrentHashMap<String, Object>();

    public ServiceMetadata(String serviceInterfaceName, String group, String version, Class<?> serviceType) {
        this.serviceInterfaceName = serviceInterfaceName;
        this.defaultGroup = group;
        this.group = group;
        this.version = version;
        this.serviceKey = URL.buildKey(serviceInterfaceName, group, version);
        this.serviceType = serviceType;
    }

    public ServiceMetadata() {
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public Object getAttribute(String key) {
        return attributeMap.get(key);
    }

    public void addAttribute(String key, Object value) {
        this.attributeMap.put(key, value);
    }

    public void addAttachment(String key, Object value) {
        this.attributeMap.put(key, value);
    }

    public Class<?> getServiceType() {
        return serviceType;
    }

    public String getDefaultGroup() {
        return defaultGroup;
    }

    public void setDefaultGroup(String defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public void setServiceType(Class<?> serviceType) {
        this.serviceType = serviceType;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
