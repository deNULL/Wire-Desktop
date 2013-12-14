package ru.denull.wire;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MessageLayout implements LayoutManager2 {
    public static final String PHOTO = "photo";
    public static final String BODY = "body";
    public static final String DATE = "date";
    public static final String ACTIONS = "actions";

    public HashMap<String, Component> comps = new HashMap<String, Component>();
    public boolean leftSide;
    public String html;
    public EmojiLabel label;
    public int vmargin, hmargin;
    public double bodyWeight = 0.7;
    public int photoSideMargin = 4;
    public int photoBottomMargin = 4;
    public int photoMargin = 4;
    public int messageBottomMargin = 3;
    public int dateMargin = 4;
    public int dateBottomMargin = 6;
    public int actionsMargin = 4;

    public JList list = null;

    public MessageLayout(JList list, boolean leftSide) {
        this.list = list;
        this.leftSide = leftSide;
    }

    public void setHTMLBody(EmojiLabel label, int vmargin, int hmargin) {
        //this.html = html;
        this.label = label;
        this.vmargin = vmargin;
        this.hmargin = hmargin;
    }

    public void addLayoutComponent(String tag, Component comp) {
        comps.put(tag, comp);
    }

    public void addLayoutComponent(Component comp, Object tag) {
        comps.put((String) tag, comp);
    }

    public void removeLayoutComponent(Component comp) {
        comps.values().remove(comp);
    }

    public void layoutContainer(final Container container) {
        Component photo = comps.get(PHOTO);
        Component body = comps.get(BODY);
        Component date = comps.get(DATE);
        Component actions = comps.get(ACTIONS);

        Dimension size = container.getSize();
        Insets insets = container.getInsets();

        int pos = (leftSide ? insets.left : insets.right) + photoSideMargin;

        if (photo != null) {
            photo.setSize(photo.getPreferredSize());
            photo.setLocation(leftSide ? pos : (size.width - pos - photo.getWidth()), size.height - insets.bottom - photo.getHeight() - photoBottomMargin);

            pos += photo.getWidth() + photoMargin;
        }

        if (body != null) {
            int width;
            if (label != null) {
                Dimension min = body.getMinimumSize();
                width = Math.max((int) (size.width * bodyWeight), min.width);
                int height = Math.max(getPreferredSize(html, true, width) + vmargin, min.height);
                width = Math.max(Math.min(width + hmargin, body.getPreferredSize().width), min.width);
                body.setBounds(leftSide ? pos : (size.width - pos - width), insets.top, width, height);
                //System.out.println(width + "X" + height);
            } else {
                width = Math.min((int) (size.width * bodyWeight), body.getPreferredSize().width);
                body.setBounds(leftSide ? pos : (size.width - pos - width), insets.top, width, width * body.getPreferredSize().height / body.getPreferredSize().width);
            }

            pos += width;
        }

        if (date != null) {
            date.setSize(date.getPreferredSize());
            date.setLocation(leftSide ? (pos + dateMargin) : (size.width - pos - dateMargin - date.getWidth()), size.height - insets.bottom - date.getHeight() - dateBottomMargin);
        }

        if (actions != null) {
            actions.setSize(actions.getPreferredSize());
            actions.setLocation(leftSide ? (pos + actionsMargin) : (size.width - pos - actionsMargin - actions.getWidth()), insets.top + (size.height - insets.top - insets.bottom - actions.getHeight()) / 2);
        }

        //container.setSize(size.width, insets.left + insets.right + getMaximumHeight(size.width));
    }

    public Dimension minimumLayoutSize(Container container) {
        return new Dimension(list.getWidth(), getMinimumHeight());
    }

    public Dimension preferredLayoutSize(Container container) {
        return new Dimension(list.getWidth(), getMaximumHeight(list.getWidth()));
    }

    public int getMaximumHeight(int width) {
        Component photo = comps.get(PHOTO);
        Component body = comps.get(BODY);
        Component date = comps.get(DATE);
        Component actions = comps.get(ACTIONS);

        int height = 0;

        if (photo != null) {
            height = Math.max(height, photo.getPreferredSize().height + photoBottomMargin);
        }

        if (body != null) {
            if (label != null) {
                Dimension min = body.getMinimumSize();
                height = Math.max(height, Math.max(getPreferredSize(html, true, Math.max((int) (width * bodyWeight), min.width)) + vmargin, min.height) + messageBottomMargin);
                //System.out.println("width: " + width + ", h" +  Math.max(getPreferredSize(html, true, Math.max((int) (width * bodyWeight), min.width)).height + vmargin, min.height));
            } else {
                int w = Math.min((int) (width * bodyWeight), body.getPreferredSize().width);
                height = Math.max(height, w * body.getPreferredSize().height / body.getPreferredSize().width + messageBottomMargin);
            }
        }

        if (date != null || actions != null) {
            height = Math.max(height, (date != null ? date.getPreferredSize().height + dateBottomMargin : 0) + (actions != null ? actions.getPreferredSize().height : 0));
        }


        return height;
    }

    public int getMinimumHeight() {
        Component photo = comps.get(PHOTO);
        Component body = comps.get(BODY);
        Component date = comps.get(DATE);
        Component actions = comps.get(ACTIONS);

        int height = 0;

        if (photo != null) {
            height = Math.max(height, photo.getPreferredSize().height);
        }

        if (body != null) {
            if (label != null) {
                Dimension min = body.getMinimumSize();
                height = Math.max(height, min.height);
            } else {
                height = Math.max(height, 10);
            }
        }

        if (date != null || actions != null) {
            height = Math.max(height, (date != null ? date.getPreferredSize().height : 0) + (actions != null ? actions.getPreferredSize().height : 0));
        }

        return height;
    }

    //private static final JLabel resizer = new JLabel();

    /**
     * Returns the preferred size to set a component at in order to render an html string. You can
     * specify the size of one dimension.
     */
    public int getPreferredSize(String html, boolean width, int prefSize) {
/*
    resizer.setText(html);
    //Utils.fixEmoji(resizer);

    View view = (View) resizer.getClientProperty("html");

    view.setSize(width ? prefSize : 0, width ? 0 : prefSize);

    float w = view.getPreferredSpan(View.X_AXIS);
    float h = view.getPreferredSpan(View.Y_AXIS);

    return new java.awt.Dimension((int) Math.ceil(w), (int) Math.ceil(h));
 */
        return width ? label.getHeightForWidth(prefSize) : label.getMaximalWidth();
    }


    @Override
    public float getLayoutAlignmentX(Container container) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container container) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container container) {
    }

    public Dimension maximumLayoutSize(Container container) {
        return new Dimension(list.getWidth(), getMaximumHeight(0));
    }

}
