package com.sxzhongf.extension.dubbo.common.config.configcenter;

import java.util.EventObject;
import java.util.Objects;

/**
 * An event raised when the config changed, immutable.
 *
 * @see ConfigChangeType
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class ConfigChangedEvent extends EventObject {

    private final String key;

    private final String group;

    private final String content;

    private final ConfigChangeType changeType;

    public ConfigChangedEvent(String key, String group, String content) {
        this(key, group, content, ConfigChangeType.MODIFIED);
    }

    public ConfigChangedEvent(String key, String group, String content, ConfigChangeType changeType) {
        super(key + "," + group);
        this.key = key;
        this.group = group;
        this.content = content;
        this.changeType = changeType;
    }

    public String getKey() {
        return key;
    }

    public String getGroup() {
        return group;
    }

    public String getContent() {
        return content;
    }

    public ConfigChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String toString() {
        return "ConfigChangedEvent{" +
                "key='" + key + '\'' +
                ", group='" + group + '\'' +
                ", content='" + content + '\'' +
                ", changeType=" + changeType +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigChangedEvent)) return false;
        ConfigChangedEvent that = (ConfigChangedEvent) o;
        return Objects.equals(getKey(), that.getKey()) &&
                Objects.equals(getGroup(), that.getGroup()) &&
                Objects.equals(getContent(), that.getContent()) &&
                getChangeType() == that.getChangeType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getGroup(), getContent(), getChangeType());
    }
}
