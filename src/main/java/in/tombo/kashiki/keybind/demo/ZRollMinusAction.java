package in.tombo.kashiki.keybind.demo;

import java.util.List;

import in.tombo.kashiki.Editor;
import in.tombo.kashiki.keybind.Action;
import in.tombo.kashiki.view.Base;

public class ZRollMinusAction implements Action {

  @Override
  public String name() {
    return "z-roll-minus";
  }

  @Override
  public void execute(String... args) {
    List<Base> drawables = Editor.getInstance().getDrawables();
    for (Base base : drawables) {
      base.getAngle().getZ().addValue(-10);
    }
  }

}
