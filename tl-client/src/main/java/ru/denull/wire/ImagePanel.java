package ru.denull.wire;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class ImagePanel extends JPanel {
    private static final long serialVersionUID = -8982671400148488491L;
    public static final int SCALE_NONE = 0; // Don't do anything, keep preferred size and don't scale image
    public static final int SCALE_WIDTH = 1; // Keeps preferred width
    public static final int SCALE_HEIGHT = 2; // Keeps preferred height
    public static final int SCALE_WEIGHT = 3; // Keeps same area as preferred width*height
    public static final int SCALE_STRETCH = 4; // Keeps preferred width&height, fills image
    public static final int SCALE_INSIDE = 5; // Keeps preferred width&height and image ratio, scales to touch inside
    public static final int SCALE_OUTSIDE = 6; // Keeps preferred width&height and image ratio, scales to touch outside

    private Image image, scaled;
    private long id;
    private int scale = SCALE_OUTSIDE;
    private int scaledW, scaledH;

    public ImagePanel() {
        super();
        setOpaque(false);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Insets insets = getInsets();
        int x = insets.left, y = insets.top, width = getWidth() - insets.right - insets.left, height = getHeight() - insets.bottom - insets.top;
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        Area clip = new Area(g.getClip());
        clip.intersect(new Area(new RoundRectangle2D.Float(x, y, width, height, 3, 3)));
        g.setClip(clip);

        if (scaled == null || scaledW != width || scaledH != height) {
            try {
                scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                scaledW = width;
                scaledH = height;
            } catch (Exception e) {
            }
        }
        g.drawImage(scaled == null ? image : scaled, x, y, width, height, null); // SCALE_STRETCH
    }

    public void setImage(Image image) {
        this.image = image;
        scaled = null;
        repaint();
    }

    /**
     * Updates image only if it has the same id
     *
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
