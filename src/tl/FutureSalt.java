package tl;

import java.nio.ByteBuffer;

public class FutureSalt extends tl.TFutureSalt {

  
  public FutureSalt(ByteBuffer buffer) throws Exception {
    valid_since = buffer.getInt();
    valid_until = buffer.getInt();
    salt = buffer.getLong();
  }
  
  public FutureSalt(int valid_since, int valid_until, long salt) {
    this.valid_since = valid_since;
    this.valid_until = valid_until;
    this.salt = salt;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x0949d9dc);
    }
    buffer.putInt(valid_since);
    buffer.putInt(valid_until);
    buffer.putLong(salt);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FutureSalt: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(future_salt valid_since:" + valid_since + " valid_until:" + valid_until + " salt:" + String.format("0x%016x", salt) + ")";
  }
}
