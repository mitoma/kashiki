package in.tombo.kashiki.view;


import org.junit.Test;

public class MovingTypeTest {

  @Test
  public void testGains() {
    // for (double d : MovingType.LINER.gains(10)) {
    // System.out.println(d);
    // }
    // for (double d : MovingType.SMOOTH.gains(10)) {
    // System.out.println(d);
    // }
    double inc = 0.0;
    for (double d : MovingType.BOUND.gains(20)) {
      inc += d;
      System.out.println(d);
    }
    System.out.println(inc);
  }

}
