package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class CreateChat extends tl.TLFunction {
  public tl.TInputUser[] users;
  public String title;
  
  public CreateChat(ByteBuffer buffer) {
    users = TL.readVector(buffer, true, new tl.TInputUser[0]);
    title = new String(TL.readString(buffer));
  }
  
  public CreateChat(tl.TInputUser[] users, String title) {
    this.users = users;
    this.title = title;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x419d9aee);
    }
    TL.writeVector(buffer, users, true, false);
    TL.writeString(buffer, title.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(users) + TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(CreateChat users:" + TL.toString(users) + " title:" + "title" + ")";
  }
}
