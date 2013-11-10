package tl;

import java.nio.ByteBuffer;

public class ChatPhotoEmpty extends tl.TChatPhoto {

  
  public ChatPhotoEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public ChatPhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x37c1011c);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at ChatPhotoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(chatPhotoEmpty)";
  }
}
