package tl.geochats;

import tl.TL;
import java.nio.ByteBuffer;

public class EditChatTitle extends tl.TLFunction {
  public tl.TInputGeoChat peer;
  public String title;
  public String address;
  
  public EditChatTitle(ByteBuffer buffer) throws Exception {
    peer = (tl.TInputGeoChat) TL.read(buffer);
    title = new String(TL.readString(buffer), "UTF8");
    address = new String(TL.readString(buffer), "UTF8");
  }
  
  public EditChatTitle(tl.TInputGeoChat peer, String title, String address) {
    this.peer = peer;
    this.title = title;
    this.address = address;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4c8e2273);
    }
    peer.writeTo(buffer, true);
    TL.writeString(buffer, title.getBytes("UTF8"), false);
    TL.writeString(buffer, address.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EditChatTitle: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 4 + peer.length() + TL.length(title.getBytes("UTF8")) + TL.length(address.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(geochats.editChatTitle peer:" + peer + " title:" + "title" + " address:" + "address" + ")";
  }
}
