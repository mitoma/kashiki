package in.tombo.kashiki.keybind.demo;

import java.util.List;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;
import in.tombo.kashiki.view.Base;

public class YRollPlusAction implements Action {

  @Override
  public String name() {
    return "y-roll-plus";
  }

  @Override
  public void execute(Editor editor, String... args) {
    List<Base> drawables = editor.getDrawables();
    for (Base base : drawables) {
      base.getAngle().getY().addValue(10);
    }
  }

}
