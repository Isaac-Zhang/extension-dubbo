package com.sxzhongf.extension.dubbo.rpc.model;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.$INVOKE;

/**
 * Replaced with {@link MethodDescriptor}
 */
@Deprecated
public class ConsumerMethodModel {
    private final Method method;
    //    private final boolean isCallBack;
//    private final boolean isFuture;
    private final String[] parameterTypes;
    private final Class<?>[] parameterClasses;
    private final Class<?> returnClass;
    private final String methodName;
    private final boolean generic;

    private final AsyncMethodInfo asyncInfo;
    private final ConcurrentMap<String, Object> attributeMap = new ConcurrentHashMap<>();


    public ConsumerMethodModel(Method method, Map<String, Object> attributes) {
        this.method = method;
        this.parameterClasses = method.getParameterTypes();
        this.returnClass = method.getReturnType();
        this.parameterTypes = this.createParamSignature(parameterClasses);
        this.methodName = method.getName();
        this.generic = methodName.equals($INVOKE) && parameterTypes != null && parameterTypes.length == 3;

        if (attributes != null) {
            ConsumerModel.AsyncMethodInfo consumerAsyncInfo = (ConsumerModel.AsyncMethodInfo) attributes.get(methodName);
            this.asyncInfo = new AsyncMethodInfo(consumerAsyncInfo);
        } else {
            asyncInfo = null;
        }

    }

    public Method getMethod() {
        return method;
    }

//    public ConcurrentMap<String, Object> getAttributeMap() {
//        return attributeMap;
//    }

    public void addAttribute(String key, Object value) {
        this.attributeMap.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributeMap.get(key);
    }


    public Class<?> getReturnClass() {
        return returnClass;
    }

    public AsyncMethodInfo getAsyncInfo() {
        return asyncInfo;
    }

    public String getMethodName() {
        return methodName;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    private String[] createParamSignature(Class<?>[] args) {
        if (args == null || args.length == 0) {
            return new String[]{};
        }
        String[] paramSig = new String[args.length];
        for (int x = 0; x < args.length; x++) {
            paramSig[x] = args[x].getName();
        }
        return paramSig;
    }


    public boolean isGeneric() {
        return generic;
    }

    public Class<?>[] getParameterClasses() {
        return parameterClasses;
    }


    public static class AsyncMethodInfo {
        private ConsumerModel.AsyncMethodInfo delegate;

        public AsyncMethodInfo(ConsumerModel.AsyncMethodInfo methodInfo) {
            this.delegate = methodInfo;
        }

        public Object getOninvokeInstance() {
            return delegate.getOninvokeInstance();
        }


        public Method getOninvokeMethod() {
            return delegate.getOninvokeMethod();
        }

        public Object getOnreturnInstance() {
            return delegate.getOnreturnInstance();
        }

        public Method getOnreturnMethod() {
            return delegate.getOnreturnMethod();
        }

        public Object getOnthrowInstance() {
            return delegate.getOnthrowInstance();
        }

        public Method getOnthrowMethod() {
            return delegate.getOnthrowMethod();
        }
    }
}
