package tl;

import java.nio.ByteBuffer;

public class MessageActionGeoChatCreate extends tl.TMessageAction {

  
  public MessageActionGeoChatCreate(ByteBuffer buffer) {
    try {  title = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  address = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
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
    try { TL.writeString(buffer, title.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, address.getBytes("UTF8"), false); } catch (Exception e) { };
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
