package in.tombo.kashiki.view;

import org.junit.jupiter.api.Test;

public class SmoothValueTest {

  @Test
  public void testGains() {
    SmoothValue sv = new SmoothValue(0, MovingType.LINER);
    sv.setValue(15);
    int count = 0;
    while (!sv.isAnimated()) {
      if (count++ == 7) {
        sv.setValueWithoutSmooth(20);
      }
      System.out.println(sv.getValue());
    }
  }

}
