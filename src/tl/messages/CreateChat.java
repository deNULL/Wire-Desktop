package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class CreateChat extends tl.TLFunction {
  public tl.TInputUser[] users;
  public String title;
  
  public CreateChat(ByteBuffer buffer) throws Exception {
    users = TL.readVector(buffer, true, new tl.TInputUser[0]);
    title = new String(TL.readString(buffer), "UTF8");
  }
  
  public CreateChat(tl.TInputUser[] users, String title) {
    this.users = users;
    this.title = title;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x419d9aee);
    }
    TL.writeVector(buffer, users, true, true);
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at CreateChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(users, true) + TL.length(title.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(messages.createChat users:" + TL.toString(users) + " title:" + "title" + ")";
  }
}
