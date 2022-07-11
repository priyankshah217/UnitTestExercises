package context;

import api.ApiClient;
import entity.Post;
import okhttp3.OkHttpClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.PostService;

import java.io.IOException;

@Configuration
public class AppConfig {

  @Bean
  public PostService postService() {
    return new PostService();
  }

  @Bean
  public ApiClient apiClient() {
    return new ApiClient();
  }

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient().newBuilder().build();
  }

  public static void main(String[] args) throws IOException {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    PostService postService = context.getBean(PostService.class);
    Post post =
        postService.updatePost(
            "1", "{\"userId\": 1, \"title\": \"Priyank test\", \"body\": \"Priyank test\"}");
    System.out.println(post);
  }
}
