package service;

import api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Post;
import exception.PostNotExistException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@SuppressWarnings({"ConstantConditions", "resource"})
@Component
public class PostService {
  @Autowired private ApiClient apiClient;

  public Post addPost(String jsonString) throws IOException {
    Request request =
        new Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts")
            .post(RequestBody.create(MediaType.parse("application/json"), jsonString))
            .build();
    Call call = apiClient.getClient().newCall(request);
    Response response = call.execute();
    String responseString = response.body().string();
    return new ObjectMapper().readValue(responseString, Post.class);
  }

  public Post getPost(String id) throws IOException {
    Request request =
        new Request.Builder().url("https://jsonplaceholder.typicode.com/posts/" + id).build();
    Call call = apiClient.getClient().newCall(request);
    Optional<Response> response = Optional.of(call.execute());
    int statusCode = response.get().code();
    if (statusCode != 200) {
      throw new PostNotExistException();
    }
    String stringResponse = response.get().body().string();
    if (stringResponse.length() <= 2) {
      throw new PostNotExistException();
    }
    return new ObjectMapper().readValue(stringResponse, Post.class);
  }

  public Post updatePost(String id, String jsonString) throws IOException {
    Request request =
        new Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts/" + id)
            .put(RequestBody.create(MediaType.parse("application/json"), jsonString))
            .build();
    Call call = apiClient.getClient().newCall(request);
    Optional<Response> response = Optional.of(call.execute());
    if (response.get().code() == 200) {
      String responseString = response.get().body().string();
      return new ObjectMapper().readValue(responseString, Post.class);
    } else {
      throw new PostNotExistException();
    }
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  private boolean checkEmptyObject(Optional<Response> response) throws IOException {
    String jsonObj =
        Optional.of(response.get().body().string()).orElseThrow(PostNotExistException::new);
    return new ObjectMapper().readValue(jsonObj, Post.class).getId() != null;
  }

  public void deletePost(String id) throws IOException {
    Request request =
        new Request.Builder()
            .url("https://jsonplaceholder.typicode.com/posts/" + id)
            .delete()
            .build();
    Call call = apiClient.getClient().newCall(request);
    Optional<Response> response = Optional.of(call.execute());
    if (response.get().code() != 200) {
      throw new PostNotExistException();
    }
  }

  //  public static void main(String[] args) throws IOException {
  //    String jsonString =
  //        "{\n    \"userId\": 420,\n    \"title\": \"Test Title\",\n    \"body\": \"Test Post
  // Body\" \n}";
  //    PostService postService = new PostService();
  //    Post post = postService.addPost(jsonString);
  //    System.out.println(post);
  //
  //    Post post1 = postService.getPost("1");
  //    System.out.println(post1);
  //
  //    Post post2 = postService.updatePost("1", jsonString);
  //    System.out.println(post2);
  //
  //    Post post3 = postService.updatePost("999", jsonString);
  //    System.out.println(post2);
  //  }
}
