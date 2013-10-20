package ru.denull.wire;

import java.awt.*;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
  private static final long serialVersionUID = -8982671400148488491L;
  public static final int SCALE_NONE = 0; // Don't do anything, keep preferred size and don't scale image
  public static final int SCALE_WIDTH = 1; // Keeps preferred width
  public static final int SCALE_HEIGHT = 2; // Keeps preferred height
  public static final int SCALE_WEIGHT = 3; // Keeps same area as preferred width*height
  public static final int SCALE_STRETCH = 4; // Keeps preferred width&height, fills image
  public static final int SCALE_INSIDE = 5; // Keeps preferred width&height and image ratio, scales to touch inside
  public static final int SCALE_OUTSIDE = 6; // Keeps preferred width&height and image ratio, scales to touch outside
  
  private Image image;
  private long id;
  private int scale = SCALE_OUTSIDE;
  
  public void paint(Graphics g) {
    int x = 0, y = 0, width = getWidth(), height = getHeight();
    g.drawImage(image, x, y, width, height, null); // SCALE_STRETCH
  }
  
  public void setImage(Image image) {
    this.image = image;
    repaint();
  }
  
  /**
   * Updates image only if it has the same id
   * @param image
   * @param id
   */
  public void setImage(Image image, long id) {
    if (id == this.id) {
      setImage(image);
    }
  }
  
  public Image getImage() {
    return image;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public long getId() {
    return id;
  }
  
  public void setScale(int scale) {
    this.scale = scale;
    repaint();
  }
  
  public int getScale() {
    return scale;
  }
}
