package com.lin.ideaplugin.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * @说明 导出虚拟机
 * @author wxt
 * @version 1.0
 * @since
 */
public class FileTool {
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String url = "http://wcler.cn/download/1e8e9feac3cfe2f5a8cdc445ee1b518d.zip";
        byte[] btImg = getVMFromNetByUrl(url);
        if(null != btImg && btImg.length > 0){
            System.out.println("读取到：" + btImg.length + " 字节");
            String savefile = "d:/download/project.txt";
            writeImageToDisk(btImg, savefile);
        }else{
            System.out.println("没有从该连接获得内容");
        }
    }

    /**
     * 下载文件
     * @param downloadUrl
     * @param savePath
     * @param fileName
     */
    public static void downloadFile(String downloadUrl, String saveFilePath) {
        byte[] btImg = getVMFromNetByUrl(downloadUrl);
        if(null != btImg && btImg.length > 0){
            System.out.println("读取到：" + btImg.length + " 字节");
            writeImageToDisk(btImg, saveFilePath);
        }else{
            System.out.println("没有从该连接获得内容");
        }
    }

    /**
     * 将vm 写入到磁盘
     * @param vm 数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeImageToDisk(byte[] vm, String saveFilePath){
        FileOutputStream fops = null;
        try {
            File file = new File(saveFilePath);
            fops = new FileOutputStream(file);
            fops.write(vm);
            fops.flush();
            System.out.println("下载完成");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fops!=null){
                try {
                    fops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getVMFromNetByUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取数据
            byte[] btImg = readInputStream(inStream);//得到的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}