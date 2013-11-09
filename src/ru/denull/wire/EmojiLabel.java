package ru.denull.wire;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.text.*;

import ru.denull.wire.Utils.EmojiIcon;

public class EmojiLabel extends JComponent {
  private int lineHeight = 18;
  private String text;
  private String author;
  private Color authorColor;
  private String intro;
  private Color introColor;
  public boolean center = true;
  
  public EmojiLabel(String text) {
    super();
    this.text = text;
  }
  
  public EmojiLabel(String text, String author, Color authorColor) {
    super();
    this.text = text;
    this.author = author;
    this.authorColor = authorColor;
  }
  
  public EmojiLabel(String text, String author, Color authorColor, String intro, Color introColor) {
    super();
    this.text = text;
    this.author = author;
    this.authorColor = authorColor;
    this.intro = intro;
    this.introColor = introColor;
  }
  
  public String getText() {
    return text;
  }
  
  public void setText(String text) {
    this.text = text;
    revalidate();
    repaint();
  }
  
  public String getAuthor() {
    return author;
  }
  
  public void setAuthor(String author) {
    this.author = author;
    revalidate();
    repaint();
  }
  
  public Color getAuthorColor() {
    return authorColor;
  }
  
  public void setAuthorColor(Color authorColor) {
    this.authorColor = authorColor;
    revalidate();
    repaint();
  }
  
  public String getIntro() {
    return intro;
  }
  
  public void setIntro(String intro) {
    this.intro = intro;
    revalidate();
    repaint();
  }
  
  public Color getIntroColor() {
    return introColor;
  }
  
  public void setIntroColor(Color introColor) {
    this.introColor = introColor;
    revalidate();
    repaint();
  }
  
  public int getLineHeight() {
    return lineHeight;
  }
  
  public void setLineHeight(int lineHeight) {
    this.lineHeight = lineHeight;
  }
  
  protected void paintComponent(Graphics g) {
    if (isOpaque()) {
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());
    }
    int offs = center ? (getHeight() - getHeightForWidth(getWidth())) / 2 : 0;
    Point pos = new Point(0, offs + g.getFontMetrics().getAscent() + 1);
    int maxWidth = getWidth();
    if (author != null) {
      g.setColor(authorColor);
      pos = drawText(g, author, pos.x, pos.y, maxWidth, false);
    }
    if (intro != null) {
      g.setColor(introColor);
      pos = drawText(g, intro, pos.x, pos.y, maxWidth, false);
    }
    if (text != null) {
      g.setColor(getForeground());
      pos = drawText(g, text, pos.x, pos.y, maxWidth, false);
    }
  }
  
  private Point drawText(Graphics g, String text, int x, int y, int maxWidth, boolean fake) {
    if (g == null || text == null) {
      return new Point(x, y);
    }
    
    y++;
    
    int max = 0;
    
    Font font = getFont();
    g.setFont(font);
    
    FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();
    
    text = text + "\n";
    char[] ch = text.toCharArray();
    
    int len = ch.length;
    int lastWord = 0;
    for (int i = 0; i < len; i++) {
      long code = ch[i];
      
      Icon icon = null;
      int skip = 0;
      if (i < len - 1 && (code == 0xD83D || code == 0xD83C || code == 35 || (code >= 48 && code <= 57))) { // 4-byte emoji
        code = (code << 16) | text.charAt(i + 1);
        if (i < len - 3 &&
            code == 0xD83CDDEF || code == 0xD83CDDF0 || code == 0xD83CDDE9 || code == 0xD83CDDE8 ||
            code == 0xD83CDDFA || code == 0xD83CDDEB || code == 0xD83CDDEA || code == 0xD83CDDEE ||
            code == 0xD83CDDF7 || code == 0xD83CDDEC) { // 8-byte emoji
          code = /*(code << 32) |*/ (text.charAt(i + 2) << 16) | text.charAt(i + 3);
          
          icon = Utils.getEmojiIcon(code);
          skip = 3;
        } else {
          icon = Utils.getEmojiIcon(code);
          skip = 1;
        }
      } else
      if (code == 0x00A9 || code == 0x00AE || code > 0x2100) { // 2-byte emoji
        icon = Utils.getEmojiIcon(code);
        skip = 1;
      }
      
      boolean nonLetter = Character.isLetterOrDigit(ch[i]);
      if (icon != null || !nonLetter) { // end of word
        boolean whitespace = (icon == null) && Character.isWhitespace(ch[i]);
        boolean newLine = (icon == null) && (ch[i] == '\n');
        
        int width = (int) font.getStringBounds(ch, lastWord, i + (icon == null ? 1 : 0), frc).getWidth();
        boolean wrap = false;
        if (i > lastWord && x > 0) {
          wrap = (x + width > maxWidth);
          if (!wrap && !whitespace && (icon == null)) {
            width = (int) font.getStringBounds(ch, lastWord, i + 1, frc).getWidth();
            if (x + width > maxWidth) {
              wrap = true;
            }
          }
        }
        
        if (wrap) {
          x = 0;
          y += lineHeight;
        }
        
        if (!fake && (icon == null || i > lastWord)) {
          g.drawChars(ch, lastWord, i - lastWord + (icon == null ? 1 : 0), x, y);
        }
        
        x += width;
        lastWord = i + skip + 1;
        
        max = Math.max(max, x);

        if (icon != null) {
          if (x + EmojiIcon.ICON_SIZE > maxWidth) {
            x = 0;
            y += lineHeight;
          }
          
          if (!fake) {
            icon.paintIcon(this, g, x, y - 15);
          }
          x += EmojiIcon.ICON_SIZE;
        }
        
        if (newLine) {
          x = 0;
          y += lineHeight;
        }
        
        i += skip;
      }
    }
    
    return new Point(maxWidth == Integer.MAX_VALUE ? max : x, y);
  }
  
  public int getHeightForWidth(int width) {
    Graphics g = getGraphics();
    return drawText(g, author, 0, 0, width, true).y + drawText(g, intro, 0, 0, width, true).y + drawText(g, text, 0, 0, width, true).y;
  }
  
  public int getMaximalWidth() {
    Graphics g = getGraphics();
    return Math.max(drawText(g, author, 0, 0, Integer.MAX_VALUE, true).x, Math.max(drawText(g, intro, 0, 0, Integer.MAX_VALUE, true).x,  drawText(g, text, 0, 0, Integer.MAX_VALUE, true).x));
  }

  public Dimension getMinimumSize() {
    return new Dimension(center ? 4 : Integer.MAX_VALUE, center ? 28 : 34);
  }
  
  public Dimension getPreferredSize() {
    return new Dimension(getMaximalWidth(), 34/* Math.max(28, getHeightForWidth(getWidth()))*/);
  }
  
}
