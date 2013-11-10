package tl;

import java.nio.ByteBuffer;

public class Message extends tl.TMessage {

  
  public Message(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    from_id = buffer.getInt();
    to_id = (tl.TPeer) TL.read(buffer);
    out = (buffer.getInt() == 0x997275b5);
    unread = (buffer.getInt() == 0x997275b5);
    date = buffer.getInt();
    message = new String(TL.readString(buffer), "UTF8");
    media = (tl.TMessageMedia) TL.read(buffer);
  }
  
  public Message(int id, int from_id, tl.TPeer to_id, boolean out, boolean unread, int date, String message, tl.TMessageMedia media) {
    this.id = id;
    this.from_id = from_id;
    this.to_id = to_id;
    this.out = out;
    this.unread = unread;
    this.date = date;
    this.message = message;
    this.media = media;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x22eb6aba);
    }
    buffer.putInt(id);
    buffer.putInt(from_id);
    to_id.writeTo(buffer, true);
    buffer.putInt(out ? 0x997275b5 : 0xbc799737);
    buffer.putInt(unread ? 0x997275b5 : 0xbc799737);
    buffer.putInt(date);
    TL.writeString(buffer, message.getBytes("UTF8"), false);
    media.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Message: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 28 + to_id.length() + TL.length(message.getBytes("UTF8")) + media.length();
  }
  
  public String toString() {
    return "(message id:" + id + " from_id:" + from_id + " to_id:" + to_id + " out:" + (out ? "true" : "false") + " unread:" + (unread ? "true" : "false") + " date:" + date + " message:" + "message" + " media:" + media + ")";
  }
}
