package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.keybind.Action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CutAction implements Action {

  @Override
  public String name() {
    return "cut";
  }

  @Override
  public void execute(String... args) {
    Buffer currentBuffer = Editor.getInstance().getCurrentBuffer();
    setClipboardString(currentBuffer.copy());
    currentBuffer.cut();
  }

  private void setClipboardString(String value) {
    StringSelection selection = new StringSelection(value);

    Toolkit toolKit = Toolkit.getDefaultToolkit();
    Clipboard clipboard = toolKit.getSystemClipboard();
    clipboard.setContents(selection, selection);
  }
}
