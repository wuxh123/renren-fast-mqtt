package io.renren.modules.parse;

import io.renren.common.annotation.Parser;
import io.renren.modules.base.CmdBase;
import io.renren.modules.base.ParseBase;
import io.renren.modules.cmd.cmd1;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

@Parser(value="Parse1")
public class Parse1 extends ParseBase {
    @Override
    public int parse(String topic, MqttMessage msg, List<CmdBase> cmds) {
        cmds.add(new cmd1());
        return 0;
    }
}
