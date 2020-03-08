package com.sxzhongf.extension.dubbo.rpc;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.remoting.RemotingServer;

/**
 * Distinct from {@link RemotingServer}, each protocol holds one or more ProtocolServers(the number usually decides by port numbers),
 * while each ProtocolServer holds zero or one RemotingServer.
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/8
 **/
public interface ProtocolServer {

    default RemotingServer getRemotingServer() {
        return null;
    }

    default void setRemotingServers(RemotingServer server) {
    }

    String getAddress();

    void setAddress(String address);

    default URL getUrl() {
        return null;
    }

    default void reset(URL url) {
    }

    void close();
}
