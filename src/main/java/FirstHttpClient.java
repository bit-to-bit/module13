import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class FirstHttpClient {

    // 1-1 POST User
    public static void postUser() throws UnsupportedEncodingException {

        // витягнемо json

        Path filePath = Path.of("data\\postUsers.json");
        String users = "";

        try {
            users = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost request = new HttpPost("https://jsonplaceholder.typicode.com/users");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(users));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("status => " + response.getStatusLine().toString());
                System.out.println("response => " + EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 1-2 PUT User
    public static void putUser() throws UnsupportedEncodingException {

        // витягнемо json
        Path filePath = Path.of("data\\putUsers.json");
        String users = "";

        try {
            users = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPut request = new HttpPut("https://jsonplaceholder.typicode.com/users/3");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(users));

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("status => " + response.getStatusLine().toString());
                System.out.println("response => " + EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 1-3 DELETE User
    public static void deleteUser() throws UnsupportedEncodingException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpDelete request = new HttpDelete("https://jsonplaceholder.typicode.com/users/4");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("status => " + response.getStatusLine().toString());
                System.out.println("response => " + EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 1-4 GET All User
    public static void getAllUser() throws UnsupportedEncodingException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/users");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("status => " + response.getStatusLine().toString());
                System.out.println("response => " + EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 1-5 GET User by ID
    public static void getUserByID() throws UnsupportedEncodingException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/users/5");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("status => " + response.getStatusLine().toString());
                System.out.println("response => " + EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 1-6 GET User by UserName
    public static void getUserByUserName() throws UnsupportedEncodingException {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/users?username=Maxime_Nienow");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("status => " + response.getStatusLine().toString());
                System.out.println("response => " + EntityUtils.toString(response.getEntity()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 2-1 GET All Comments Of MAX Post To JSON
    public static void getAllCommentsToJson(int userId) {

        ArrayList<UserPost> allUserPosts = new ArrayList<>();
        String json = "";

        // GET posts
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/users/" + userId + "/posts");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                json = EntityUtils.toString(response.getEntity());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(ArrayList.class, UserPost.class).getType();
        allUserPosts = gson.fromJson(json, type);

        Integer maxPostId = allUserPosts.stream()
                .map(UserPost::getId)
                .max(Integer::compare)
                .get();


        // GET Comments
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/posts/" + maxPostId + "/comments");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                json = EntityUtils.toString(response.getEntity());

                // write json to file
                File file = new File("data\\user-" + userId + "-post-" + maxPostId + "-comments.json");

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(json);
                    writer.flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 3-1 GET All Open Tasks
    public static void getAllOpenTasks(int userId) {

        ArrayList<UserTask> allUserTasks = new ArrayList<>();
        String json = "";

        // GET posts
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("https://jsonplaceholder.typicode.com/users/" + userId + "/todos");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                json = EntityUtils.toString(response.getEntity());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(ArrayList.class, UserTask.class).getType();
        allUserTasks = gson.fromJson(json, type);

        System.out.println("allUserOpenTasks = " + allUserTasks.stream()
                .filter(item -> !item.isCompleted())
                .collect(Collectors.toList()));
    }

}
