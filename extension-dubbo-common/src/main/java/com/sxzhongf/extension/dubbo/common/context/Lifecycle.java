package com.sxzhongf.extension.dubbo.common.context;

/**
 * The Lifecycle of Dubbo component
 *
 * @since 2.7.5
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/5
 **/
public interface Lifecycle {

    /**
     * Initialize the component before {@link #start() start}
     *
     * @return current {@link Lifecycle}
     * @throws IllegalStateException
     */
    void initialize() throws IllegalStateException;

    /**
     * Start the component
     *
     * @return current {@link Lifecycle}
     * @throws IllegalStateException
     */
    void start() throws IllegalStateException;

    /**
     * Destroy the component
     *
     * @throws IllegalStateException
     */
    void destroy() throws IllegalStateException;
}
