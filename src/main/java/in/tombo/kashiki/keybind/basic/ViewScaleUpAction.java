package in.tombo.kashiki.keybind.basic;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;
import in.tombo.kashiki.view.SmoothValue;

public class ViewScaleUpAction implements Action {

  @Override
  public String name() {
    return "view-scale-up";
  }

  @Override
  public void execute(Editor editor, String... args) {
    SmoothValue scale = editor.getScale();
    scale.setValue(scale.getLastValue() * 1.25);
  }

}
