package in.tombo.kashiki.keybind.demo;

import java.util.List;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;
import in.tombo.kashiki.view.Base;

public class XRollMinusAction implements Action {

  @Override
  public String name() {
    return "x-roll-minus";
  }

  @Override
  public void execute(String... args) {
    List<Base> drawables = Editor.getInstance().getDrawables();
    for (Base base : drawables) {
      base.getAngle().getX().addValue(-10);
    }
  }

}
