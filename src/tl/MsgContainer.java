package tl;

import java.nio.ByteBuffer;

public class MsgContainer extends tl.TMessageContainer {

  
  public MsgContainer(ByteBuffer buffer) throws Exception {
    messages = TL.readVectorMessage(buffer, false);;
  }
  
  public MsgContainer(tl.TTransportMessage[] messages) {
    this.messages = messages;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x73f1f8dc);
    }
    TL.writeVector(buffer, messages, false, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MsgContainer: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + TL.length(messages);
  }
  
  public String toString() {
    return "(msg_container messages:" + TL.toString(messages) + ")";
  }
}
