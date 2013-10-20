package tl;

import java.nio.ByteBuffer;

public class UpdateRestoreMessages extends tl.TUpdate {
  
  public UpdateRestoreMessages(ByteBuffer buffer) {
    messages = TL.readVectorInt(buffer, true);
    pts = buffer.getInt();
  }
  
  public UpdateRestoreMessages(int[] messages, int pts) {
    this.messages = messages;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xd15de04d);
    }
    TL.writeVector(buffer, messages, true, false);
    buffer.putInt(pts);
  	return buffer;
  }
  
  public int length() {
    return 12 + messages.length * 4;
  }
  
  public String toString() {
    return "(UpdateRestoreMessages messages:" + TL.toString(messages) + " pts:" + pts + ")";
  }
}
