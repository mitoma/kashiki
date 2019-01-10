package in.tombo.kashiki.buffer;

import java.util.function.Consumer;

public interface CaretHandler {

  public void head();

  public void last();

  public void back();

  public void forward();

  public void previous();

  public void next();

  public void bufferHead();

  public void bufferLast();

  public void addCaret();

  public void clearCaret();

  public Caret currentCaret();

  public void forEach(Consumer<Caret> consumer);
}
