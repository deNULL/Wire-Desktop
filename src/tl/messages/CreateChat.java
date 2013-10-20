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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x419d9aee);
    }
    TL.writeVector(buffer, users, true, true);
    TL.writeString(buffer, title.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at CreateChat: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(users) + TL.length(title.getBytes());
  }
  
  public String toString() {
    return "(messages.createChat users:" + TL.toString(users) + " title:" + "title" + ")";
  }
}
