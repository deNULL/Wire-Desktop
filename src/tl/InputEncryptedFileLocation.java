package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFileLocation extends tl.TInputFileLocation {
  
  public InputEncryptedFileLocation(ByteBuffer buffer) {
    id = buffer.getLong();
    access_hash = buffer.getLong();
  }
  
  public InputEncryptedFileLocation(long id, long access_hash) {
    this.id = id;
    this.access_hash = access_hash;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf5235d55);
    }
    buffer.putLong(id);
    buffer.putLong(access_hash);
  	return buffer;
  }
  
  public int length() {
    return 16;
  }
  
  public String toString() {
    return "(InputEncryptedFileLocation id:" + String.format("0x%016x", id) + " access_hash:" + String.format("0x%016x", access_hash) + ")";
  }
}
