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
    if (boxed) {
      buffer.putInt(0x35d81a95);
    }
    peer.writeTo(buffer, false);
    photo.writeTo(buffer, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + peer.length() + photo.length();
  }
  
  public String toString() {
    return "(EditChatPhoto peer:" + peer + " photo:" + photo + ")";
  }
}
