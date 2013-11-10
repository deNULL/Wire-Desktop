package tl;

import java.nio.ByteBuffer;

public class UpdateDeleteMessages extends tl.TUpdate {

  
  public UpdateDeleteMessages(ByteBuffer buffer) throws Exception {
    messages = TL.readVectorInt(buffer, true);
    pts = buffer.getInt();
  }
  
  public UpdateDeleteMessages(int[] messages, int pts) {
    this.messages = messages;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa92bfe26);
    }
    TL.writeVector(buffer, messages, true, false);
    buffer.putInt(pts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateDeleteMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + messages.length * 4;
  }
  
  public String toString() {
    return "(updateDeleteMessages messages:" + TL.toString(messages) + " pts:" + pts + ")";
  }
}
