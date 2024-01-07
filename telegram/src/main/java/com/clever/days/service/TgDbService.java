package com.clever.days.service;

import com.clever.days.model.User;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class TgDbService {

    private static final String dbApiUrl = "http://localhost:8080/api/v1";

    //users
    private static final String dbUsersApiUrl = dbApiUrl + "/users";
    private static final String saveUserApiUrl = dbUsersApiUrl + "/create";
    private static final String findUserByTg = dbUsersApiUrl + "/tg/";

    private final Gson gson = new Gson();

    public void createUser(long tgId, String username) {
        String json = String.format("{\"tgId\":\"%s\", \"username\":\"%s\"}", tgId, username);
        HttpPost post = new HttpPost(saveUserApiUrl);
        StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);

        post.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println("Ответ сервера: " + response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findUserByTgId(long tgId) {
        String urlWithParams = findUserByTg + tgId;
        User user = null;
        HttpGet get = new HttpGet(urlWithParams);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {

            String responseString = EntityUtils.toString(response.getEntity());
            System.out.println("Ответ сервера: " + responseString);

            if (response.getStatusLine().getStatusCode() == 200) {
                user = gson.fromJson(responseString, User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
