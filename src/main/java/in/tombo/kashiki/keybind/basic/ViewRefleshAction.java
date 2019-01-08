package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class ViewRefleshAction implements Action {

  @Override
  public String name() {
    return "view-reflesh";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.reflesh();
  }

}
