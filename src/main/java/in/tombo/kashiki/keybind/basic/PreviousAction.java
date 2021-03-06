package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class PreviousAction implements Action {

  @Override
  public String name() {
    return "previous";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.getCurrentBuffer().previous();
  }

}
