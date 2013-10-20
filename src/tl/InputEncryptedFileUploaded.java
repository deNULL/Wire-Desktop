package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFileUploaded extends tl.TInputEncryptedFile {
  
  public InputEncryptedFileUploaded(ByteBuffer buffer) {
    id = buffer.getLong();
    parts = buffer.getInt();
    md5_checksum = new String(TL.readString(buffer));
    key_fingerprint = buffer.getInt();
  }
  
  public InputEncryptedFileUploaded(long id, int parts, String md5_checksum, int key_fingerprint) {
    this.id = id;
    this.parts = parts;
    this.md5_checksum = md5_checksum;
    this.key_fingerprint = key_fingerprint;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x64bd0306);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    TL.writeString(buffer, md5_checksum.getBytes(), false);
    buffer.putInt(key_fingerprint);
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(md5_checksum.getBytes());
  }
  
  public String toString() {
    return "(InputEncryptedFileUploaded id:" + String.format("0x%016x", id) + " parts:" + parts + " md5_checksum:" + "md5_checksum" + " key_fingerprint:" + key_fingerprint + ")";
  }
}
