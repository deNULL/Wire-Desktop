package tl;

import java.nio.ByteBuffer;

public class MessageActionGeoChatCreate extends tl.TMessageAction {

  
  public MessageActionGeoChatCreate(ByteBuffer buffer) {
    title = new String(TL.readString(buffer));
    address = new String(TL.readString(buffer));
  }
  
  public MessageActionGeoChatCreate(String title, String address) {
    this.title = title;
    this.address = address;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6f038ebc);
    }
    TL.writeString(buffer, title.getBytes(), false);
    TL.writeString(buffer, address.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionGeoChatCreate: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(title.getBytes()) + TL.length(address.getBytes());
  }
  
  public String toString() {
    return "(messageActionGeoChatCreate title:" + "title" + " address:" + "address" + ")";
  }
}
