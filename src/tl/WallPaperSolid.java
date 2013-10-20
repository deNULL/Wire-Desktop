package tl;

import java.nio.ByteBuffer;

public class WallPaperSolid extends tl.TWallPaper {

  
  public WallPaperSolid(ByteBuffer buffer) {
    id = buffer.getInt();
    title = new String(TL.readString(buffer));
    bg_color = buffer.getInt();
    color = buffer.getInt();
  }
  
  public WallPaperSolid(int id, String title, int bg_color, int color) {
    this.id = id;
    this.title = title;
    this.bg_color = bg_color;
    this.color = color;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x63117f24);
    }
    buffer.putInt(id);
    TL.writeString(buffer, title.getBytes(), false);
    buffer.putInt(bg_color);
    buffer.putInt(color);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at WallPaperSolid: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(wallPaperSolid id:" + id + " title:" + "title" + " bg_color:" + bg_color + " color:" + color + ")";
  }
}
