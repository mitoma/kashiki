package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;

public class FullScreenAction implements Action {

  @Override
  public String name() {
    return "full-screen";
  }

  @Override
  public void execute(Editor editor, String... args) {
    editor.toggleFullScreen();
  }

}
