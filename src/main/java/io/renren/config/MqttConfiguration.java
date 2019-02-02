package io.renren.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.mqtt")
@Data
public class MqttConfiguration {
    private String host;
    private String username;
    private String password;
    private String topic;
    private int timeout;
    private int keepalive;
    private String dev_up_topic;
    private String down_dev_topic;
    private String up_app_topic;
    private String app_down_topic;
}
