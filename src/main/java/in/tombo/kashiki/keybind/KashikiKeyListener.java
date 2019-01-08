package in.tombo.kashiki.keybind;

import in.tombo.kashiki.Editor;

public interface KashikiKeyListener {

  public boolean keyPressed(Editor editor, SupportKey supportKey, int keyCode, long when);

  public boolean keyTyped(Editor editor, char typedString, long when);

}
