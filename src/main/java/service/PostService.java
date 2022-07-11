package service;

import api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Post;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class PostService {
  public Post addPost(String jsonString) throws IOException {
    Request request =
        new Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts")
            .post(RequestBody.create(MediaType.parse("application/json"), jsonString))
            .build();
    ApiClient apiClient = new ApiClient();
    Response response = apiClient.getClient().newCall(request).execute();
    String responseString = Objects.requireNonNull(response.body()).string();
    return new ObjectMapper().readValue(responseString, Post.class);
  }

  public Post getPost(String id) throws IOException {
    Request request =
        new Request.Builder().url("https://jsonplaceholder.typicode.com/posts/" + id).build();
    ApiClient apiClient = new ApiClient();
    Response response = apiClient.getClient().newCall(request).execute();
    String responseString = Objects.requireNonNull(response.body()).string();
    return new ObjectMapper().readValue(responseString, Post.class);
  }

  public static void main(String[] args) throws IOException {
    String jsonString =
        "{\n    \"userId\": 420,\n    \"title\": \"Test Title\",\n    \"body\": \"Test Post Body\" \n}";
    PostService postService = new PostService();
    Post post = postService.addPost(jsonString);
    System.out.println(post);

    Post post1 = postService.getPost("1");
    System.out.println(post1);
  }
}
