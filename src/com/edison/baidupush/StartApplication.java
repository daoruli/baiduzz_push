package com.edison.baidupush;

import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: 陈俊锋
 * @Date: 2020年4月24日17:22:16
 */
public class StartApplication {
    public static void main(String[] args) {
        //理论传入参数的长度
        final int argsLength = 4;
        //文件扩展名
        final String fileExtName = "xml";
        //站点域名参数索引
        final int siteIndex = 0;
        //token参数索引
        final int tokenIndex = 1;
        //站点地图路径参数索引
        final int siteMapIndex = 2;
        //节点名称参数索引
        final int tagIndex = 3;
        //文件名称按.分割后数组长度
        final int fileSplitLength = 2;

        if (null == args || args.length != argsLength) {
            throw new RuntimeException("传入参数有误请检查参数");
        }
        System.out.println("传入的站点为:" + args[siteIndex]);
        System.out.println("传入的token为:" + args[tokenIndex]);
        System.out.println("传入的站点地图路径为:" + args[siteMapIndex]);
        System.out.println("传入的截取标签为:" + args[tagIndex]);

        if (null == args[siteMapIndex] || "".equals(args[siteMapIndex]) || "".equals(args[siteMapIndex].trim())) {
            throw new RuntimeException("站点地图路径不能为空");
        }
        //获取站点地图 并扫描路径
        File file = new File(args[2]);
        String[] strs = file.getName().split("[.]");
        if (strs.length < fileSplitLength || !fileExtName.equals(strs[1])) {
            throw new RuntimeException("传入的文件路径必须以.xml文件结尾！");
        }
        Document document = XmlUtil.readXML(file);
        List<String> urls = XmlGetUrl.getDocumentUrls(document, "loc");
        BaiDuPush baiDuPush = new BaiDuPush(args[0], args[1]);
        try {
            System.out.println(baiDuPush.postUrl(urls));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
