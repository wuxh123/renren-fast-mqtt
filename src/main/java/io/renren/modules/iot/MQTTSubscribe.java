package io.renren.modules.iot;

import io.renren.common.utils.Constant;
import io.renren.config.MqttConfiguration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;

@SuppressWarnings("Duplicates")
@Component
public class MQTTSubscribe implements CommandLineRunner {

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
    CallBackSubscribe callback =  new CallBackSubscribe(this);

    public String subscribe() {
        try {
            //创建MqttClient
            String clientid= UUID.randomUUID().toString();
            client = new MqttClient(mqttConfiguration.getHost(),clientid);
            //创建连接可选项信息
            conOptions = new MqttConnectOptions();
            //
            conOptions.setUserName(mqttConfiguration.getUsername());
            conOptions.setPassword(mqttConfiguration.getPassword().toCharArray());
            conOptions.setCleanSession(false);
            conOptions.setAutomaticReconnect(true);
            //回调处理类
            client.setCallback(callback);
            //连接broker
            client.connect(conOptions);
            //发布相关的订阅
            String[] topic = {mqttConfiguration.getDev_up_topic(),mqttConfiguration.getApp_down_topic()};
            int[] qos = {1,1};
            client.subscribe(topic, qos);
            //client.disconnect();
        } catch (Exception e) {
            //e.printStackTrace();
            return "failed";
        }
        return "success";
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
         while (true ) {
            if (subscribe()!="success" || !client.isConnected()) {
                System.out.println("订阅，无法连接mqtt服务器，重连。");
                mySleep(1000);
                continue;
            }
            System.out.println("订阅，mqtt 服务器连接成功。");
            break;
        }
    }
}
