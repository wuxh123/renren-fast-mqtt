package io.renren.modules.iot;

import io.renren.config.MqttConfiguration;
import io.renren.modules.thread.parse;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.security.auth.callback.Callback;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("Duplicates")
@Configuration
public class CallBackSubscribe implements MqttCallback {

    @Autowired
    MqttConfiguration mqttConfiguration;

    MQTTSubscribe sub=null;

/*    @Autowired
    MQTTPublisher mqttPublisher;*/

    private ExecutorService pool = Executors.newCachedThreadPool();

    public CallBackSubscribe() {
    }

    public CallBackSubscribe(MQTTSubscribe sub) {
        this.sub = sub;
    }

    //方法实现说明 断线重连方法，如果是持久订阅，重连是不需要再次订阅，如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true); true为非持久订阅
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("订阅,连接失败重连");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        parse parseThread = new parse();
        parseThread.setMsg(mqttMessage);
        parseThread.setTopic(s);
        parseThread.start();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)");
    }
}
