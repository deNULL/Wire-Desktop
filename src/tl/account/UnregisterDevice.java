package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class UnregisterDevice extends tl.TLFunction {
  public int token_type;
  public String token;
  
  public UnregisterDevice(ByteBuffer buffer) {
    token_type = buffer.getInt();
    token = new String(TL.readString(buffer));
  }
  
  public UnregisterDevice(int token_type, String token) {
    this.token_type = token_type;
    this.token = token;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x65c55b40);
    }
    buffer.putInt(token_type);
    TL.writeString(buffer, token.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(token.getBytes());
  }
  
  public String toString() {
    return "(UnregisterDevice token_type:" + token_type + " token:" + "token" + ")";
  }
}
