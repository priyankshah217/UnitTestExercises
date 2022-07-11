package api;

import okhttp3.OkHttpClient;

public class ApiClient {
  private OkHttpClient client;

  public ApiClient() {
    client = new OkHttpClient().newBuilder().build();
  }

  public OkHttpClient getClient() {
    return client;
  }
}
