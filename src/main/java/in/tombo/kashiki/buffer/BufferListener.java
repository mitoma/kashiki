package in.tombo.kashiki.buffer;

public interface BufferListener {

  public void update(Buffer buffer);

  public void updateCaret(Caret caret);

  public void addLine(BufferLine bufferLine);

  public void removeLine(BufferLine bufferLine);

  public void moveChar(BufferLine fromLine, BufferLine toLine, BufferChar c);

  public void setMark(Caret caret);

  public void sendMessage(String string);
}
