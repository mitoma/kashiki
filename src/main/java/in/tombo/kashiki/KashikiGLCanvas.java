package in.tombo.kashiki;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

public class KashikiGLCanvas extends GLCanvas {

  private static final long serialVersionUID = 1L;

  public KashikiGLCanvas() {
    super(new GLCapabilities(GLProfile.getDefault()));
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    addKeyListener(new DocumentKeyListener());
    addKeyListener(new LoggingKeyListener());
    addGLEventListener(new KashikiFrame());
    new FPSAnimator(this, 30).start();
  }
}
