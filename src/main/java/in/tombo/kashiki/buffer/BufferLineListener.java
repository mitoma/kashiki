package in.tombo.kashiki.buffer;

public interface BufferLineListener {
  public void update(BufferLine bl);

  public void addChar(BufferChar bufferChar);

  public void removeChar(BufferChar bufferChar);

}
