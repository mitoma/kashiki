package in.tombo.kashiki.keybind;

import in.tombo.kashiki.Editor;

public interface KashikiKeyListener {

  public void keyPressed(Editor editor, SupportKey supportKey, int keyCode, long when);

  public void keyTyped(Editor editor, char typedString, long when);

}
