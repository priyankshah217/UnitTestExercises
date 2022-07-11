package api;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiClient {
  @Autowired private OkHttpClient client;

  public OkHttpClient getClient() {
    return client;
  }
}
