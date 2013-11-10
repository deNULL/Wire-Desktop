package tl;

import java.nio.ByteBuffer;

public class ChatForbidden extends tl.TChat {

  
  public ChatForbidden(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    title = new String(TL.readString(buffer), "UTF8");
    date = buffer.getInt();
  }
  
  public ChatForbidden(int id, String title, int date) {
    this.id = id;
    this.title = title;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xfb0ccc41);
    }
    buffer.putInt(id);
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    buffer.putInt(date);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatForbidden: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(title.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(chatForbidden id:" + id + " title:" + "title" + " date:" + date + ")";
  }
}
