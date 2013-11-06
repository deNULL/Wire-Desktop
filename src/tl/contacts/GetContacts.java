package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetContacts extends tl.TLFunction {
  public String hash;
  
  public GetContacts(ByteBuffer buffer) {
    try {  hash = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public GetContacts(String hash) {
    this.hash = hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x22c6aa08);
    }
    try { TL.writeString(buffer, hash.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at GetContacts: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return TL.length(hash.getBytes());
  }
  
  public String toString() {
    return "(contacts.getContacts hash:" + "hash" + ")";
  }
}
