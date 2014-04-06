package in.tombo.kashiki.view;

import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.view.BufferView;

import org.junit.Before;
import org.junit.Test;

public class BufferViewTest {

  Buffer buffer;

  @Before
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
