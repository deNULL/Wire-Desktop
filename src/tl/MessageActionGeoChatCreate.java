package tl;

import java.nio.ByteBuffer;

public class MessageActionGeoChatCreate extends tl.TMessageAction {

  
  public MessageActionGeoChatCreate(ByteBuffer buffer) throws Exception {
    title = new String(TL.readString(buffer), "UTF8");
    address = new String(TL.readString(buffer), "UTF8");
  }
  
  public MessageActionGeoChatCreate(String title, String address) {
    this.title = title;
    this.address = address;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x6f038ebc);
    }
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    TL.writeString(buffer, address.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageActionGeoChatCreate: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return TL.length(title.getBytes("UTF8")) + TL.length(address.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(messageActionGeoChatCreate title:" + "title" + " address:" + "address" + ")";
  }
}
