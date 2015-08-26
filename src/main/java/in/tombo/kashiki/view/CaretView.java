package in.tombo.kashiki.view;

import in.tombo.kashiki.buffer.Caret;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

public class CaretView extends Base {
  private TextureProvider textureProvider;
  private SmoothValue col = getPosition().getX();
  private SmoothValue row = getPosition().getY();
  private Caret caret;

  public CaretView(Caret caret) {
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
    gl.glColor4d(0.4, 0.4, 1, 0.5);

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
