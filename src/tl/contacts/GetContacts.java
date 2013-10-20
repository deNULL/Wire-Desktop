package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class GetContacts extends tl.TLFunction {
  public String hash;
  
  public GetContacts(ByteBuffer buffer) {
    hash = new String(TL.readString(buffer));
  }
  
  public GetContacts(String hash) {
    this.hash = hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x22c6aa08);
    }
    TL.writeString(buffer, hash.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return TL.length(hash.getBytes());
  }
  
  public String toString() {
    return "(GetContacts hash:" + "hash" + ")";
  }
}
