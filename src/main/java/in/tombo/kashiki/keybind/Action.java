package in.tombo.kashiki.keybind;

import in.tombo.kashiki.Editor;

public interface Action {
  public String name();

  public void execute(Editor editor, String... args);
}
