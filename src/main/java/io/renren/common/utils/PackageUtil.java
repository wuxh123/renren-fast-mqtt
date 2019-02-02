package io.renren.common.utils;

import io.renren.modules.base.ParseBase;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author jiangzeyin
 * @date 2016-9-9
 */
public class PackageUtil {

    private static ArrayList<ParseBase> parses =null;
    public static ArrayList<ParseBase>getParserClass(){
        if (parses == null){
            try {
                parses=BeanFactory.getParsers();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return  parses;
    }
}