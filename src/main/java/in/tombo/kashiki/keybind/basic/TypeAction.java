package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class TypeAction implements Action {

  @Override
  public String name() {
    return "type";
  }

  @Override
  public void execute(String... args) {
    for (String string : args) {
      Editor.getInstance().getCurrentBuffer().insertString(string);
    }
  }

}
