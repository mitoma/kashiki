package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class NextAction implements Action {

  @Override
  public String name() {
    return "next";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.getCurrentBuffer().next();
  }

}
