package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class BackspaceAction implements Action {

  @Override
  public String name() {
    return "backspace";
  }

  @Override
  public void execute(String... args) {
    Editor.getInstance().getCurrentBuffer().backspace();
  }

}
