package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFile extends tl.TInputEncryptedFile {
  
  public InputEncryptedFile(ByteBuffer buffer) {
    id = buffer.getLong();
    access_hash = buffer.getLong();
  }
  
  public InputEncryptedFile(long id, long access_hash) {
    this.id = id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x5a17b5e5);
    }
    buffer.putLong(id);
    buffer.putLong(access_hash);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(InputEncryptedFile id:" + String.format("0x%016x", id) + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
