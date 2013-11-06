package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class EditChatTitle extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public String title;
  public String address;
  
  public EditChatTitle(ByteBuffer buffer) {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    try {  title = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  address = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
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
    try { TL.writeString(buffer, title.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, address.getBytes("UTF8"), false); } catch (Exception e) { };
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
