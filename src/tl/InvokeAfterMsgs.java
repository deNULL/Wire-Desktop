package tl;

import java.nio.ByteBuffer;

public class InvokeAfterMsgs extends tl.TLFunction {
  public long[] msg_ids;
  public tl.TLObject query;
  
  public InvokeAfterMsgs(ByteBuffer buffer) {
    msg_ids = TL.readVectorLong(buffer, true);
    query = (tl.TLObject) TL.read(buffer);
  }
  
  public InvokeAfterMsgs(long[] msg_ids, tl.TLObject query) {
    this.msg_ids = msg_ids;
    this.query = query;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x3dc4b4f0);
    }
    TL.writeVector(buffer, msg_ids, true, false);
    query.writeTo(buffer, true);
  	return buffer;
  }
  
  public int length() {
    return 12 + msg_ids.length * 8 + query.length();
  }
  
  public String toString() {
    return "(InvokeAfterMsgs msg_ids:" + TL.toString(msg_ids) + " query:" + query + ")";
  }
}
