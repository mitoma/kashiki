package in.tombo.kashiki.buffer;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BufferTest {

  Buffer buf;

  @BeforeEach
  public void setup() {
    buf = new Buffer("testBuffer", "");
  }

  @Test
  public void initialState() {
    assertThat(buf.getLines()).hasSize(1);
    assertThat(buf.getLines().getFirst().getLength()).isEqualTo(0);
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
    buf.forward();
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
    buf.back();
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
    buf.previous();
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
    buf.next();
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
  }

  @Test
  public void insertString1() {
    buf.insertString("abcde");
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 5));
    buf.insertEnter();
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 0));
  }

  @Test
  public void insertCRLF() {
    buf.insertString("\r\n");
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 0));
  }

  @Test
  public void insertLF() {
    buf.insertString("\n");
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 0));
  }

  @Test
  public void insertEmpty() {
    buf.insertString("");
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
  }

  @Test
  public void insertEnter() {
    buf.insertString("abcde\n12345\n");
    assertThat(buf.getCaret()).isEqualTo(new Caret(2, 0));
    buf.back();
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 5));
    buf.head();
    buf.previous();
    assertThat(buf.getCaret()).isEqualTo(new Caret(0, 0));
    buf.forward();
    buf.forward();
    buf.insertEnter();
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 0));
    assertThat(buf.toBufferString()).isEqualTo("ab\ncde\n12345\n");
  }

  @Test
  public void backspace() {
    buf.insertString("abcde\n12345\n");
    assertThat(buf.getCaret()).isEqualTo(new Caret(2, 0));
    buf.backspace();
    assertThat(buf.toBufferString()).isEqualTo("abcde\n12345");
    for (int i = 0; i < 5; i++) {
      buf.backspace();
    }
    assertThat(buf.toBufferString()).isEqualTo("abcde\n");
    for (int i = 0; i < 5; i++) {
      buf.backspace();
    }
    assertThat(buf.toBufferString()).isEqualTo("a");
    buf.backspace();
    assertThat(buf.toBufferString()).isEqualTo("");
  }

  @Test
  public void delete() {
    buf.insertString("abcde\n12345\n");
    assertThat(buf.getCaret()).isEqualTo(new Caret(2, 0));
    buf.previous();
    buf.forward();
    buf.forward();
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 2));
    buf.delete();
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString()).isEqualTo("abcde\n12\n");
    buf.delete();
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString()).isEqualTo("abcde\n12");
    assertThat(buf.getCaret()).isEqualTo(new Caret(1, 2));
    buf.bufferHead();
    buf.delete();
    buf.delete();
    buf.delete();
    buf.delete();
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString()).isEqualTo("12");
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString()).isEqualTo("");
    buf.delete();
    assertThat(buf.toBufferString()).isEqualTo("");
  }

}
