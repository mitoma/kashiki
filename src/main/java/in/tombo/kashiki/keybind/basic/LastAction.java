package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class LastAction implements Action {

  @Override
  public String name() {
    return "last";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.getCurrentBuffer().last();
  }

}
