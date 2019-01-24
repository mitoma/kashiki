package in.tombo.kashiki.keybind.basic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class PasteAction implements Action {

  @Override
  public String name() {
    return "paste";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.getCurrentBuffer().insertString(getClipboardString());
  }

  private String getClipboardString() {
    Toolkit toolKit = Toolkit.getDefaultToolkit();
    Clipboard clipboard = toolKit.getSystemClipboard();
    try {
      return clipboard.getData(DataFlavor.stringFlavor).toString();
    } catch (UnsupportedFlavorException | IOException e1) {
      e1.printStackTrace();
    }
    return "";
  }
}
