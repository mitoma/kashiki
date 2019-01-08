package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.keybind.Action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class KillRingAction implements Action {

  @Override
  public String name() {
    return "kill-ring";
  }

  @Override
  public void execute(Editor editor, String... args) {
    Buffer currentBuffer = editor.getCurrentBuffer();
    if (currentBuffer.isLineLast()) {
      currentBuffer.delete();
    } else {
      currentBuffer.mark();
      currentBuffer.last();
      setClipboardString(currentBuffer.copy());
      currentBuffer.cut();
    }
  }

  private void setClipboardString(String value) {
    StringSelection selection = new StringSelection(value);

    Toolkit toolKit = Toolkit.getDefaultToolkit();
    Clipboard clipboard = toolKit.getSystemClipboard();
    clipboard.setContents(selection, selection);
  }
}
