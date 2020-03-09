package com.sxzhongf.extension.dubbo.config;

import com.sxzhongf.extension.dubbo.common.logger.Logger;
import com.sxzhongf.extension.dubbo.common.logger.LoggerFactory;
import com.sxzhongf.extension.dubbo.config.support.Parameter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SslConfig
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class SslConfig extends AbstractConfig {

    private static final Logger logger = LoggerFactory.getLogger(SslConfig.class);
    private AtomicBoolean inited = new AtomicBoolean(false);

    private String serverKeyCertChainPath;
    private String serverPrivateKeyPath;
    private String serverKeyPassword;
    private String serverTrustCertCollectionPath;

    private String clientKeyCertChainPath;
    private String clientPrivateKeyPath;
    private String clientKeyPassword;
    private String clientTrustCertCollectionPath;

    private InputStream serverKeyCertChainPathStream;
    private InputStream serverPrivateKeyPathStream;
    private InputStream serverTrustCertCollectionPathStream;

    private InputStream clientKeyCertChainPathStream;
    private InputStream clientPrivateKeyPathStream;
    private InputStream clientTrustCertCollectionPathStream;

    @Parameter(key = "server-key-cert-chain-path")
    public String getServerKeyCertChainPath() {
        return serverKeyCertChainPath;
    }

    public void setServerKeyCertChainPath(String serverKeyCertChainPath) {
        this.serverKeyCertChainPath = serverKeyCertChainPath;
    }

    @Parameter(key = "server-private-key-path")
    public String getServerPrivateKeyPath() {
        return serverPrivateKeyPath;
    }

    public void setServerPrivateKeyPath(String serverPrivateKeyPath) {
        this.serverPrivateKeyPath = serverPrivateKeyPath;
    }

    @Parameter(key = "server-key-password")
    public String getServerKeyPassword() {
        return serverKeyPassword;
    }

    public void setServerKeyPassword(String serverKeyPassword) {
        this.serverKeyPassword = serverKeyPassword;
    }

    @Parameter(key = "server-trust-cert-collection-path")
    public String getServerTrustCertCollectionPath() {
        return serverTrustCertCollectionPath;
    }

    public void setServerTrustCertCollectionPath(String serverTrustCertCollectionPath) {
        this.serverTrustCertCollectionPath = serverTrustCertCollectionPath;
    }

    @Parameter(key = "client-key-cert-chain-path")
    public String getClientKeyCertChainPath() {
        return clientKeyCertChainPath;
    }

    public void setClientKeyCertChainPath(String clientKeyCertChainPath) {
        this.clientKeyCertChainPath = clientKeyCertChainPath;
    }

    @Parameter(key = "client-private-key-path")
    public String getClientPrivateKeyPath() {
        return clientPrivateKeyPath;
    }

    public void setClientPrivateKeyPath(String clientPrivateKeyPath) {
        this.clientPrivateKeyPath = clientPrivateKeyPath;
    }

    @Parameter(key = "client-key-password")
    public String getClientKeyPassword() {
        return clientKeyPassword;
    }

    public void setClientKeyPassword(String clientKeyPassword) {
        this.clientKeyPassword = clientKeyPassword;
    }

    @Parameter(key = "client-trust-cert-collection-path")
    public String getClientTrustCertCollectionPath() {
        return clientTrustCertCollectionPath;
    }

    public void setClientTrustCertCollectionPath(String clientTrustCertCollectionPath) {
        this.clientTrustCertCollectionPath = clientTrustCertCollectionPath;
    }

    public InputStream getServerKeyCertChainPathStream() throws FileNotFoundException {
        if (serverKeyCertChainPath != null) {
            serverKeyCertChainPathStream = new FileInputStream(serverKeyCertChainPath);
        }
        return serverKeyCertChainPathStream;
    }

    public void setServerKeyCertChainPathStream(InputStream serverKeyCertChainPathStream) {
        this.serverKeyCertChainPathStream = serverKeyCertChainPathStream;
    }

    public InputStream getServerPrivateKeyPathStream() throws FileNotFoundException {
        if (serverPrivateKeyPath != null) {
            serverPrivateKeyPathStream = new FileInputStream(serverPrivateKeyPath);
        }
        return serverPrivateKeyPathStream;
    }

    public void setServerPrivateKeyPathStream(InputStream serverPrivateKeyPathStream) {
        this.serverPrivateKeyPathStream = serverPrivateKeyPathStream;
    }

    public InputStream getServerTrustCertCollectionPathStream() throws FileNotFoundException {
        if (serverTrustCertCollectionPath != null) {
            serverTrustCertCollectionPathStream = new FileInputStream(serverTrustCertCollectionPath);
        }
        return serverTrustCertCollectionPathStream;
    }

    public void setServerTrustCertCollectionPathStream(InputStream serverTrustCertCollectionPathStream) {
        this.serverTrustCertCollectionPathStream = serverTrustCertCollectionPathStream;
    }

    public InputStream getClientKeyCertChainPathStream() throws FileNotFoundException {
        if (clientKeyCertChainPath != null) {
            clientKeyCertChainPathStream = new FileInputStream(clientKeyCertChainPath);
        }
        return clientKeyCertChainPathStream;
    }

    public void setClientKeyCertChainPathStream(InputStream clientKeyCertChainPathStream) {
        this.clientKeyCertChainPathStream = clientKeyCertChainPathStream;
    }

    public InputStream getClientPrivateKeyPathStream() throws FileNotFoundException {
        if (clientPrivateKeyPath != null) {
            clientPrivateKeyPathStream = new FileInputStream(clientPrivateKeyPath);
        }
        return clientPrivateKeyPathStream;
    }

    public void setClientPrivateKeyPathStream(InputStream clientPrivateKeyPathStream) {
        this.clientPrivateKeyPathStream = clientPrivateKeyPathStream;
    }

    public InputStream getClientTrustCertCollectionPathStream() throws FileNotFoundException {
        if (clientTrustCertCollectionPath != null) {
            clientTrustCertCollectionPathStream = new FileInputStream(clientTrustCertCollectionPath);
        }
        return clientTrustCertCollectionPathStream;
    }

    public void setClientTrustCertCollectionPathStream(InputStream clientTrustCertCollectionPathStream) {
        this.clientTrustCertCollectionPathStream = clientTrustCertCollectionPathStream;
    }
}
