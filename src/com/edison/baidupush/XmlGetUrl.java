package com.edison.baidupush;

import org.w3c.dom.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 陈俊锋
 */
public class XmlGetUrl {
    /**
     * 根据Document获取链接
     * @param document 文本对象
     * @param tagName url存放节点名称
     * @return
     */
    public static List<String> getDocumentUrls(Document document, String tagName) {
        List<String> urls = new LinkedList<>();
        Element element = document.getDocumentElement();
        getElementUrls(element, tagName, urls);
        return urls;
    }

    /**
     * 递归根据节点获取urls
     * @param element 节点对象
     * @param tagName url存放节点名称
     * @param urls 链接存储对象
     */
    private static void getElementUrls(Element element, String tagName, List<String> urls) {
        NodeList nodeList = element.getChildNodes();
        Node childNode;
        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            childNode = nodeList.item(temp);

            // 判断是否属于节点
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                // 判断是否还有子节点
                if (tagName.equals(((Element)childNode).getTagName())) {
                    urls.add(childNode.getTextContent());
                } else if(childNode.hasChildNodes()){
                    getElementUrls((Element) childNode, tagName, urls);
                }
            }
        }
    }
}
