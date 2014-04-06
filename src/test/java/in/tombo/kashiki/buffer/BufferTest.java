package in.tombo.kashiki.buffer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.buffer.Caret;

import org.junit.Before;
import org.junit.Test;

public class BufferTest {

  Buffer buf;

  @Before
  public void setup() {
    buf = new Buffer("testBuffer", "");
  }

  @Test
  public void initialState() {
    assertThat(buf.getLines().size(), is(1));
    assertThat(buf.getLines().getFirst().getLength(), is(0));
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
    buf.forward();
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
    buf.back();
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
    buf.previous();
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
    buf.next();
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
  }

  @Test
  public void insertString1() {
    buf.insertString("abcde");
    assertThat(buf.getCaret(), is(new Caret(0, 5)));
    buf.insertEnter();
    assertThat(buf.getCaret(), is(new Caret(1, 0)));
  }

  @Test
  public void insertCRLF() {
    buf.insertString("\r\n");
    assertThat(buf.getCaret(), is(new Caret(1, 0)));
  }

  @Test
  public void insertLF() {
    buf.insertString("\n");
    assertThat(buf.getCaret(), is(new Caret(1, 0)));
  }

  @Test
  public void insertEmpty() {
    buf.insertString("");
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
  }

  @Test
  public void insertEnter() {
    buf.insertString("abcde\n12345\n");
    assertThat(buf.getCaret(), is(new Caret(2, 0)));
    buf.back();
    assertThat(buf.getCaret(), is(new Caret(1, 5)));
    buf.head();
    buf.previous();
    assertThat(buf.getCaret(), is(new Caret(0, 0)));
    buf.forward();
    buf.forward();
    buf.insertEnter();
    assertThat(buf.getCaret(), is(new Caret(1, 0)));
    assertThat(buf.toBufferString(), is("ab\ncde\n12345\n"));
  }

  @Test
  public void backspace() {
    buf.insertString("abcde\n12345\n");
    assertThat(buf.getCaret(), is(new Caret(2, 0)));
    buf.backspace();
    assertThat(buf.toBufferString(), is("abcde\n12345"));
    for (int i = 0; i < 5; i++) {
      buf.backspace();
    }
    assertThat(buf.toBufferString(), is("abcde\n"));
    for (int i = 0; i < 5; i++) {
      buf.backspace();
    }
    assertThat(buf.toBufferString(), is("a"));
    buf.backspace();
    assertThat(buf.toBufferString(), is(""));
  }

  @Test
  public void delete() {
    buf.insertString("abcde\n12345\n");
    assertThat(buf.getCaret(), is(new Caret(2, 0)));
    buf.previous();
    buf.forward();
    buf.forward();
    assertThat(buf.getCaret(), is(new Caret(1, 2)));
    buf.delete();
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString(), is("abcde\n12\n"));
    buf.delete();
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString(), is("abcde\n12"));
    assertThat(buf.getCaret(), is(new Caret(1, 2)));
    buf.bufferHead();
    buf.delete();
    buf.delete();
    buf.delete();
    buf.delete();
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString(), is("12"));
    buf.delete();
    buf.delete();
    assertThat(buf.toBufferString(), is(""));
    buf.delete();
    assertThat(buf.toBufferString(), is(""));
  }

}
