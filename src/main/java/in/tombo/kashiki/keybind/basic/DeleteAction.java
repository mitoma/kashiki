package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class DeleteAction implements Action {

  @Override
  public String name() {
    return "delete";
  }

  @Override
  public void execute(String... args) {
    Editor.getInstance().getCurrentBuffer().delete();
  }

}
