package tl;

import java.nio.ByteBuffer;

public class EncryptedFile extends tl.TEncryptedFile {

  
  public EncryptedFile(ByteBuffer buffer) throws Exception {
    id = buffer.getLong();
    access_hash = buffer.getLong();
    size = buffer.getInt();
    dc_id = buffer.getInt();
    key_fingerprint = buffer.getInt();
  }
  
  public EncryptedFile(long id, long access_hash, int size, int dc_id, int key_fingerprint) {
    this.id = id;
    this.access_hash = access_hash;
    this.size = size;
    this.dc_id = dc_id;
    this.key_fingerprint = key_fingerprint;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4a70994c);
    }
    buffer.putLong(id);
    buffer.putLong(access_hash);
    buffer.putInt(size);
    buffer.putInt(dc_id);
    buffer.putInt(key_fingerprint);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at EncryptedFile: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 28;
  }
  
  public String toString() {
    return "(encryptedFile id:" + String.format("0x%016x", id) + " access_hash:" + String.format("0x%016x", access_hash) + " size:" + size + " dc_id:" + dc_id + " key_fingerprint:" + key_fingerprint + ")";
  }
}
