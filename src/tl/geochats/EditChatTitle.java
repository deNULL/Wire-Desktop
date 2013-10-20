package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class EditChatTitle extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public String title;
  public String address;
  
  public EditChatTitle(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    title = new String(TL.readString(buffer));
    address = new String(TL.readString(buffer));
  }
  
  public EditChatTitle(tl.TInputGeoChat peer, String title, String address) {
    this.peer = peer;
    this.title = title;
    this.address = address;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x4c8e2273);
    }
    peer.writeTo(buffer, false);
    TL.writeString(buffer, title.getBytes(), false);
    TL.writeString(buffer, address.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length() + TL.length(title.getBytes()) + TL.length(address.getBytes());
  }
  
  public String toString() {
    return "(EditChatTitle peer:" + peer + " title:" + "title" + " address:" + "address" + ")";
  }
}
