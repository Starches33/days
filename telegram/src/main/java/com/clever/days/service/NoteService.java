package com.clever.days.service;

import com.clever.days.model.Note;
import com.clever.days.util.Enums;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NoteService {

    private static final String dbApiUrl = "http://localhost:8080";
    private static final String dbNotesApiUrl = dbApiUrl + "/notes";
    private static final String saveNotesApiUrl = dbNotesApiUrl + "/create";
    private static final String findAllNotesByTg = dbNotesApiUrl + "/tg/";

    private final Gson gson = new Gson();

    public void createNote(long tgId, String text, Enums.NoteType noteType, Enums.Sphere sphere) {
        HttpPost post = new HttpPost(saveNotesApiUrl + "?tgId=" + String.valueOf(tgId)
                + "&text=" + text
                + "&noteType=" + noteType.toString());

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            System.out.println("Ответ сервера: " + response.getStatusLine().getStatusCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Note> findNoteListByTgId(long tgId) {
        String urlWithParams = findAllNotesByTg + tgId;
        List<Note> notes = new ArrayList<>();

        HttpGet get = new HttpGet(urlWithParams);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {

            String responseString = EntityUtils.toString(response.getEntity());
            System.out.println("Ответ сервера: " + responseString);

            if (response.getStatusLine().getStatusCode() == 200) {
                Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
                notes = new Gson().fromJson(responseString, listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notes;
    }

}