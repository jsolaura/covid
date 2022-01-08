package com.example.covid.api;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class CovidAPI {
    public static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null) return null;

        return nValue.getNodeValue();
    }

    public void getApi() throws ParserConfigurationException, IOException, SAXException {
        try {
            String key = "MXK5FWJU%2BAENSsKL0p9RUS0xLqIuFjN5MK7PtAMeoWRuKL2Y4XtjyFbeXPmF48FpvNLBPmw5ZG5dW6YNil34lg%3D%3D";
            String openapi = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
            String apiUrl = openapi + "?serviceKey=" + key +
                    "&pageNo=1" +
                    "&numOfRows=10" +
                    "&startCreateDt=20220101" +
                    "&endCreateDt=20220106";
            Document documentInfo = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(apiUrl);
            documentInfo.getDocumentElement().normalize();
            NodeList nodeList = documentInfo.getElementsByTagName("list");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node nNode = nodeList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    System.out.println("#######################");
                    System.out.println("기준일" + getTagValue("CREATE_DT", element));
                    System.out.println("사망자수" + getTagValue("DEATH_CNT", element));
                    System.out.println("확진자수" + getTagValue("DECIDE_CNT", element));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
