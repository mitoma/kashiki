package in.tombo.kashiki.buffer;

import java.nio.file.Paths;

public class FileOpenBuffer extends Buffer {

  public FileOpenBuffer() {
    super("FileOpenBuffer", Paths.get(".").toAbsolutePath().normalize().toString());
  }

  @Override
  public void save() {
    // noop
  }

  @Override
  public void next() {
    // noop
  }

  @Override
  public void insertEnter() {
    observer.sendMessage("hogehoge");
  }

}
