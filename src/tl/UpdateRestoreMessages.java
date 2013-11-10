package tl;

import java.nio.ByteBuffer;

public class UpdateRestoreMessages extends tl.TUpdate {

  
  public UpdateRestoreMessages(ByteBuffer buffer) throws Exception {
    messages = TL.readVectorInt(buffer, true);
    pts = buffer.getInt();
  }
  
  public UpdateRestoreMessages(int[] messages, int pts) {
    this.messages = messages;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd15de04d);
    }
    TL.writeVector(buffer, messages, true, false);
    buffer.putInt(pts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateRestoreMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + messages.length * 4;
  }
  
  public String toString() {
    return "(updateRestoreMessages messages:" + TL.toString(messages) + " pts:" + pts + ")";
  }
}
