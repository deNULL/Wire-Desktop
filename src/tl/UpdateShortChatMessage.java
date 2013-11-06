package tl;

import java.nio.ByteBuffer;

public class UpdateShortChatMessage extends tl.TUpdates {

  
  public UpdateShortChatMessage(ByteBuffer buffer) {
    id = buffer.getInt();
    from_id = buffer.getInt();
    chat_id = buffer.getInt();
    try {  message = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    pts = buffer.getInt();
    date = buffer.getInt();
    seq = buffer.getInt();
  }
  
  public UpdateShortChatMessage(int id, int from_id, int chat_id, String message, int pts, int date, int seq) {
    this.id = id;
    this.from_id = from_id;
    this.chat_id = chat_id;
    this.message = message;
    this.pts = pts;
    this.date = date;
    this.seq = seq;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2b2fbd4e);
    }
    buffer.putInt(id);
    buffer.putInt(from_id);
    buffer.putInt(chat_id);
    try { TL.writeString(buffer, message.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(pts);
    buffer.putInt(date);
    buffer.putInt(seq);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateShortChatMessage: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 24 + TL.length(message.getBytes());
  }
  
  public String toString() {
    return "(updateShortChatMessage id:" + id + " from_id:" + from_id + " chat_id:" + chat_id + " message:" + "message" + " pts:" + pts + " date:" + date + " seq:" + seq + ")";
  }
}
