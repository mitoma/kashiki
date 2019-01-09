package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class ExitNavyAction implements Action {

  @Override
  public String name() {
    return "exit-navy";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.tearDown();
    System.exit(0);
  }
}
