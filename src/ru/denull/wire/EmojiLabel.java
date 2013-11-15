package ru.denull.wire;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  public ArrayList<Link> links = new ArrayList<Link>();
  
  // TODO: write own regexp
  //public static final Pattern linkRegexp = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www\\.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)");
  //public static final Pattern linkRegexp = Pattern.compile("(https:[/][/]|http:[/][/]|www.)[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-._?,'/\\\\+&%$#\\=~])*$");
  
  public class Link {
    int st, en;
    Rectangle bounds = null;
    public Link(int st, int en) {
      this.st = st;
      this.en = en;
    }
  }
  
  public EmojiLabel(String text) {
    super();
    this.text = text;
  }
  
  public EmojiLabel(String text, String author, Color authorColor) {
    super();
    this.text = text;
    this.author = author;
    this.authorColor = authorColor;
    parseLinks();
  }
  
  public EmojiLabel(String text, String author, Color authorColor, String intro, Color introColor) {
    super();
    this.text = text;
    this.author = author;
    this.authorColor = authorColor;
    this.intro = intro;
    this.introColor = introColor;
    parseLinks();
  }
  
  public String getText() {
    return text;
  }
  
  public void setText(String text) {
    this.text = text;
    parseLinks();
    revalidate();
    repaint();
  }
  
  private void parseLinks() {
    /*links.clear();

    text = text.trim() + "\n";
    Matcher m = linkRegexp.matcher(text);
    while (m.find()) {
      links.add(new Link(m.start(), m.end() - 1));
    }*/
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
    
    y += 1;
    
    int max = 0;
    
    Font font = getFont();
    Color fcolor = g.getColor();
    Color lcolor = Color.decode("0x006fc8");
    Map<TextAttribute, Object> map =
        new Hashtable<TextAttribute, Object>();
    map.put(TextAttribute.UNDERLINE,
        TextAttribute.UNDERLINE_ON);
    Font lfont = font.deriveFont(map);
    
    FontRenderContext frc = ((Graphics2D) g).getFontRenderContext();
    
    ((Graphics2D) g).setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    
    ((Graphics2D) g).setRenderingHint(
        RenderingHints.KEY_TEXT_LCD_CONTRAST,
        100);

    text = text.trim() + "\n";
    char[] ch = text.toCharArray();
    
    int len = ch.length;
    int lastWord = 0;
    int linkIndex = 0;
    boolean wasLink = false;
    for (int i = 0; i < len; i++) {
      long code = ch[i];
      
      while (linkIndex < links.size() && links.get(linkIndex).en < i) { // Move pointer forward
        linkIndex++;
      }
      boolean withinLink = (linkIndex < links.size() && links.get(linkIndex).st <= i && links.get(linkIndex).en >= i);
      if (i == 0) wasLink = withinLink;
      //boolean nextLink = (linkIndex < links.size() && links.get(linkIndex).st == i + 1);
      
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
        //System.out.println("next:" + (int)ch[i+1]);
        skip = (i + 1 < len && ch[i + 1] == 65039) ? 1 : 0;
      }
      
      boolean nonLetter = Character.isLetterOrDigit(ch[i]) || withinLink;
      if (icon != null || !nonLetter || wasLink != withinLink) { // end of word
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
          if (wasLink) {
            g.setColor(lcolor);
            //g.setFont(lfont);
            g.drawLine(x, y + 1, x + (int) font.getStringBounds(ch, lastWord, i, frc).getWidth(), y + 1);
          } else {
            g.setColor(fcolor);
            //g.setFont(font);
          }
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
      wasLink = withinLink;
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
    return new Dimension(center ? 4 : Integer.MAX_VALUE, center ? 28 : 38);
  }
  
  public Dimension getPreferredSize() {
    return new Dimension(getMaximalWidth(), 38/* Math.max(28, getHeightForWidth(getWidth()))*/);
  }
  
}
