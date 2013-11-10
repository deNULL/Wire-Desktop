package tl.messages;

import tl.TL;
import java.nio.ByteBuffer;

public class SendBroadcast extends tl.TLFunction {
  public tl.TInputUser[] contacts;
  public String message;
  public tl.TInputMedia media;
  
  public SendBroadcast(ByteBuffer buffer) throws Exception {
    contacts = TL.readVector(buffer, true, new tl.TInputUser[0]);
    message = new String(TL.readString(buffer), "UTF8");
    media = (tl.TInputMedia) TL.read(buffer);
  }
  
  public SendBroadcast(tl.TInputUser[] contacts, String message, tl.TInputMedia media) {
    this.contacts = contacts;
    this.message = message;
    this.media = media;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x41bb0972);
    }
    TL.writeVector(buffer, contacts, true, true);
    TL.writeString(buffer, message.getBytes("UTF8"), false);
    media.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SendBroadcast: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + TL.length(contacts) + TL.length(message.getBytes("UTF8")) + media.length();
  }
  
  public String toString() {
    return "(messages.sendBroadcast contacts:" + TL.toString(contacts) + " message:" + "message" + " media:" + media + ")";
  }
}
