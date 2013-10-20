package tl;

import java.nio.ByteBuffer;

public class UpdateReadMessages extends tl.TUpdate {

  
  public UpdateReadMessages(ByteBuffer buffer) {
    messages = TL.readVectorInt(buffer, true);
    pts = buffer.getInt();
  }
  
  public UpdateReadMessages(int[] messages, int pts) {
    this.messages = messages;
    this.pts = pts;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xc6649e31);
    }
    TL.writeVector(buffer, messages, true, false);
    buffer.putInt(pts);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateReadMessages: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + messages.length * 4;
  }
  
  public String toString() {
    return "(updateReadMessages messages:" + TL.toString(messages) + " pts:" + pts + ")";
  }
}
