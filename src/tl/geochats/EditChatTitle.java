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
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4c8e2273);
    }
    peer.writeTo(buffer, true);
    TL.writeString(buffer, title.getBytes(), false);
    TL.writeString(buffer, address.getBytes(), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EditChatTitle: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 4 + peer.length() + TL.length(title.getBytes()) + TL.length(address.getBytes());
  }
  
  public String toString() {
    return "(geochats.editChatTitle peer:" + peer + " title:" + "title" + " address:" + "address" + ")";
  }
}
