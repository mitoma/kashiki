package in.tombo.kashiki.keybind;

public interface KashikiKeyListener {

  public void keyPressed(SupportKey supportKey, int keyCode, long when);

  public void keyTyped(char typedString, long when);

}
