package in.tombo.kashiki.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

public class TextureProvider {
  private int FONT_SIZE = 64;

  private LoadingCache<String, Texture> textureCache =
      CacheBuilder.newBuilder().maximumSize(10000).build(new CacheLoader<String, Texture>() {
        @Override
        public Texture load(String c) throws Exception {
          return AWTTextureIO.newTexture(gl.getGLProfile(), getTexture(c, FONT_SIZE), true);
        }
      });
  private LoadingCache<String, Double> sizeCache =
      CacheBuilder.newBuilder().maximumSize(10000).build(new CacheLoader<String, Double>() {

        @Override
        public Double load(String c) throws Exception {
          return rawGetWidth(c);
        }
      });

  private GL2 gl;

  private static TextureProvider INSTANCE = null;

  public static TextureProvider getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new TextureProvider();
    }
    return INSTANCE;
  }

  private Font font;

  private TextureProvider() {
    try {
      font = Font
          .createFont(Font.PLAIN,
              this.getClass().getClassLoader().getResourceAsStream("TakaoMincho.ttf"))
          .deriveFont((float) FONT_SIZE);
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Texture getTexture(GL2 gl, String c) {
    this.gl = gl;
    try {
      return textureCache.get(c);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  public double getWidth(String c) {
    try {
      return sizeCache.get(c);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private double rawGetWidth(String singleCharString) {
    BufferedImage image = new BufferedImage(FONT_SIZE, FONT_SIZE, BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g2d = (Graphics2D) image.getGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    FontMetrics fm = g2d.getFontMetrics(font);
    return (double) fm.charWidth(singleCharString.codePointAt(0)) / (double) FONT_SIZE;
  }

  private BufferedImage getTexture(String c, int size) {
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = (Graphics2D) image.getGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    FontRenderContext fontRenderContext = g2d.getFontRenderContext();
    GlyphVector gv = font.createGlyphVector(fontRenderContext, c.toCharArray());

    FontMetrics fm = g2d.getFontMetrics(font);

    g2d.drawGlyphVector(gv, (FONT_SIZE - fm.charWidth(c.codePointAt(0))) / 2, fm.getMaxAscent());
    return image;
  }
}
