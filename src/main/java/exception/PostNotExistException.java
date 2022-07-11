package exception;

public class PostNotExistException extends RuntimeException {
  public PostNotExistException() {
    super("Post not exist");
  }
}
