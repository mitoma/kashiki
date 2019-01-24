package in.tombo.kashiki.keybind.basic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class CopyAction implements Action {

  @Override
  public String name() {
    return "copy";
  }

  @Override
  public void execute(Editor editor, String... args) {
    setClipboardString(editor.getCurrentBuffer().copy());
  }

  private void setClipboardString(String value) {
    StringSelection selection = new StringSelection(value);

    Toolkit toolKit = Toolkit.getDefaultToolkit();
    Clipboard clipboard = toolKit.getSystemClipboard();
    clipboard.setContents(selection, selection);
  }
}
