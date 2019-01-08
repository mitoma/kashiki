package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class BackAction implements Action {

  @Override
  public String name() {
    return "back";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.getCurrentBuffer().back();
  }

}
