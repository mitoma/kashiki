package in.tombo.kashiki.keybind;


import java.awt.event.KeyEvent;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmacsKeyListenerTest {
  private EmacsKeyListener listener;

  @Before
  public void setUp() {
    listener = new EmacsKeyListener(null, null);
  }

  @Test
  public void testSetUp() {
    try {
      listener.setup();
      System.out.println(listener.keybinds);
    } catch (IOException e) {
      Assert.fail(e.getMessage());
    }
  }

  @Test
  public void testKeyPressed() {
    listener.keyPressed(SupportKey.CTRL, KeyEvent.VK_Q, System.currentTimeMillis());
  }

}
