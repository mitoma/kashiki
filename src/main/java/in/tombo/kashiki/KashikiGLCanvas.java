package in.tombo.kashiki;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
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
