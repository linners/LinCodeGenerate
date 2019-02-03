package com.lin.ideaplugin.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class VelocityUtils {

    private static Map<String, VelocityUtils> instanceMap = new HashMap<>();
    private VelocityEngine velocityEngine;

    /**
     * 初始化velocity引擎
     *
     * @param fileBasePath  加载文件的基础目录
     */
    private VelocityUtils(String fileBasePath) {
        Properties properties = new Properties();
        //设置velocity资源加载方式为file
        properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, fileBasePath);
        //设置velocity资源加载方式为file时的处理类
        properties.put("input.encoding", "UTF-8");
        properties.put("output.encoding", "UTF-8");
        //实例化一个VelocityEngine对象
        velocityEngine = new VelocityEngine(properties);
    }
    private VelocityUtils() {
//        Properties properties = new Properties();
//        //设置velocity资源加载方式为file
//        properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//        //设置velocity资源加载方式为file时的处理类
//        properties.put("input.encoding", "UTF-8");
//        properties.put("output.encoding", "UTF-8");
//        //实例化一个VelocityEngine对象
//        velocityEngine = new VelocityEngine(properties);
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        velocityEngine.init();
    }

    public static VelocityUtils getInstance(String fileBasePath) {
        if(instanceMap.get(fileBasePath)==null) {
            instanceMap.put(fileBasePath, new VelocityUtils(fileBasePath));
        }
        return instanceMap.get(fileBasePath);
    }

    public static VelocityUtils getInstance() {
        String fileBasePath = "classpath";
        if(instanceMap.get(fileBasePath)==null) {
            instanceMap.put(fileBasePath, new VelocityUtils());
        }
        return instanceMap.get(fileBasePath);
    }


    // 编译velocity字符串
    public String compileVelocityString(String content, VelocityContext context) {

        //实例化一个StringWriter
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "LOG_TAG", content);
        return writer.toString();
    }

    // 编译velocity文件
    public String compileVelocityFile(String templatePath, VelocityContext context) {
        //实例化一个StringWriter
        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate(templatePath, "UTF-8", context, writer);
        return writer.toString();
    }



    public static void main(String[] args) {
        VelocityContext context = new VelocityContext();
        context.put("apiPackage", "com.lin.bulter");
//        VelocityUtils instance = VelocityUtils.getInstance("e:/github-template");
//        String str1 = instance.compileVelocityString("${test}/a/b/${test2}", context);
//        VelocityUtils instance2 = VelocityUtils.getInstance("e:/github-template2");
//        String str2 = instance2.compileVelocityFile("/controller/${entityNameUp}Controller.java", context);
//        System.out.println(str2);
        VelocityUtils instance = VelocityUtils.getInstance();
        
    }
}
