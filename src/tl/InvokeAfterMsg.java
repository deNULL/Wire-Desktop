package tl;

import java.nio.ByteBuffer;

public class InvokeAfterMsg extends tl.TLFunction {
  public long msg_id;
  public tl.TLObject query;
  
  public InvokeAfterMsg(ByteBuffer buffer) throws Exception {
    msg_id = buffer.getLong();
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeAfterMsg(long msg_id, tl.TLObject query) {
    this.msg_id = msg_id;
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xcb9f372d);
    }
    buffer.putLong(msg_id);
    query.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InvokeAfterMsg: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + query.length();
  }
  
  public String toString() {
    return "(invokeAfterMsg msg_id:" + String.format("0x%016x", msg_id) + " query:" + query + ")";
  }
}
