package com.sxzhongf.extension.dubbo.config;

import com.sxzhongf.extension.dubbo.common.URL;
import com.sxzhongf.extension.dubbo.common.utils.StringUtils;
import com.sxzhongf.extension.dubbo.config.support.Parameter;

import java.util.HashMap;
import java.util.Map;

import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.DUBBO;
import static com.sxzhongf.extension.dubbo.common.constants.CommonConstants.PROPERTIES_CHAR_SEPERATOR;

/**
 * MetadataReportConfig
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class MetadataReportConfig extends AbstractConfig {

    private static final long serialVersionUID = 55233L;
    /**
     * the value is : metadata-report
     */
    private static final String PREFIX_TAG = StringUtils.camelToSplitName(
            MetadataReportConfig.class.getSimpleName().substring(0, MetadataReportConfig.class.getSimpleName().length() - 6), PROPERTIES_CHAR_SEPERATOR);

    // Register center address
    private String address;

    // Username to login register center
    private String username;

    // Password to login register center
    private String password;

    // Request timeout in milliseconds for register center
    private Integer timeout;

    /**
     * The group the metadata in . It is the same as registry
     */
    private String group;

    // Customized parameters
    private Map<String, String> parameters;

    private Integer retryTimes;

    private Integer retryPeriod;
    /**
     * By default the metadatastore will store full metadata repeatly every day .
     */
    private Boolean cycleReport;

    /**
     * Sync report, default async
     */
    private Boolean syncReport;

    /**
     * cluster
     */
    private Boolean cluster;

    public MetadataReportConfig() {
    }

    public MetadataReportConfig(String address) {
        setAddress(address);
    }

    public URL toUrl() {
        String address = this.getAddress();
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        appendParameters(map, this);
        if (!StringUtils.isEmpty(address)) {
            URL url = URL.valueOf(address);
            map.put("metadata", url.getProtocol());
            return new URL("metadata", url.getUsername(), url.getPassword(), url.getHost(),
                    url.getPort(), url.getPath(), map);
        }
        throw new IllegalArgumentException("The address of metadata report is invalid.");
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Parameter(key = "retry-times")
    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    @Parameter(key = "retry-period")
    public Integer getRetryPeriod() {
        return retryPeriod;
    }

    public void setRetryPeriod(Integer retryPeriod) {
        this.retryPeriod = retryPeriod;
    }

    @Parameter(key = "cycle-report")
    public Boolean getCycleReport() {
        return cycleReport;
    }

    public void setCycleReport(Boolean cycleReport) {
        this.cycleReport = cycleReport;
    }

    @Parameter(key = "sync-report")
    public Boolean getSyncReport() {
        return syncReport;
    }

    public void setSyncReport(Boolean syncReport) {
        this.syncReport = syncReport;
    }

    @Override
    @Parameter(excluded = true)
    public String getPrefix() {
        return StringUtils.isNotEmpty(prefix) ? prefix : (DUBBO + "." + PREFIX_TAG);
    }

    @Override
    @Parameter(excluded = true)
    public boolean isValid() {
        return StringUtils.isNotEmpty(address);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getCluster() {
        return cluster;
    }

    public void setCluster(Boolean cluster) {
        this.cluster = cluster;
    }
}
