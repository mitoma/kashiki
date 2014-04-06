package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class HeadAction implements Action {

  @Override
  public String name() {
    return "head";
  }

  @Override
  public void execute(String... args) {
    Editor.getInstance().getCurrentBuffer().head();
  }

}
