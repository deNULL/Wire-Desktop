package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class RequestEncryption extends tl.TLFunction {
  public tl.TInputUser user_id;
  public int random_id;
  public byte[] g_a;
  
  public RequestEncryption(ByteBuffer buffer) {
    user_id = (tl.TInputUser) TL.read(buffer);
    random_id = buffer.getInt();
    g_a = TL.readString(buffer);
  }
  
  public RequestEncryption(tl.TInputUser user_id, int random_id, byte[] g_a) {
    this.user_id = user_id;
    this.random_id = random_id;
    this.g_a = g_a;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf64daf43);
    }
    user_id.writeTo(buffer, false);
    buffer.putInt(random_id);
    TL.writeString(buffer, g_a, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + user_id.length() + TL.length(g_a);
  }
  
  public String toString() {
    return "(RequestEncryption user_id:" + user_id + " random_id:" + random_id + " g_a:" + TL.toString(g_a) + ")";
  }
}
