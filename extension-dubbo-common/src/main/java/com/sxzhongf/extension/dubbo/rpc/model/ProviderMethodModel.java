package com.sxzhongf.extension.dubbo.rpc.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Replaced with {@link MethodDescriptor}
 */
@Deprecated
public class ProviderMethodModel {
    private final Method method;
    private final String methodName;
    private final Class<?>[] parameterClasses;
    private final String[] methodArgTypes;
    private final Type[] genericParameterTypes;
    private final ConcurrentMap<String, Object> attributeMap = new ConcurrentHashMap<>();

    public ProviderMethodModel(Method method) {
        this.method = method;
        this.methodName = method.getName();
        this.parameterClasses = method.getParameterTypes();
        this.methodArgTypes = getArgTypes(method);
        this.genericParameterTypes = method.getGenericParameterTypes();
    }

    public Method getMethod() {
        return method;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getMethodArgTypes() {
        return methodArgTypes;
    }

    public ConcurrentMap<String, Object> getAttributeMap() {
        return attributeMap;
    }

    private static String[] getArgTypes(Method method) {
        String[] methodArgTypes = new String[0];
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            methodArgTypes = new String[parameterTypes.length];
            int index = 0;
            for (Class<?> paramType : parameterTypes) {
                methodArgTypes[index++] = paramType.getName();
            }
        }
        return methodArgTypes;
    }

    public Class<?>[] getParameterClasses() {
        return parameterClasses;
    }

    public Type[] getGenericParameterTypes() {
        return genericParameterTypes;
    }
}
