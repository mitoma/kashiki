package in.tombo.kashiki.view;

/**
 * http://ethanschoonover.com/solarized
 */
public enum SolarizedColor {
  BASE03(0, 43, 54), //
  BASE02(7, 54, 66), //
  BASE01(88, 110, 117), //
  BASE00(101, 123, 131), //
  BASE0(131, 148, 150), //
  BASE1(147, 161, 161), //
  BASE2(238, 232, 213), //
  BASE3(253, 246, 227), //
  YELLOW(181, 137, 0), //
  ORANGE(203, 75, 22), //
  RED(220, 50, 47), //
  MAGENTA(211, 54, 130), //
  VIOLET(108, 113, 196), //
  BLUE(38, 139, 210), //
  CYAN(43, 161, 152), //
  GREEN(133, 153, 0);

  private double r;
  private double g;
  private double b;

  private SolarizedColor(int r, int g, int b) {
    this.r = (double) r / 255.0;
    this.g = (double) g / 255.0;
    this.b = (double) b / 255.0;
  }

  public double getR() {
    return r;
  }

  public double getG() {
    return g;
  }

  public double getB() {
    return b;
  }

}
