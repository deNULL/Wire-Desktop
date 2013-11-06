package tl.account;

import tl.TL;
import java.nio.ByteBuffer;

public class UnregisterDevice extends tl.TLFunction {
  public int token_type;
  public String token;
  
  public UnregisterDevice(ByteBuffer buffer) {
    token_type = buffer.getInt();
    try {  token = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public UnregisterDevice(int token_type, String token) {
    this.token_type = token_type;
    this.token = token;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x65c55b40);
    }
    buffer.putInt(token_type);
    try { TL.writeString(buffer, token.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UnregisterDevice: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + TL.length(token.getBytes());
  }
  
  public String toString() {
    return "(account.unregisterDevice token_type:" + token_type + " token:" + "token" + ")";
  }
}
