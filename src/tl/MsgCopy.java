package tl;

import java.nio.ByteBuffer;

public class MsgCopy extends tl.TMessageCopy {
  
  public MsgCopy(ByteBuffer buffer) {
    orig_message = (tl.TTransportMessage) TL.read(buffer);
  }
  
  public MsgCopy(tl.TTransportMessage orig_message) {
    this.orig_message = orig_message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xe06046b2);
    }
    orig_message.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 4 + orig_message.length();
  }
  
  public String toString() {
    return "(MsgCopy orig_message:" + orig_message + ")";
  }
}
