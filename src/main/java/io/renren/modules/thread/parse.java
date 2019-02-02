package io.renren.modules.thread;

import io.renren.common.utils.PackageUtil;
import io.renren.common.utils.PageUtils;
import io.renren.modules.base.CmdBase;
import io.renren.modules.base.ParseBase;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class parse  extends Thread{
    private String topic;
    private MqttMessage msg;
    private static Logger logger =  LoggerFactory.getLogger("");

    @Override
    public void run() {
        System.out.println(new Date());
        System.out.println("public void messageArrived(String s, MqttMessage mqttMessage)");
        System.out.println(topic);
        try {
            List<ParseBase> list =PackageUtil.getParserClass();

            //执行解析 指令
            List<CmdBase> rtn_cmds = new ArrayList<>();
            for (ParseBase parser:list){
                System.out.println(parser.parse(topic,msg,rtn_cmds));
            }

            //执行完毕，执行返回的cmd命令
            for (CmdBase cmd :rtn_cmds){
                cmd.exec();
            }

            System.out.println(new String(msg.getPayload(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
