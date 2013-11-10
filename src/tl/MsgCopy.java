package tl;

import java.nio.ByteBuffer;

public class MsgCopy extends tl.TMessageCopy {

  
  public MsgCopy(ByteBuffer buffer) throws Exception {
    orig_message = (tl.TTransportMessage) TL.read(buffer);
  }
  
  public MsgCopy(tl.TTransportMessage orig_message) {
    this.orig_message = orig_message;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xe06046b2);
    }
    orig_message.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgCopy: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + orig_message.length();
  }
  
  public String toString() {
    return "(msg_copy orig_message:" + orig_message + ")";
  }
}
