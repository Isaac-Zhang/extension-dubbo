package com.sxzhongf.extension.dubbo.remoting;

/**
 * Constants for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/8
 **/
public interface Constants {

    String BUFFER_KEY = "buffer";

    /**
     * default buffer size is 8k.
     */
    int DEFAULT_BUFFER_SIZE = 8 * 1024;

    int MAX_BUFFER_SIZE = 16 * 1024;

    int MIN_BUFFER_SIZE = 1 * 1024;

    String IDLE_TIMEOUT_KEY = "idle.timeout";

    int DEFAULT_IDLE_TIMEOUT = 600 * 1000;
    /**
     * max size of channel. default value is zero that means unlimited.
     */
    String ACCEPTS_KEY = "accepts";

    int DEFAULT_ACCEPTS = 0;

    String CONNECT_QUEUE_CAPACITY = "connect.queue.capacity";

    String CONNECT_QUEUE_WARNING_SIZE = "connect.queue.warning.size";

    int DEFAULT_CONNECT_QUEUE_WARNING_SIZE = 1000;

    String CHARSET_KEY = "charset";

    String DEFAULT_CHARSET = "UTF-8";

    /**
     * Every heartbeat duration / HEATBEAT_CHECK_TICK, check if a heartbeat should be sent. Every heartbeat timeout
     * duration / HEATBEAT_CHECK_TICK, check if a connection should be closed on server side, and if reconnect on
     * client side
     */
    int HEARTBEAT_CHECK_TICK = 3;

    /**
     * the least heartbeat during is 1000 ms.
     */
    long LEAST_HEARTBEAT_DURATION = 1000;

    /**
     * ticks per wheel.
     */
    int TICKS_PER_WHEEL = 128;
    String PAYLOAD_KEY = "payload";
    /**
     * 8M
     */
    int DEFAULT_PAYLOAD = 8 * 1024 * 1024;

    String CONNECT_TIMEOUT_KEY = "connect.timeout";

    int DEFAULT_CONNECT_TIMEOUT = 3000;

    String SERIALIZATION_KEY = "serialization";

    String DEFAULT_REMOTING_SERIALIZATION = "hessian2";

    String CODEC_KEY = "codec";

    String CODEC_VERSION_KEY = "codec.version";

    String SERVER_KEY = "server";

    String CLIENT_KEY = "client";

    String DEFAULT_REMOTING_CLIENT = "netty";

    String TRANSPORTER_KEY = "transporter";

    String DEFAULT_TRANSPORTER = "netty";

    String EXCHANGER_KEY = "exchanger";

    String DEFAULT_EXCHANGER = "header";

    String DISPACTHER_KEY = "dispacther";

    int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 32);

    String BIND_IP_KEY = "bind.ip";

    String BIND_PORT_KEY = "bind.port";

    String SENT_KEY = "sent";

    String DISPATCHER_KEY = "dispatcher";

    String CHANNEL_ATTRIBUTE_READONLY_KEY = "channel.readonly";

    String CHANNEL_READONLYEVENT_SENT_KEY = "channel.readonly.sent";

    String CHANNEL_SEND_READONLYEVENT_KEY = "channel.readonly.send";

    String RECONNECT_KEY = "reconnect";

    int DEFAULT_RECONNECT_PERIOD = 2000;

    String SEND_RECONNECT_KEY = "send.reconnect";

    String CHECK_KEY = "check";

    String PROMPT_KEY = "prompt";

    String DEFAULT_PROMPT = "dubbo>";
    String TELNET = "telnet";
    String HEARTBEAT_KEY = "heartbeat";
    int DEFAULT_HEARTBEAT = 60 * 1000;
    String HEARTBEAT_TIMEOUT_KEY = "heartbeat.timeout";
    String CONNECTIONS_KEY = "connections";

    int DEFAULT_BACKLOG = 1024;
}
