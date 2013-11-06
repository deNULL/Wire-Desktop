package tl;

import java.nio.ByteBuffer;

public class ChatForbidden extends tl.TChat {

  
  public ChatForbidden(ByteBuffer buffer) {
    id = buffer.getInt();
    try {  title = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    date = buffer.getInt();
  }
  
  public ChatForbidden(int id, String title, int date) {
    this.id = id;
    this.title = title;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xfb0ccc41);
    }
    buffer.putInt(id);
    try { TL.writeString(buffer, title.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatForbidden: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(chatForbidden id:" + id + " title:" + "title" + " date:" + date + ")";
  }
}