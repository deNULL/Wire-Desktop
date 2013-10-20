package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class EditChatPhoto extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public tl.TInputChatPhoto photo;
  
  public EditChatPhoto(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    photo = (tl.TInputChatPhoto) TL.read(buffer);
  }
  
  public EditChatPhoto(tl.TInputGeoChat peer, tl.TInputChatPhoto photo) {
    this.peer = peer;
    this.photo = photo;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x35d81a95);
    }
    peer.writeTo(buffer, true);
    photo.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EditChatPhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length() + photo.length();
  }
  
  public String toString() {
    return "(geochats.editChatPhoto peer:" + peer + " photo:" + photo + ")";
  }
}
