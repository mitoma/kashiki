package in.tombo.kashiki;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoggingKeyListener implements KeyListener {

  public LoggingKeyListener() {}

  @Override
  public void keyTyped(KeyEvent e) {
    logging("Typed", e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    logging("Press", e);
  }

  private void logging(String string, KeyEvent e) {
    // System.out.println(String.format("%s\ttime:%s\tKey:%s\tChar:%s", string,
    // String.valueOf(e.getWhen()), KeyEvent.getKeyText(e.getKeyCode()),
    // String.valueOf(e.getKeyChar())));
  }

  @Override
  public void keyReleased(KeyEvent e) {}

}
