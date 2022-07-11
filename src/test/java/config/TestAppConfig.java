package config;

import api.ApiClient;
import okhttp3.OkHttpClient;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.PostService;

@Configuration
public class TestAppConfig {

  @Bean
  public PostService postService() {
    return new PostService();
  }

//  @Bean
//  public ApiClient apiClient() {
//    return Mockito.mock(ApiClient.class);
//  }
//
//  @Bean
//  public OkHttpClient okHttpClient() {
//    return Mockito.mock(OkHttpClient.class);
//  }
}
