package tl;

import java.nio.ByteBuffer;

public class InvokeAfterMsg extends tl.TLFunction {
  public long msg_id;
  public tl.TLObject query;
  
  public InvokeAfterMsg(ByteBuffer buffer) {
    msg_id = buffer.getLong();
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeAfterMsg(long msg_id, tl.TLObject query) {
    this.msg_id = msg_id;
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xcb9f372d);
    }
    buffer.putLong(msg_id);
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 12 + query.length();
  }
  
  public String toString() {
    return "(InvokeAfterMsg msg_id:" + String.format("0x%016x", msg_id) + " query:" + query + ")";
  }
}
