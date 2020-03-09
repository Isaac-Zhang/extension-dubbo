package com.sxzhongf.extension.dubbo.common.extension;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.common.logger.Logger;
import com.sxzhongf.extension.dubbo.common.logger.LoggerFactory;
import com.sxzhongf.extension.dubbo.common.utils.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Code generator for Adaptive class
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/5
 **/
public class AdaptiveClassCodeGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AdaptiveClassCodeGenerator.class);

    //FIXME: Isaac changed the classname from 'org.apache.dubbo.rpc.Invocation'
    private static final String CLASSNAME_INVOCATION = "com.sxzhongf.extension.dubbo.rpc.Invocation";

    private static final String CODE_PACKAGE = "package %s;\n";

    private static final String CODE_IMPORTS = "import %s;\n";

    private static final String CODE_CLASS_DECLARATION = "public class %s$Adaptive implements %s {\n";

    private static final String CODE_METHOD_DECLARATION = "public %s %s(%s) %s {\n%s}\n";

    private static final String CODE_METHOD_ARGUMENT = "%s arg%d";

    private static final String CODE_METHOD_THROWS = "throws %s";

    private static final String CODE_UNSUPPORTED = "throw new UnsupportedOperationException(\"The method %s of interface %s is not adaptive method!\");\n";

    private static final String CODE_URL_NULL_CHECK = "if (arg%d == null) throw new IllegalArgumentException(\"url == null\");\n%s url = arg%d;\n";

    private static final String CODE_EXT_NAME_ASSIGNMENT = "String extName = %s;\n";

    private static final String CODE_EXT_NAME_NULL_CHECK = "if(extName == null) "
            + "throw new IllegalStateException(\"Failed to get extension (%s) name from url (\" + url.toString() + \") use keys(%s)\");\n";

    private static final String CODE_INVOCATION_ARGUMENT_NULL_CHECK = "if (arg%d == null) throw new IllegalArgumentException(\"invocation == null\"); "
            + "String methodName = arg%d.getMethodName();\n";


    private static final String CODE_EXTENSION_ASSIGNMENT = "%s extension = (%<s)%s.getExtensionLoader(%s.class).getExtension(extName);\n";

    private static final String CODE_EXTENSION_METHOD_INVOKE_ARGUMENT = "arg%d";

    private final Class<?> type;

    private String defaultExtName;

    public AdaptiveClassCodeGenerator(Class<?> type, String defaultExtName) {
        this.type = type;
        this.defaultExtName = defaultExtName;
    }

    /**
     * test if given type has at least one method annotated with <code>SPI</code>
     */
    private boolean hasAdaptiveMethod() {
        return Arrays.stream(type.getMethods()).anyMatch(m -> m.isAnnotationPresent(Adaptive.class));
    }

    /**
     * generate and return class code
     */
    public String generate() {
        // no need to generate adaptive class since there's no adaptive method found.
        // 只要有一个方法有@Adaptive 注解，就返回true
        if (!hasAdaptiveMethod()) {
            throw new IllegalStateException("No adaptive method exist on extension " + type.getName() + ", refuse to create the adaptive class!");
        }

        StringBuilder code = new StringBuilder();
        code.append(generatePackageInfo());
        code.append(generateImports());
        code.append(generateClassDeclaration());

        Method[] methods = type.getMethods();
        for (Method method : methods) {
            code.append(generateMethod(method));
        }
        code.append("}");

        if (logger.isDebugEnabled()) {
            logger.debug(code.toString());
        }
        return code.toString();
    }

    /**
     * generate package info
     */
    private String generatePackageInfo() {
        return String.format(CODE_PACKAGE, type.getPackage().getName());
    }

    /**
     * generate imports
     */
    private String generateImports() {
        return String.format(CODE_IMPORTS, ExtensionServiceLoader.class.getName());
    }

    /**
     * generate class declaration
     */
    private String generateClassDeclaration() {
        return String.format(CODE_CLASS_DECLARATION, type.getSimpleName(), type.getCanonicalName());
    }

    /**
     * generate method not annotated with Adaptive with throwing unsupported exception
     */
    private String generateUnsupported(Method method) {
        return String.format(CODE_UNSUPPORTED, method, type.getName());
    }

    /**
     * get index of parameter with type URL
     */
    private int getUrlTypeIndex(Method method) {
        int urlTypeIndex = -1;
        Class<?>[] pts = method.getParameterTypes();
        // 遍历参数列表，确定 URL 参数位置
        for (int i = 0; i < pts.length; ++i) {
            if (pts[i].equals(URL.class)) {
                urlTypeIndex = i;
                break;
            }
        }
        return urlTypeIndex;
    }

    /**
     * generate method declaration
     */
    private String generateMethod(Method method) {
        String methodReturnType = method.getReturnType().getCanonicalName();
        String methodName = method.getName();
        String methodContent = generateMethodContent(method);
        String methodArgs = generateMethodArguments(method);
        String methodThrows = generateMethodThrows(method);
        return String.format(CODE_METHOD_DECLARATION, methodReturnType, methodName, methodArgs, methodThrows, methodContent);
    }

    /**
     * generate method arguments
     */
    private String generateMethodArguments(Method method) {
        Class<?>[] pts = method.getParameterTypes();
        return IntStream.range(0, pts.length)
                .mapToObj(i -> String.format(CODE_METHOD_ARGUMENT, pts[i].getCanonicalName(), i))
                .collect(Collectors.joining(", "));
    }

    /**
     * generate method throws
     */
    private String generateMethodThrows(Method method) {
        Class<?>[] ets = method.getExceptionTypes();
        if (ets.length > 0) {
            String list = Arrays.stream(ets).map(Class::getCanonicalName).collect(Collectors.joining(", "));
            return String.format(CODE_METHOD_THROWS, list);
        } else {
            return "";
        }
    }

    /**
     * generate method URL argument null check
     */
    private String generateUrlNullCheck(int index) {
        return String.format(CODE_URL_NULL_CHECK, index, URL.class.getName(), index);
    }

    /**
     * generate method content
     */
    private String generateMethodContent(Method method) {
        Adaptive adaptiveAnnotation = method.getAnnotation(Adaptive.class);
        StringBuilder code = new StringBuilder(512);
        if (adaptiveAnnotation == null) {
            return generateUnsupported(method);
        } else {
            int urlTypeIndex = getUrlTypeIndex(method);

            // found parameter in URL type
            // urlTypeIndex != -1，表示参数列表中存在 URL 参数
            if (urlTypeIndex != -1) {
                // Null Point check
                // 为 URL 类型参数生成判空代码，格式如下：
                //  if (arg + urlTypeIndex== null)
                //      throw new IllegalArgumentException("url == null");
                //  com.sxzhongf.extension.dubbo.common.URL url = arg + index;
                code.append(generateUrlNullCheck(urlTypeIndex));
            } else {
                // 参数列表中不存在 URL 类型参数
                // did not find parameter in URL type
                code.append(generateUrlAssignmentIndirectly(method));
            }

            String[] value = getMethodAdaptiveValue(adaptiveAnnotation);

            // 此段逻辑是检测方法列表中是否存在 Invocation 类型的参数
            boolean hasInvocation = hasInvocationArgument(method);
            // 若存在 Invocation ，则为其生成判空代码和其他一些代码
            code.append(generateInvocationArgumentNullCheck(method));

            code.append(generateExtNameAssignment(value, hasInvocation));
            // check extName == null?
            // 生成 extName 判空代码
            code.append(generateExtNameNullCheck(value));

            //逻辑用于根据拓展名加载拓展实例，并调用拓展实例的目标方法
            // 生成拓展获取代码，格式如下：
            // type全限定名 extension = (type全限定名)ExtensionLoader全限定名
            //     .getExtensionLoader(type全限定名.class).getExtension(extName);
            // Tips: 格式化字符串中的 %<s 表示使用前一个转换符所描述的参数，即 type 全限定名
            code.append(generateExtensionAssignment());

            // return statement
            // 生成目标方法调用逻辑，格式为：
            //     extension.方法名(arg0, arg2, ..., argN);
            code.append(generateReturnAndInvocation(method));
        }

        return code.toString();
    }

    /**
     * generate code for variable extName null check
     */
    private String generateExtNameNullCheck(String[] value) {
        return String.format(CODE_EXT_NAME_NULL_CHECK, type.getName(), Arrays.toString(value));
    }

    /**
     * generate extName assigment code
     * 根据 SPI 和 Adaptive 注解值生成“获取拓展名逻辑”，同时生成逻辑也受 Invocation 类型参数影响
     */
    private String generateExtNameAssignment(String[] value, boolean hasInvocation) {
        // TODO: refactor it
        String getNameCode = null;
        //遍历 value，这里的 value 是 Adaptive 的注解值
        for (int i = value.length - 1; i >= 0; --i) {
            // 当 i 为最后一个元素的坐标时
            if (i == value.length - 1) {
                // 默认拓展名非空
                if (null != defaultExtName) {

                    // protocol 是 url 的一部分，可通过 getProtocol 方法获取，其他的则是从
                    // URL 参数中获取。因为获取方式不同，所以这里要判断 value[i] 是否为 protocol
                    if (!"protocol".equals(value[i])) {
                        // hasInvocation 用于标识方法参数列表中是否有 Invocation 类型参数
                        if (hasInvocation) {
                            // 生成的代码功能等价于下面的代码：
                            //   url.getMethodParameter(methodName, value[i], defaultExtName)
                            // 以 LoadBalance 接口的 select 方法为例，最终生成的代码如下：
                            //   url.getMethodParameter(methodName, "loadbalance", "random")
                            getNameCode = String.format("url.getMethodParameter(methodName, \"%s\", \"%s\")", value[i], defaultExtName);
                        } else {
                            // 生成的代码功能等价于下面的代码：
                            //   url.getParameter(value[i], defaultExtName)
                            getNameCode = String.format("url.getParameter(\"%s\", \"%s\")", value[i], defaultExtName);
                        }
                    } else {
                        getNameCode = String.format("( url.getProtocol() == null ? \"%s\" : url.getProtocol() )", defaultExtName);
                    }
                // 默认拓展名为空
                } else {
                    if (!"protocol".equals(value[i])) {
                        if (hasInvocation) {
                            // 同上面生成逻辑
                            getNameCode = String.format("url.getMethodParameter(methodName, \"%s\", \"%s\")", value[i], defaultExtName);
                        } else {
                            // 生成的代码功能等价于下面的代码：
                            //   url.getParameter(value[i])
                            getNameCode = String.format("url.getParameter(\"%s\")", value[i]);
                        }
                    } else {
                        // 生成从 url 中获取协议的代码，比如 "dubbo"
                        getNameCode = "url.getProtocol()";
                    }
                }
            } else {
                if (!"protocol".equals(value[i])) {
                    if (hasInvocation) {
                        // 生成逻辑同上
                        getNameCode = String.format("url.getMethodParameter(methodName, \"%s\", \"%s\")", value[i], defaultExtName);
                    } else {
                        // 生成的代码功能等价于下面的代码：
                        //   url.getParameter(value[i], getNameCode)
                        // 以 Transporter 接口的 connect 方法为例，最终生成的代码如下：
                        //   url.getParameter("client", url.getParameter("transporter", "netty"))
                        getNameCode = String.format("url.getParameter(\"%s\", %s)", value[i], getNameCode);
                    }
                } else {
                    // 生成的代码功能等价于下面的代码：
                    //   url.getProtocol() == null ? getNameCode : url.getProtocol()
                    // 以 Protocol 接口的 connect 方法为例，最终生成的代码如下：
                    //   url.getProtocol() == null ? "dubbo" : url.getProtocol()
                    getNameCode = String.format("url.getProtocol() == null ? (%s) : url.getProtocol()", getNameCode);
                }
            }
        }

        return String.format(CODE_EXT_NAME_ASSIGNMENT, getNameCode);
    }

    /**
     * @return
     */
    private String generateExtensionAssignment() {
        return String.format(CODE_EXTENSION_ASSIGNMENT, type.getName(), ExtensionServiceLoader.class.getSimpleName(), type.getName());
    }

    /**
     * generate method invocation statement and return it if necessary
     */
    private String generateReturnAndInvocation(Method method) {
        String returnStatement = method.getReturnType().equals(void.class) ? "" : "return ";

        String args = IntStream.range(0, method.getParameters().length)
                .mapToObj(i -> String.format(CODE_EXTENSION_METHOD_INVOKE_ARGUMENT, i))
                .collect(Collectors.joining(", "));

        return returnStatement + String.format("extension.%s(%s);\n", method.getName(), args);
    }

    /**
     * test if method has argument of type <code>Invocation</code>
     */
    private boolean hasInvocationArgument(Method method) {
        Class<?>[] pts = method.getParameterTypes();
        return Arrays.stream(pts).anyMatch(p -> CLASSNAME_INVOCATION.equals(p.getName()));
    }

    /**
     * generate code to test argument of type <code>Invocation</code> is null
     */
    private String generateInvocationArgumentNullCheck(Method method) {
        Class<?>[] pts = method.getParameterTypes();
        return IntStream.range(0, pts.length).filter(i -> CLASSNAME_INVOCATION.equals(pts[i].getName()))
                .mapToObj(i -> String.format(CODE_INVOCATION_ARGUMENT_NULL_CHECK, i, i))
                .findFirst().orElse("");
    }

    /**
     * get value of adaptive annotation or if empty return splitted simple name
     */
    private String[] getMethodAdaptiveValue(Adaptive adaptiveAnnotation) {
        String[] value = adaptiveAnnotation.value();
        // value is not set, use the value generated from class name as the key
        if (value.length == 0) {
            String splitName = StringUtils.camelToSplitName(type.getSimpleName(), ".");
            value = new String[]{splitName};
        }
        return value;
    }

    /**
     * get parameter with type <code>URL</code> from method parameter:
     * <p>
     * test if parameter has method which returns type <code>URL</code>
     * <p>
     * if not found, throws IllegalStateException
     */
    private String generateUrlAssignmentIndirectly(Method method) {
        Class<?>[] pts = method.getParameterTypes();

        // find URL getter method
        // 遍历方法列表，寻找可返回 URL 的 getter 方法
        for (int i = 0; i < pts.length; ++i) {
            // 获取某一类型参数的全部方法
            for (Method m : pts[i].getMethods()) {
                String name = m.getName();
                // 1. 方法名以 get 开头，或方法名大于3个字符
                // 2. 方法的访问权限为 public
                // 3. 非静态方法
                // 4. 方法参数数量为0
                // 5. 方法返回值类型为 URL
                if ((name.startsWith("get") || name.length() > 3)
                        && Modifier.isPublic(m.getModifiers())
                        && !Modifier.isStatic(m.getModifiers())
                        && m.getParameterTypes().length == 0
                        && m.getReturnType() == URL.class) {
                    return generateGetUrlNullCheck(i, pts[i], name);
                }
            }
        }

        // getter method not found, throw
        throw new IllegalStateException("Failed to create adaptive class for interface " + type.getName()
                + ": not found url parameter or url attribute in parameters of method " + method.getName());

    }

    /**
     * 1, test if argi is null
     * 2, test if argi.getXX() returns null
     * 3, assign url with argi.getXX()
     */
    private String generateGetUrlNullCheck(int index, Class<?> type, String method) {
        // Null point check
        StringBuilder code = new StringBuilder();
        code.append(String.format("if (arg%d == null) throw new IllegalArgumentException(\"%s argument == null\");\n",
                index, type.getName()));
        code.append(String.format("if (arg%d.%s() == null) throw new IllegalArgumentException(\"%s argument %s() == null\");\n",
                index, method, type.getName(), method));

        code.append(String.format("%s url = arg%d.%s();\n", URL.class.getName(), index, method));
        return code.toString();
    }

}
