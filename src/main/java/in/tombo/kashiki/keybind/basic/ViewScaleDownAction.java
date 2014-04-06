package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;
import in.tombo.kashiki.view.SmoothValue;

public class ViewScaleDownAction implements Action {

  @Override
  public String name() {
    return "view-scale-down";
  }

  @Override
  public void execute(String... args) {
    SmoothValue scale = Editor.getInstance().getScale();
    scale.setValue(scale.getLastValue() / 1.25);
  }

}
