package in.tombo.kashiki.keybind;


import java.awt.event.KeyEvent;
import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmacsKeyListenerTest {
  private EmacsKeyListener listener;

  @BeforeEach
  public void setUp() {
    listener = new EmacsKeyListener(null, null);
  }

  @Test
  public void testSetUp() {
    try {
      listener.setup();
      System.out.println(listener.keybinds);
    } catch (IOException e) {
      Assertions.fail(e.getMessage());
    }
  }

  @Test
  public void testKeyPressed() {
    listener.keyPressed(SupportKey.CTRL, KeyEvent.VK_Q, System.currentTimeMillis());
  }

}
