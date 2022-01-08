package com.example.covid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class CovidService {

    public static void xmlToJson(String str) {
        try {
            String xml = str;
            JSONObject jsonObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jsonObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException{
        String key = "MXK5FWJU%2BAENSsKL0p9RUS0xLqIuFjN5MK7PtAMeoWRuKL2Y4XtjyFbeXPmF48FpvNLBPmw5ZG5dW6YNil34lg%3D%3D";
        String openapi = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
        String apiUrl = openapi + "?serviceKey=" + key +
                "&pageNo=1" +
                "&numOfRows=10" +
                "&startCreateDt=20220101" +
                "&endCreateDt=20220106";
        URL url = new URL(apiUrl);
        BufferedReader bf;
        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        String result = bf.readLine();
        xmlToJson(result);
    }
}
