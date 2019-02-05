package io.renren.modules.iot;

import io.renren.config.MqttConfiguration;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SuppressWarnings("Duplicates")
@Component
public class MQTTPublisher implements CommandLineRunner {
    @Autowired
    MqttConfiguration mqttConfiguration;
    MqttClient client = null;
    MqttConnectOptions conOptions = null;
    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    Integer sem=0;
    CallBackPublisher callback = new CallBackPublisher();

    public MQTTPublisher() {
    }

    public String publish() {
        try {
             //创建MqttClient
            String clientid= UUID.randomUUID().toString();
            client = new MqttClient(mqttConfiguration.getHost(), clientid);
            //创建连接可选项信息
            conOptions = new MqttConnectOptions();
            //
            conOptions.setUserName(mqttConfiguration.getUsername());
            conOptions.setPassword(mqttConfiguration.getPassword().toCharArray());
            conOptions.setCleanSession(false);
            conOptions.setAutomaticReconnect(true);
            //连接broker
            client.connect(conOptions);
            //回调处理类
            client.setCallback(callback);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed";
    }

    public void publish_to_dev(String subtopic,String str){
        try {
            MqttMessage message = new MqttMessage(str.getBytes());
            message.setQos(1);
            MqttTopic topic_down = client.getTopic(mqttConfiguration.getDown_dev_topic()+"/"+subtopic);
            MqttDeliveryToken token = topic_down.publish(message);
             while (!token.isComplete()){
                 token.waitForCompletion(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish_to_app(String  subtopic,String str){
        try {
            MqttTopic topic_up = client.getTopic(mqttConfiguration.getUp_app_topic()+"/"+subtopic);
            MqttMessage message = new MqttMessage(str.getBytes());
            message.setQos(1);
            MqttDeliveryToken token = topic_up.publish(message);
            while (!token.isComplete()){
                token.waitForCompletion(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mySleep(int millis){
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) {
        System.out.println("----------------------------------------------------------------------------------------------------");
        while (true ) {
            if (publish()!="success" || !client.isConnected()) {
                System.out.println("发布，无法连接mqtt服务器，重连。");
                mySleep(1000);
                continue;
            }
            System.out.println("发布，mqtt 服务器连接成功。");
            break;
        }
    }
}
