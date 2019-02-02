package io.renren.modules.iot;

import io.renren.config.MqttConfiguration;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("Duplicates")
@Configuration
public class CallBackPublisher implements MqttCallback {

    @Autowired
    MqttConfiguration mqttConfiguration;

    @Autowired
    MQTTPublisher mqttPublisher;

    MQTTPublisher pub=null;

    public CallBackPublisher() {

    }

    public CallBackPublisher(MQTTPublisher pub) {
        this.pub = pub;
    }

    //方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true); true为非持久订阅
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("发布，触发断线事件。");
        pub.run(null);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)");
    }
}