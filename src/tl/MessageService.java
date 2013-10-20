package tl;

import java.nio.ByteBuffer;

public class MessageService extends tl.TMessage {

  
  public MessageService(ByteBuffer buffer) {
    id = buffer.getInt();
    from_id = buffer.getInt();
    to_id = (tl.TPeer) TL.read(buffer);
    out = (buffer.getInt() == 0x997275b5);
    unread = (buffer.getInt() == 0x997275b5);
    date = buffer.getInt();
    action = (tl.TMessageAction) TL.read(buffer);
  }
  
  public MessageService(int id, int from_id, tl.TPeer to_id, boolean out, boolean unread, int date, tl.TMessageAction action) {
    this.id = id;
    this.from_id = from_id;
    this.to_id = to_id;
    this.out = out;
    this.unread = unread;
    this.date = date;
    this.action = action;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9f8d60bb);
    }
    buffer.putInt(id);
    buffer.putInt(from_id);
    to_id.writeTo(buffer, true);
    buffer.putInt(out ? 0x997275b5 : 0xbc799737);
    buffer.putInt(unread ? 0x997275b5 : 0xbc799737);
    buffer.putInt(date);
    action.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at MessageService: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 28 + to_id.length() + action.length();
  }
  
  public String toString() {
    return "(messageService id:" + id + " from_id:" + from_id + " to_id:" + to_id + " out:" + (out ? "true" : "false") + " unread:" + (unread ? "true" : "false") + " date:" + date + " action:" + action + ")";
  }
}
