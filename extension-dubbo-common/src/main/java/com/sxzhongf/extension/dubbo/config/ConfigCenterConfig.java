package com.sxzhongf.extension.dubbo.config;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.common.constants.CommonConstants;
import com.sxzhongf.extension.dubbo.common.utils.StringUtils;
import com.sxzhongf.extension.dubbo.common.utils.UrlUtils;
import com.sxzhongf.extension.dubbo.config.support.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.*;
import static com.sxzhongf.extension.dubbo.config.Constants.CONFIG_APP_CONFIGFILE_KEY;
import static com.sxzhongf.extension.dubbo.config.Constants.ZOOKEEPER_PROTOCOL;

/**
 * ConfigCenterConfig for TODO
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ConfigCenterConfig extends AbstractConfig {
    private AtomicBoolean inited = new AtomicBoolean(false);

    private String protocol;
    private String address;

    /* The config center cluster, it's real meaning may very on different Config Center products. */
    private String cluster;

    /* The namespace of the config center, generally it's used for multi-tenant,
    but it's real meaning depends on the actual Config Center you use.
    */

    private String namespace = CommonConstants.DUBBO;
    /* The group of the config center, generally it's used to identify an isolated space for a batch of config items,
    but it's real meaning depends on the actual Config Center you use.
    */
    private String group = CommonConstants.DUBBO;
    private String username;
    private String password;
    private Long timeout = 3000L;

    // If the Config Center is given the highest priority, it will override all the other configurations
    private Boolean highestPriority = true;

    // Decide the behaviour when initial connection try fails, 'true' means interrupt the whole process once fail.
    private Boolean check = true;

    /* Used to specify the key that your properties file mapping to, most of the time you do not need to change this parameter.
    Notice that for Apollo, this parameter is meaningless, set the 'namespace' is enough.
    */
    private String configFile = CommonConstants.DEFAULT_DUBBO_PROPERTIES;

    /* the .properties file under 'configFile' is global shared while .properties under this one is limited only to this application
     */
    private String appConfigFile;

    /* If the Config Center product you use have some special parameters that is not covered by this class, you can add it to here.
    For example, with XML:
      <dubbo:config-center>
           <dubbo:parameter key="{your key}" value="{your value}" />
      </dubbo:config-center>
     */
    private Map<String, String> parameters;

    private Map<String, String> externalConfiguration;

    private Map<String, String> appExternalConfiguration;

    public ConfigCenterConfig() {
    }

    public URL toUrl() {
        Map<String, String> map = new HashMap<>();
        appendParameters(map, this);
        if (StringUtils.isEmpty(address)) {
            address = ANYHOST_VALUE;
        }
        map.put(PATH_KEY, ConfigCenterConfig.class.getSimpleName());
        // use 'zookeeper' as the default configcenter.
        if (StringUtils.isEmpty(map.get(PROTOCOL_KEY))) {
            map.put(PROTOCOL_KEY, ZOOKEEPER_PROTOCOL);
        }
        return UrlUtils.parseURL(address, map);
    }

    public boolean checkOrUpdateInited() {
        return inited.compareAndSet(false, true);
    }

    public Map<String, String> getExternalConfiguration() {
        return externalConfiguration;
    }

    public Map<String, String> getAppExternalConfiguration() {
        return appExternalConfiguration;
    }

    public void setExternalConfig(Map<String, String> externalConfiguration) {
        this.externalConfiguration = externalConfiguration;
    }

    public void setAppExternalConfig(Map<String, String> appExternalConfiguration) {
        this.appExternalConfiguration = appExternalConfiguration;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Parameter(key = CONFIG_ENABLE_KEY)
    public Boolean isHighestPriority() {
        return highestPriority;
    }

    public void setHighestPriority(Boolean highestPriority) {
        this.highestPriority = highestPriority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Parameter(key = CONFIG_CONFIGFILE_KEY)
    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    @Parameter(excluded = true, key = CONFIG_APP_CONFIGFILE_KEY)
    public String getAppConfigFile() {
        return appConfigFile;
    }

    public void setAppConfigFile(String appConfigFile) {
        this.appConfigFile = appConfigFile;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    @Parameter(excluded = true)
    public boolean isValid() {
        if (StringUtils.isEmpty(address)) {
            return false;
        }

        return address.contains("://") || StringUtils.isNotEmpty(protocol);
    }
}
