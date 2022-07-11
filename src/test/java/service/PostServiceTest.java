package service;

import api.ApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import context.AppConfig;
import entity.Post;
import exception.PostNotExistException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class PostServiceTest {

  @InjectMocks private PostService postService;

  @Mock private ApiClient apiClient;

  @Test
  public void addPost() throws IOException {
    //    Arrange
    String jsonString =
        "{\"userId\": 1, \"title\": \"Sample test\", \"body\": \"Sample test body\",\"id\": \"101\"}";
    Post expectedPost = new ObjectMapper().readValue(jsonString, Post.class);
    ResponseBody responseBody =
        ResponseBody.create(MediaType.parse("application/json"), jsonString);
    Response response =
        new Response.Builder()
            .request(new Request.Builder().url("http://localhost:8080/posts").build())
            .protocol(Protocol.HTTP_1_1)
            .code(201)
            .message("")
            .body(responseBody)
            .build();
    Call call = mock(Call.class);
    OkHttpClient okHttpClient = mock(OkHttpClient.class);
    when(apiClient.getClient()).thenReturn(okHttpClient);
    when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
    when(call.execute()).thenReturn(response);

    //    Act
    Post actualPost = postService.addPost(jsonString);

    //    Assert
    assertThat(actualPost).isEqualTo(expectedPost);
  }

  @Test
  public void getPost() throws IOException {
    //    Arrange
    String jsonString =
        "{\"userId\": 1, \"title\": \"Sample test\", \"body\": \"Sample test body\",\"id\": \"101\"}";
    Post expectedPost = new ObjectMapper().readValue(jsonString, Post.class);
    ResponseBody responseBody =
        ResponseBody.create(MediaType.parse("application/json"), jsonString);
    Response response =
        new Response.Builder()
            .request(new Request.Builder().url("http://localhost:8080/posts").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(responseBody)
            .build();
    Call call = mock(Call.class);
    OkHttpClient okHttpClient = mock(OkHttpClient.class);
    when(apiClient.getClient()).thenReturn(okHttpClient);
    when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
    when(call.execute()).thenReturn(response);

    //    Act
    Post actualPost = postService.getPost("101");

    //    Assert
    assertThat(actualPost).isEqualTo(expectedPost);
  }

  @Test
  public void throwExceptionsIfPostNotFound() throws IOException {
    //    Arrange
    String jsonString = "{}";
    Post expectedPost = new ObjectMapper().readValue(jsonString, Post.class);
    ResponseBody responseBody =
        ResponseBody.create(MediaType.parse("application/json"), jsonString);
    Response response =
        new Response.Builder()
            .request(new Request.Builder().url("http://localhost:8080/posts").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(responseBody)
            .build();
    Call call = mock(Call.class);
    OkHttpClient okHttpClient = mock(OkHttpClient.class);
    when(apiClient.getClient()).thenReturn(okHttpClient);
    when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
    when(call.execute()).thenReturn(response);

    //    Act & Assert
    assertThatThrownBy(() -> postService.getPost("101")).isInstanceOf(PostNotExistException.class);
  }

  @Test
  public void updatePost() throws IOException {
    //    Arrange
    String jsonString =
        "{\"userId\": 1, \"title\": \"Sample test\", \"body\": \"Sample test body\",\"id\": \"101\"}";
    Post expectedPost = new ObjectMapper().readValue(jsonString, Post.class);
    ResponseBody responseBody =
        ResponseBody.create(MediaType.parse("application/json"), jsonString);
    Response response =
        new Response.Builder()
            .request(new Request.Builder().url("http://localhost:8080/posts").build())
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(responseBody)
            .build();
    Call call = mock(Call.class);
    OkHttpClient okHttpClient = mock(OkHttpClient.class);
    when(apiClient.getClient()).thenReturn(okHttpClient);
    when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
    when(call.execute()).thenReturn(response);

    //    Act
    Post actualPost = postService.updatePost("101", jsonString);

    //    Assert
    assertThat(actualPost.getUserId()).isEqualTo(expectedPost.getUserId());
  }

  @Test
  public void throwExceptionWhenUpdatePostThatDidNotExists() throws IOException {
    //    Arrange
    Response response =
        new Response.Builder()
            .request(new Request.Builder().url("http://localhost:8080/posts").build())
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message("")
            .body(null)
            .build();
    Call call = mock(Call.class);
    OkHttpClient okHttpClient = mock(OkHttpClient.class);
    when(apiClient.getClient()).thenReturn(okHttpClient);
    when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
    when(call.execute()).thenReturn(response);

    //    Act & Assert
    assertThatThrownBy(() -> postService.getPost("879")).isInstanceOf(PostNotExistException.class);
  }
}
