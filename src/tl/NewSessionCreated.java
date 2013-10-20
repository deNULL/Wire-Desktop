package tl;

import java.nio.ByteBuffer;

public class NewSessionCreated extends tl.TNewSession {

  
  public NewSessionCreated(ByteBuffer buffer) {
    first_msg_id = buffer.getLong();
    unique_id = buffer.getLong();
    server_salt = buffer.getLong();
  }
  
  public NewSessionCreated(long first_msg_id, long unique_id, long server_salt) {
    this.first_msg_id = first_msg_id;
    this.unique_id = unique_id;
    this.server_salt = server_salt;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x9ec20908);
    }
    buffer.putLong(first_msg_id);
    buffer.putLong(unique_id);
    buffer.putLong(server_salt);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at NewSessionCreated: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 24;
  }
  
  public String toString() {
    return "(new_session_created first_msg_id:" + String.format("0x%016x", first_msg_id) + " unique_id:" + String.format("0x%016x", unique_id) + " server_salt:" + String.format("0x%016x", server_salt) + ")";
  }
}
