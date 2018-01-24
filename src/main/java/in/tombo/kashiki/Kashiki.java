package in.tombo.kashiki;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Kashiki {

  public static void main(String[] args) throws IOException {
    GLFWErrorCallback.createPrint(System.err).set();
    if(!GLFW.glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }
    
    Frame frame = new Frame("Kashiki");
    frame.setIconImage(ImageIO.read(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("in/tombo/kashiki/icon.png")));

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    frame.setSize(800, 600);
    frame.setLocation(100, 100);
    frame.add(new KashikiGLCanvas());

    frame.setVisible(true);
    Editor.getInstance().setFrame(frame);
    
  }
}
