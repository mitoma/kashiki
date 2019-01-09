package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class ReturnAction implements Action {

  @Override
  public String name() {
    return "return";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.getCurrentBuffer().insertString("\n");
  }

}
