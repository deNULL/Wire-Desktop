package tl;

import java.nio.ByteBuffer;

public class UpdateDeleteMessages extends tl.TUpdate {
  
  public UpdateDeleteMessages(ByteBuffer buffer) {
    messages = TL.readVectorInt(buffer, true);
    pts = buffer.getInt();
  }
  
  public UpdateDeleteMessages(int[] messages, int pts) {
    this.messages = messages;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xa92bfe26);
    }
    TL.writeVector(buffer, messages, true, false);
    buffer.putInt(pts);
  	return buffer;
  }
  
  public int length() {
    return 12 + messages.length * 4;
  }
  
  public String toString() {
    return "(UpdateDeleteMessages messages:" + TL.toString(messages) + " pts:" + pts + ")";
  }
}
