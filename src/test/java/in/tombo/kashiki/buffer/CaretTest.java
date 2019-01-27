package in.tombo.kashiki.buffer;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CaretTest {

  @Test
  public void testCompareTo() {
    assertThat(new Caret(10, 10)).isEqualTo(new Caret(10, 10));
    assertThat(new Caret(10, 10)).isLessThan(new Caret(10, 11));
    assertThat(new Caret(10, 10)).isGreaterThan(new Caret(10, 9));

    assertThat(new Caret(10, 10)).isLessThan(new Caret(11, 10));
    assertThat(new Caret(10, 10)).isGreaterThan(new Caret(9, 10));
  }

}
