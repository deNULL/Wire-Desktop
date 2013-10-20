package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class EditChatTitle extends tl.TLFunction {
  public int chat_id;
  public String title;
  
  public EditChatTitle(ByteBuffer buffer) {
    chat_id = buffer.getInt();
    title = new String(TL.readString(buffer));
  }
  
  public EditChatTitle(int chat_id, String title) {
    this.chat_id = chat_id;
    this.title = title;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xb4bc68b5);
    }
    buffer.putInt(chat_id);
    TL.writeString(buffer, title.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(EditChatTitle chat_id:" + chat_id + " title:" + "title" + ")";
  }
}
