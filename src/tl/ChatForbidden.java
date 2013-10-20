package tl;

import java.nio.ByteBuffer;

public class ChatForbidden extends tl.TChat {
  
  public ChatForbidden(ByteBuffer buffer) {
    id = buffer.getInt();
    title = new String(TL.readString(buffer));
    date = buffer.getInt();
  }
  
  public ChatForbidden(int id, String title, int date) {
    this.id = id;
    this.title = title;
    this.date = date;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xfb0ccc41);
    }
    buffer.putInt(id);
    TL.writeString(buffer, title.getBytes(), false);
    buffer.putInt(date);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(ChatForbidden id:" + id + " title:" + "title" + " date:" + date + ")";
  }
}
