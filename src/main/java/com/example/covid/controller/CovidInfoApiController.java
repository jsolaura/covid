package com.example.covid.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/")
public class CovidInfoApiController {

    @GetMapping("/api/covid")
    public static Map<String, Object> getCovid() throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        String serviceKey = "MXK5FWJU%2BAENSsKL0p9RUS0xLqIuFjN5MK7PtAMeoWRuKL2Y4XtjyFbeXPmF48FpvNLBPmw5ZG5dW6YNil34lg%3D%3D";
        String apiUrl = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";

        try {
            StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("startCreateDt", "UTF-8") + "=" + URLEncoder.encode("20220101", "UTF-8")); /*검색할 생성일 범위의 시작*/
            urlBuilder.append("&" + URLEncoder.encode("endCreateDt", "UTF-8") + "=" + URLEncoder.encode("20220107", "UTF-8")); /*검색할 생성일 범위의 종료*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
            String xmlJSONObjString = xmlJSONObj.toString();
//            System.out.println("### xmlJSONObjString => " + xmlJSONObjString);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<>();
            map = objectMapper.readValue(xmlJSONObjString, new TypeReference<Map<String, Object>>() {});
            Map<String, Object> dataRespopns = (Map<String, Object>) map.get("response");
            Map<String, Object> body = (Map<String, Object>) dataRespopns.get("body");
            Map<String, Object> items = null;
            List<Map<String, Object>> itemList = null;

            items = (Map<String, Object>) body.get("items");
            itemList = (List<Map<String, Object>>) items.get("item");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
            // 오늘 어제 날짜 구하기
//            LocalDate now = LocalDate.now();
//            LocalDate yesterday = now.minusDays(1);
            List<Object> countList = new ArrayList<>();
            List<Object> dateList = new ArrayList<>();

            List<Object> totalList = new ArrayList<>();

            for (int i = 0; i < itemList.size(); i++) {
                Map<String, Object> detailList = itemList.get(i);

                // 문자열 형식 날짜 -> 날짜 형식 포맷
                String createDtData = (String) detailList.get("createDt");
                String date = createDtData.substring(0, 10);

                LocalDate createDt = LocalDate.parse(date, formatter);
                Integer decideCnt = (Integer) detailList.get("decideCnt");

                countList.add(decideCnt);
                dateList.add(createDt);



                resultMap.put("data", detailList);
            }
            Integer countResult = (Integer) countList.get(0) - (Integer) countList.get(1);
            System.out.println(countResult); // 금 : 3717

            totalList.add(countList);
            totalList.add(dateList);
            System.out.println(totalList);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.clear();
            resultMap.put("Result", "0001");
        }
        return resultMap;
    }
}




//    @Value("${openapi.service.key}")
//    String OPENAPI_KEY;
//
//    @Value("${openapi.service.url}")
//    String OPENAPI_URL;
//
//    @GetMapping("/covid19")
//    public String getCovid() throws IOException {
//        StringBuilder result = new StringBuilder();
//
//            String apiUrl = OPENAPI_URL + "?serviceKey=" + OPENAPI_KEY +
//                        "&pageNo=1" +
//                        "&numOfRows=10" +
//                        "&startCreateDt=20220101" +
//                        "&endCreateDt=20220106";
//            URL url = new URL(apiUrl);
//
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//
//            BufferedReader bufferedReader;
//
//            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//
//            String returnLine;
//
//            while ((returnLine = bufferedReader.readLine()) != null) {
//                result.append(returnLine + "\n");
//            }
//            urlConnection.disconnect();
//
//        return result.toString();
//    }
//
//

