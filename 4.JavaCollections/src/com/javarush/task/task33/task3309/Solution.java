package com.javarush.task.task33.task3309;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            marshaller.marshal(obj, bos);

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            Document document = null;
            document = builder.parse(bis);

            NodeList nodeList = document.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName())) {
                    node.getParentNode().insertBefore(document.createComment(comment), node);
                }
                if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                    String text = node.getFirstChild().getTextContent();
                    if (checkCDATA(text)) {
                        CDATASection cdataSection = document.createCDATASection(text);
                        node.replaceChild(cdataSection, node.getFirstChild());
                    }
                }
            }

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));

            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean checkCDATA(String text) {
        return text.matches(".*[<>&'\"].*");
    }


    public static void main(String[] args) {
        System.out.println(Solution.toXmlWithComment(new First(), "second", "it's a comment"));
    }
}

@XmlRootElement(name = "first")
class First {
    @XmlElement(name = "second")
    public String item1 = "some string";
    @XmlElement(name = "second")
    public String item2 = "need CDATA because of <second>";
    @XmlElement(name = "second")
    public String item3 = "";
    @XmlElement(name = "third")
    public String item4;
    @XmlElement(name = "forth")
    public Second[] third = new Second[]{new Second()};
    @XmlElement(name = "fifth")
    public String item5 = "need CDATA because of \"";
}

class Second {
    @XmlElement(name = "second")
    public String item1 = "some string";
    @XmlElement(name = "second")
    public String item2 = "need CDATA because of <second>";
}
