package entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class Post {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("userId")
  private Integer userId;

  @JsonProperty("title")
  private String title;

  @JsonProperty("body")
  private String body;
}
