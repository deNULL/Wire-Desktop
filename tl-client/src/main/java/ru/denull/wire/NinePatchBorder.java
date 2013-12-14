package ru.denull.wire;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class NinePatchBorder extends AbstractBorder {
    private static final long serialVersionUID = 4194792249061847077L;
    private Image bg;
    private Insets fixed;
    private Insets padding;
    private boolean ninePatch;

    public NinePatchBorder(Image bg) {
        super();
        this.bg = bg;

        ninePatch = true;
        // TODO: read fixed & padding from image
    }

    public NinePatchBorder(Image bg, Insets fixed) {
        this(bg, fixed, fixed);
    }

    public NinePatchBorder(Image bg, Insets fixed, int padding) {
        this(bg, fixed, new Insets(padding, padding, padding, padding));
    }

    public NinePatchBorder(Image bg, Insets fixed, int vpadding, int hpadding) {
        this(bg, fixed, new Insets(vpadding, hpadding, vpadding, hpadding));
    }

    public NinePatchBorder(Image bg, Insets fixed, int ptop, int pleft, int pbottom, int pright) {
        this(bg, fixed, new Insets(ptop, pleft, pbottom, pright));
    }

    public NinePatchBorder(Image bg, int top, int left, int bottom, int right) {
        this(bg, new Insets(top, left, bottom, right));
    }

    public NinePatchBorder(Image bg, int top, int left, int bottom, int right, int padding) {
        this(bg, new Insets(top, left, bottom, right), new Insets(padding, padding, padding, padding));
    }

    public NinePatchBorder(Image bg, int top, int left, int bottom, int right, int vpadding, int hpadding) {
        this(bg, new Insets(top, left, bottom, right), new Insets(vpadding, hpadding, vpadding, hpadding));
    }

    public NinePatchBorder(Image bg, int top, int left, int bottom, int right, int ptop, int pleft, int pbottom, int pright) {
        this(bg, new Insets(top, left, bottom, right), new Insets(ptop, pleft, pbottom, pright));
    }

    public NinePatchBorder(Image bg, Insets fixed, Insets padding) {
        super();
        this.bg = bg;
        this.fixed = fixed;
        this.padding = padding;

        ninePatch = false;
    }

    public Insets getBorderInsets(Component c) {
        return padding;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if (bg != null) {
            int ofs = ninePatch ? 1 : 0;
            int w = bg.getWidth(null);
            int h = bg.getHeight(null);
            g.drawImage(bg, x, y, x + fixed.left, y + fixed.top, ofs, ofs, ofs + fixed.left, ofs + fixed.top, null); // Top left
            g.drawImage(bg, x + width - fixed.right, y, x + width, y + fixed.top, w - ofs - fixed.right, ofs, w - ofs, ofs + fixed.top, null); // Top right
            g.drawImage(bg, x, y + height - fixed.bottom, x + fixed.left, y + height, ofs, h - ofs - fixed.bottom, ofs + fixed.left, h - ofs, null); // Bottom left
            g.drawImage(bg, x + width - fixed.right, y + height - fixed.bottom, x + width, y + height, w - ofs - fixed.right, h - ofs - fixed.bottom, w - ofs, h - ofs, null); // Bottom right

            g.drawImage(bg, x + fixed.left, y, x + width - fixed.right, y + fixed.top, ofs + fixed.left, ofs, w - ofs - fixed.right, ofs + fixed.top, null); // Top
            g.drawImage(bg, x + fixed.left, y + height - fixed.bottom, x + width - fixed.right, y + height, ofs + fixed.left, h - ofs - fixed.bottom, w - ofs - fixed.right, h - ofs, null); // Bottom

            g.drawImage(bg, x, y + fixed.top, x + fixed.left, y + height - fixed.bottom, ofs, ofs + fixed.top, ofs + fixed.left, h - ofs - fixed.bottom, null); // Left
            g.drawImage(bg, x + width - fixed.right, y + fixed.top, x + width, y + height - fixed.bottom, w - ofs - fixed.right, ofs + fixed.top, w - ofs, h - ofs - fixed.bottom, null); // Right

            g.drawImage(bg, x + fixed.left, y + fixed.top, x + width - fixed.right, y + height - fixed.bottom, ofs + fixed.left, ofs + fixed.top, w - ofs - fixed.right, h - ofs - fixed.bottom, null); // Center
        }
    }


}
