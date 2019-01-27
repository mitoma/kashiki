package in.tombo.kashiki.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import in.tombo.kashiki.buffer.Buffer;

public class BufferViewTest {

  Buffer buffer;

  @BeforeEach  
  public void setup() {
    buffer = new Buffer("testBuffer", "あいうえお");

    BufferView listener = new BufferView(buffer);
    buffer.addListener(listener);
  }

  @Test
  public void test() {
    buffer.back();
    buffer.backspace();
    buffer.delete();
    buffer.insertEnter();
  }

}
