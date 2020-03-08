package com.sxzhongf.extension.dubbo.remoting;

import com.sxzhongf.extension.dubbo.common.Resetable;
import com.sxzhongf.extension.dubbo.common.URL;

/**
 * Remoting Client. (API/SPI, Prototype, ThreadSafe)
 * <p>
 * <a href="http://en.wikipedia.org/wiki/Client%E2%80%93server_model">Client/Server</a>
 *
 * @see Transporter#connect(URL, ChannelHandler)
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/8
 **/
public interface Client extends Endpoint, Channel, Resetable, IdleSensible {

    /**
     * reconnect.
     */
    void reconnect() throws RemotingException;

//    @Deprecated
//    void reset(org.apache.dubbo.common.Parameters parameters);

}
