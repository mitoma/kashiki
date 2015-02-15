package in.tombo.kashiki.view;

import in.tombo.kashiki.buffer.Caret;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

public class CaretView extends Base {
  private TextureProvider textureProvider;
  private SmoothValue col = getPosition().getX();
  private SmoothValue row = getPosition().getY();
  private Caret caret;

  public CaretView(Caret caret) {
    this.getColor().update(SolarizedColor.BLUE);
    this.getColor().getAlpha().setValue(0.75);

    this.caret = caret;
    this.textureProvider = TextureProvider.getInstance();
  }

  public void updatePosition(double x, double y) {
    this.row.setValue(-y);
    this.col.setValue(x);
  }

  public double getWidth() {
    return textureProvider.getWidth("◆");
  }

  public Caret getCaret() {
    return caret;
  }

  @Override
  public void innerDraw(GL2 gl) {
    Texture texture = textureProvider.getTexture(gl, "◆");
    texture.enable(gl);
    texture.bind(gl);
    Color color = getColor();
    gl.glColor4d(color.getRed().getValue(), color.getGreen().getValue(),
        color.getBlue().getValue(), color.getAlpha().getValue());

    gl.glRotated((System.currentTimeMillis() / 5) % 360, 0, 1, 0);

    gl.glBegin(GL2.GL_POLYGON);
    gl.glTexCoord2f(0, 1);
    gl.glVertex2d(-0.5, -0.5);
    gl.glTexCoord2f(0, 0);
    gl.glVertex2d(-0.5, 0.5);
    gl.glTexCoord2f(1, 0);
    gl.glVertex2d(0.5, 0.5);
    gl.glTexCoord2f(1, 1);
    gl.glVertex2d(0.5, -0.5);
    gl.glEnd();
  }
}
