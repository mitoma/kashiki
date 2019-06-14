package in.tombo.kashiki;

import static in.tombo.kashiki.keybind.SupportKey.*;

import in.tombo.kashiki.keybind.KashikiKeyListener;
import in.tombo.kashiki.keybind.SupportKey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

public class KeyListenerAdapter implements KeyListener {

  private final KashikiKeyListener keyListener;

  public KeyListenerAdapter(KashikiKeyListener keyListener) {
    this.keyListener = keyListener;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    final SupportKey key;
    if (e.isControlDown() && e.isAltDown() && e.isShiftDown()) {
      key = CTRL_ALT_SHIFT;
    } else if (e.isControlDown() && e.isAltDown()) {
      key = CTRL_ALT;
    } else if (e.isControlDown() && e.isShiftDown()) {
      key = CTRL_SHIFT;
    } else if (e.isAltDown() && e.isShiftDown()) {
      key = ALT_SHIFT;
    } else if (e.isControlDown()) {
      key = CTRL;
    } else if (e.isAltDown()) {
      key = ALT;
    } else if (e.isShiftDown()) {
      key = SHIFT;
    } else {
      key = NONE;
    }
    SwingUtilities.invokeLater(() -> keyListener.keyPressed(key, e.getKeyCode(), e.getWhen()));
  }

  @Override
  public void keyTyped(KeyEvent e) {
    SwingUtilities.invokeLater(() -> keyListener.keyTyped(e.getKeyChar(), e.getWhen()));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // noop
  }

}
