package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class ForwardAction implements Action {

  @Override
  public String name() {
    return "forward";
  }

  @Override
  public void execute(String... args) {
    Editor.getInstance().getCurrentBuffer().forward();
  }

}
