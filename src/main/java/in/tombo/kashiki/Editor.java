package in.tombo.kashiki;

import java.util.List;

import in.tombo.kashiki.buffer.Buffer;
import in.tombo.kashiki.view.Base;
import in.tombo.kashiki.view.SmoothValue;

public interface Editor {

  Buffer getCurrentBuffer();

  List<Base> getDrawables();

  void tearDown();

  void toggleFullScreen();

  void reflesh();

  SmoothValue getScale();

  void createNewBuffer();

}
