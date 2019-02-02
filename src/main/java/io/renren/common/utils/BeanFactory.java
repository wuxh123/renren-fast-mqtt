package io.renren.common.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.renren.common.annotation.Parser;
import io.renren.modules.base.ParseBase;
import org.reflections.Reflections;

public class BeanFactory {

    /**
     * 初始化指定包下的所有@Service注解标记的类
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static ArrayList<ParseBase> getParsers() throws InstantiationException, IllegalAccessException {
        ArrayList<ParseBase> list= new ArrayList<ParseBase> ();
        Reflections f = new Reflections("io.renren.modules.parse");
        Set<Class<?>> set = f.getTypesAnnotatedWith(Parser.class);
        for (Class<?> c : set) {
            Object bean = c.newInstance();
            Parser annotation = c.getAnnotation(Parser.class);
            list.add((ParseBase)bean);
        }
        return list;
    }
}