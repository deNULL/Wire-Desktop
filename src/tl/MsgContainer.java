package tl;

import java.nio.ByteBuffer;

public class MsgContainer extends tl.TMessageContainer {
  
  public MsgContainer(ByteBuffer buffer) {
    messages = TL.readVectorMessage(buffer, false);
  }
  
  public MsgContainer(tl.TTransportMessage[] messages) {
    this.messages = messages;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x73f1f8dc);
    }
    TL.writeVector(buffer, messages, false, false);
    return buffer;
  }
  
  public int length() {
    return 4 + TL.length(messages);
  }
  
  public String toString() {
    return "(MsgContainer messages:" + TL.toString(messages) + ")";
  }
}
