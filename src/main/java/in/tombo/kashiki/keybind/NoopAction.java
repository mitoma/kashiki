package in.tombo.kashiki.keybind;

import in.tombo.kashiki.Editor;

public class NoopAction implements Action {

  @Override
  public String name() {
    return "noop";
  }

  @Override
  public void execute(Editor editor, String... args) {
    // noop
  }

}
