package in.tombo.kashiki;

import static in.tombo.kashiki.keybind.SupportKey.*;
import in.tombo.kashiki.keybind.SupportKey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

public class DocumentKeyListener implements KeyListener {

  private final Editor editor;

  public DocumentKeyListener(Editor editor) {
    this.editor = editor;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    SupportKey key;
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
    SwingUtilities.invokeLater(() -> editor.keyPressed(key, e.getKeyCode(), e.getWhen()));
  }

  @Override
  public void keyTyped(KeyEvent e) {
    SwingUtilities.invokeLater(() -> editor.keyTyped(e.getKeyChar(), e.getWhen()));
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // noop
  }

}
