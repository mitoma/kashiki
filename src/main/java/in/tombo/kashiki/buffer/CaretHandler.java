package in.tombo.kashiki.buffer;

public interface CaretHandler {

  public void addCaret();

  public void clearCaret();

  public void head();

  public void last();

  public void back();

  public void forward();

  public void previous();

  public void next();

  public void bufferHead();

  public void bufferLast();
}
