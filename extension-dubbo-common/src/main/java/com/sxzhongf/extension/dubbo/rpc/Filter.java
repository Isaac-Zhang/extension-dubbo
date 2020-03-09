package com.sxzhongf.extension.dubbo.rpc;

/**
 * Extension for intercepting the invocation for both service provider and consumer, furthermore, most of
 * functions in dubbo are implemented base on the same mechanism. Since every time when remote method is
 * invoked, the filter extensions will be executed too, the corresponding penalty should be considered before
 * more filters are added.
 * <pre>
 *  They way filter work from sequence point of view is
 *    <b>
 *    ...code before filter ...
 *          invoker.invoke(invocation) //filter work in a filter implementation class
 *          ...code after filter ...
 *    </b>
 *    Caching is implemented in dubbo using filter approach. If cache is configured for invocation then before
 *    remote call configured caching type's (e.g. Thread Local, JCache etc) implementation invoke method gets called.
 * </pre>
 * Filter. (SPI, Singleton, ThreadSafe)
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public interface Filter {
    /**
     * Make sure call invoker.invoke() in your implementation.
     */
    Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException;

    /**
     * Please use {@link Listener#onMessage(Result, Invoker, Invocation)} instead.
     * This method is kept only for compatibility and may get removed at any version in the future.
     *
     * @param appResponse
     * @param invoker
     * @param invocation
     */
    @Deprecated
    default Result onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        return appResponse;
    }

    interface Listener {

        void onMessage(Result appResponse, Invoker<?> invoker, Invocation invocation);

        void onError(Throwable t, Invoker<?> invoker, Invocation invocation);
    }

}
