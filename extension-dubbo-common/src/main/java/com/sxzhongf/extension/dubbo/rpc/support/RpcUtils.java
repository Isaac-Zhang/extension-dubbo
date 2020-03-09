package com.sxzhongf.extension.dubbo.rpc.support;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.common.logger.Logger;
import com.sxzhongf.extension.dubbo.common.logger.LoggerFactory;
import com.sxzhongf.extension.dubbo.common.utils.ReflectUtils;
import com.sxzhongf.extension.dubbo.common.utils.StringUtils;
import com.sxzhongf.extension.dubbo.rpc.Invocation;
import com.sxzhongf.extension.dubbo.rpc.InvokeMode;
import com.sxzhongf.extension.dubbo.rpc.RpcInvocation;
import com.sxzhongf.extension.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.$INVOKE;
import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.$INVOKE_ASYNC;
import static com.sxzhongf.extension.dubbo.rpc.Constants.*;

/**
 * RpcUtils
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class RpcUtils {

    private static final Logger logger = LoggerFactory.getLogger(RpcUtils.class);
    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    public static Class<?> getReturnType(Invocation invocation) {
        try {
            if (invocation != null && invocation.getInvoker() != null
                    && invocation.getInvoker().getUrl() != null
                    && invocation.getInvoker().getInterface() != GenericService.class
                    && !invocation.getMethodName().startsWith("$")) {
                String service = invocation.getInvoker().getUrl().getServiceInterface();
                if (StringUtils.isNotEmpty(service)) {
                    Class<?> invokerInterface = invocation.getInvoker().getInterface();
                    Class<?> cls = invokerInterface != null ? ReflectUtils.forName(invokerInterface.getClassLoader(), service)
                            : ReflectUtils.forName(service);
                    Method method = cls.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    if (method.getReturnType() == void.class) {
                        return null;
                    }
                    return method.getReturnType();
                }
            }
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
        return null;
    }

    public static Type[] getReturnTypes(Invocation invocation) {
        try {
            if (invocation != null && invocation.getInvoker() != null
                    && invocation.getInvoker().getUrl() != null
                    && invocation.getInvoker().getInterface() != GenericService.class
                    && !invocation.getMethodName().startsWith("$")) {
                String service = invocation.getInvoker().getUrl().getServiceInterface();
                if (StringUtils.isNotEmpty(service)) {
                    Class<?> invokerInterface = invocation.getInvoker().getInterface();
                    Class<?> cls = invokerInterface != null ? ReflectUtils.forName(invokerInterface.getClassLoader(), service)
                            : ReflectUtils.forName(service);
                    Method method = cls.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    if (method.getReturnType() == void.class) {
                        return null;
                    }
                    return ReflectUtils.getReturnTypes(method);
                }
            }
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
        return null;
    }

    public static Long getInvocationId(Invocation inv) {
        String id = inv.getAttachment(ID_KEY);
        return id == null ? null : new Long(id);
    }

    /**
     * Idempotent operation: invocation id will be added in async operation by default
     *
     * @param url
     * @param inv
     */
    public static void attachInvocationIdIfAsync(URL url, Invocation inv) {
        if (isAttachInvocationId(url, inv) && getInvocationId(inv) == null && inv instanceof RpcInvocation) {
            ((RpcInvocation) inv).setAttachment(ID_KEY, String.valueOf(INVOKE_ID.getAndIncrement()));
        }
    }

    private static boolean isAttachInvocationId(URL url, Invocation invocation) {
        String value = url.getMethodParameter(invocation.getMethodName(), AUTO_ATTACH_INVOCATIONID_KEY);
        if (value == null) {
            // add invocationid in async operation by default
            return isAsync(url, invocation);
        } else if (Boolean.TRUE.toString().equalsIgnoreCase(value)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getMethodName(Invocation invocation) {
        if ($INVOKE.equals(invocation.getMethodName())
                && invocation.getArguments() != null
                && invocation.getArguments().length > 0
                && invocation.getArguments()[0] instanceof String) {
            return (String) invocation.getArguments()[0];
        }
        return invocation.getMethodName();
    }

    public static Object[] getArguments(Invocation invocation) {
        if ($INVOKE.equals(invocation.getMethodName())
                && invocation.getArguments() != null
                && invocation.getArguments().length > 2
                && invocation.getArguments()[2] instanceof Object[]) {
            return (Object[]) invocation.getArguments()[2];
        }
        return invocation.getArguments();
    }

    public static Class<?>[] getParameterTypes(Invocation invocation) {
        if ($INVOKE.equals(invocation.getMethodName())
                && invocation.getArguments() != null
                && invocation.getArguments().length > 1
                && invocation.getArguments()[1] instanceof String[]) {
            String[] types = (String[]) invocation.getArguments()[1];
            if (types == null) {
                return new Class<?>[0];
            }
            Class<?>[] parameterTypes = new Class<?>[types.length];
            for (int i = 0; i < types.length; i++) {
                parameterTypes[i] = ReflectUtils.forName(types[0]);
            }
            return parameterTypes;
        }
        return invocation.getParameterTypes();
    }

    public static boolean isAsync(URL url, Invocation inv) {
        boolean isAsync;
        if (Boolean.TRUE.toString().equals(inv.getAttachment(ASYNC_KEY))) {
            isAsync = true;
        } else {
            isAsync = url.getMethodParameter(getMethodName(inv), ASYNC_KEY, false);
        }
        return isAsync;
    }

    public static boolean isReturnTypeFuture(Invocation inv) {
        Class<?> clazz;
        if (inv instanceof RpcInvocation) {
            clazz = ((RpcInvocation) inv).getReturnType();
        } else {
            clazz = getReturnType(inv);
        }
        return (clazz != null && CompletableFuture.class.isAssignableFrom(clazz)) || isGenericAsync(inv);
    }

    public static boolean isGenericAsync(Invocation inv) {
        return $INVOKE_ASYNC.equals(inv.getMethodName());
    }

    public static boolean isGenericCall(String path, String method) {
        return $INVOKE.equals(method) || $INVOKE_ASYNC.equals(method);
    }

    public static boolean isEcho(String path, String method) {
        return $ECHO.equals(method);
    }

    public static InvokeMode getInvokeMode(URL url, Invocation inv) {
        if (isReturnTypeFuture(inv)) {
            return InvokeMode.FUTURE;
        } else if (isAsync(url, inv)) {
            return InvokeMode.ASYNC;
        } else {
            return InvokeMode.SYNC;
        }
    }

    public static boolean isOneway(URL url, Invocation inv) {
        boolean isOneway;
        if (Boolean.FALSE.toString().equals(inv.getAttachment(RETURN_KEY))) {
            isOneway = true;
        } else {
            isOneway = !url.getMethodParameter(getMethodName(inv), RETURN_KEY, true);
        }
        return isOneway;
    }
}
