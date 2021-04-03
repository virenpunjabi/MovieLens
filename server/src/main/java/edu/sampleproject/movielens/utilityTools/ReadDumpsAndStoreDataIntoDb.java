package edu.sampleproject.movielens.utilityTools;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ReadDumpsAndStoreDataIntoDb {

    public static final String dbURL = "http://localhost:9200";

    public static <T, R> R getGenericObjectFromJson(String jsonFilePath, T t, R r) {
        Gson gson = new Gson();
        File file = new File(jsonFilePath);
        try {
            return gson.fromJson(new JsonReader(new FileReader(file)), (Type) t);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Map<String, LinkedTreeMap<String, String>>> map = getGenericObjectFromJson("server/src/main/resources/dbdumps.json", ArrayList.class, new ArrayList<>());
        processDataMap(map);
        System.out.println(map);

    }

    private static void processDataMap(List<Map<String, LinkedTreeMap<String, String>>> list) {
        for (Map<String, LinkedTreeMap<String, String>> iterator : list) {
            //if(entry.)
            for (Map.Entry entry : iterator.entrySet()) {
                if (((String) entry.getKey()).equalsIgnoreCase("PUT")) {
                    createIndex(entry);
                } else {
                    insertDataIntoIndex(entry);
                }
            }
        }
    }

    private static void insertDataIntoIndex(Map.Entry entry) {
        LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) entry.getValue();
        String indexUrl = dbURL + map.get("appendUrl");
        String createQuery = map.get("value");

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(indexUrl);
            StringEntity entity = new StringEntity(createQuery);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200)
                System.out.println("Data inserted successfully. i=" + indexUrl);

            client.close();

        } catch (Exception e) {
            System.out.println("Error occurred while inserting data into index. i=" + indexUrl + " e=" + e.getMessage());
            e.printStackTrace();
        }
    }


    private static void createIndex(Map.Entry entry) {
        LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) entry.getValue();
        String indexUrl = dbURL + map.get("appendUrl");
        String createQuery = map.get("value");

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(indexUrl);
            StringEntity entity = new StringEntity(createQuery);
            httpPut.setEntity(entity);
            httpPut.setHeader("Content-type", "application/json");

            CloseableHttpResponse response = client.execute(httpPut);
            if (response.getStatusLine().getStatusCode() == 200)
                System.out.println("Index created successfully. i=" + indexUrl);

            client.close();

        } catch (Exception e) {
            System.out.println("Error occurred while creating index. i=" + indexUrl + " e=" + e.getMessage());
            e.printStackTrace();
        }
    }
}
