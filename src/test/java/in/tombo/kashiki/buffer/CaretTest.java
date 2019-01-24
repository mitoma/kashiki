package in.tombo.kashiki.buffer;

import static org.junit.Assert.*;

import org.junit.Test;

public class CaretTest {

  @Test
  public void testCompareTo() {
    assertEquals(0, new Caret(10, 10).compareTo(new Caret(10, 10)));
    assertEquals(-1, new Caret(10, 10).compareTo(new Caret(10, 11)));
    assertEquals(1, new Caret(10, 10).compareTo(new Caret(10, 9)));

    assertEquals(-1, new Caret(10, 10).compareTo(new Caret(11, 10)));
    assertEquals(1, new Caret(10, 10).compareTo(new Caret(9, 10)));
  }

}
