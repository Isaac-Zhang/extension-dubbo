package com.sxzhongf.extension.dubbo.rpc.support;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.common.utils.StringUtils;

import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.*;

/**
 * ProtocolUtils
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ProtocolUtils {

    private ProtocolUtils() {
    }

    public static String serviceKey(URL url) {
        return serviceKey(url.getPort(), url.getPath(), url.getParameter(VERSION_KEY),
                url.getParameter(GROUP_KEY));
    }

    public static String serviceKey(int port, String serviceName, String serviceVersion, String serviceGroup) {
        StringBuilder buf = new StringBuilder();
        if (StringUtils.isNotEmpty(serviceGroup)) {
            buf.append(serviceGroup);
            buf.append("/");
        }
        buf.append(serviceName);
        if (serviceVersion != null && serviceVersion.length() > 0 && !"0.0.0".equals(serviceVersion)) {
            buf.append(":");
            buf.append(serviceVersion);
        }
        buf.append(":");
        buf.append(port);
        return buf.toString();
    }

    public static boolean isGeneric(String generic) {
        return generic != null
                && !"".equals(generic)
                && (GENERIC_SERIALIZATION_DEFAULT.equalsIgnoreCase(generic)  /* Normal generalization cal */
                || GENERIC_SERIALIZATION_NATIVE_JAVA.equalsIgnoreCase(generic) /* Streaming generalization call supporting jdk serialization */
                || GENERIC_SERIALIZATION_BEAN.equalsIgnoreCase(generic)
                || GENERIC_SERIALIZATION_PROTOBUF.equalsIgnoreCase(generic)
                || GENERIC_RAW_RETURN.equalsIgnoreCase(generic));

    }

    public static boolean isValidGenericValue(String generic) {
        return isGeneric(generic) || Boolean.FALSE.toString().equalsIgnoreCase(generic);

    }

    public static boolean isDefaultGenericSerialization(String generic) {
        return isGeneric(generic)
                && GENERIC_SERIALIZATION_DEFAULT.equalsIgnoreCase(generic);
    }

    public static boolean isJavaGenericSerialization(String generic) {
        return isGeneric(generic)
                && GENERIC_SERIALIZATION_NATIVE_JAVA.equalsIgnoreCase(generic);
    }

    public static boolean isBeanGenericSerialization(String generic) {
        return isGeneric(generic) && GENERIC_SERIALIZATION_BEAN.equals(generic);
    }

    public static boolean isProtobufGenericSerialization(String generic) {
        return isGeneric(generic) && GENERIC_SERIALIZATION_PROTOBUF.equals(generic);
    }

    public static boolean isGenericReturnRawResult(String generic) {
        return GENERIC_RAW_RETURN.equals(generic);
    }
}
