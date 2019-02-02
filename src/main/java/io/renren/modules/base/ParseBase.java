package io.renren.modules.base;

import io.renren.common.utils.PackageUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ParseBase {
    public abstract  int parse(String topic, MqttMessage msg, List<CmdBase> cmds);



}
