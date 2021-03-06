package in.tombo.kashiki;

import java.io.IOException;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.FPSAnimator;

public class KashikiFrame implements GLEventListener {

  private final Editor editor;
  private FPSAnimator animator;

  int canvasWidth = 0;
  int canvasHeight = 0;
  double yscale;

  public KashikiFrame(Editor editor) {
    this.editor = editor;
  }

  @Override
  public void display(GLAutoDrawable drawable) {
    try {
      render(drawable);
    } catch (GLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void dispose(GLAutoDrawable drawable) {
    animator.stop();
    drawable.removeGLEventListener(this);
    drawable.destroy();
  }

  @Override
  public void init(GLAutoDrawable drawable) {

  }

  @Override
  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    this.canvasWidth = w;
    this.canvasHeight = h;
    this.yscale = ((double) h / (double) w);
  }

  private void render(GLAutoDrawable drawable) throws GLException, IOException {
    GL2 gl = drawable.getGL().getGL2();

    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    gl.glEnable(GL2.GL_BLEND);
    gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA);
    gl.glEnable(GL2.GL_MULTISAMPLE);
    gl.glEnable(GL2.GL_SAMPLE_ALPHA_TO_COVERAGE);

    gl.glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
    gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
    gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

    gl.glMatrixMode(GL2.GL_MODELVIEW_MATRIX);
    gl.glPushMatrix();
    double s = editor.getScale().getValue();
    gl.glScaled(s, s / yscale, 1.0);
    gl.glTranslated(0, 0, -10);

    editor.getDrawables().forEach(base -> base.draw(gl));

    gl.glPopMatrix();

    {
      gl.glMatrixMode(GL2.GL_PROJECTION_MATRIX);
      gl.glLoadIdentity();
      gl.glFrustum(-1, 1, -1, 1, 1, 500);
    }

    gl.glFlush();

  }

}
