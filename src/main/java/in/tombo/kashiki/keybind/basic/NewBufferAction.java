package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class NewBufferAction implements Action {

  @Override
  public String name() {
    return "new-buffer";
  }

  @Override
  public void execute(String... args) {
    Editor.getInstance().createNewBuffer();
  }

}
