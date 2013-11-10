package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFileBigUploaded extends tl.TInputEncryptedFile {

  
  public InputEncryptedFileBigUploaded(ByteBuffer buffer) throws Exception {
    id = buffer.getLong();
    parts = buffer.getInt();
    key_fingerprint = buffer.getInt();
  }
  
  public InputEncryptedFileBigUploaded(long id, int parts, int key_fingerprint) {
    this.id = id;
    this.parts = parts;
    this.key_fingerprint = key_fingerprint;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2dc173c8);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    buffer.putInt(key_fingerprint);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputEncryptedFileBigUploaded: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16;
  }
  
  public String toString() {
    return "(inputEncryptedFileBigUploaded id:" + String.format("0x%016x", id) + " parts:" + parts + " key_fingerprint:" + key_fingerprint + ")";
  }
}
