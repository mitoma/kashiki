package in.tombo.kashiki.keybind;


public interface KashikiKeyListener {

  public boolean keyPressed(SupportKey supportKey, int keyCode, long when);

  public boolean keyTyped(char typedString, long when);

}
