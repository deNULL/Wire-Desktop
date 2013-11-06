package tl;

import java.nio.ByteBuffer;

public class InputEncryptedFileUploaded extends tl.TInputEncryptedFile {

  
  public InputEncryptedFileUploaded(ByteBuffer buffer) {
    id = buffer.getLong();
    parts = buffer.getInt();
    try {  md5_checksum = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    key_fingerprint = buffer.getInt();
  }
  
  public InputEncryptedFileUploaded(long id, int parts, String md5_checksum, int key_fingerprint) {
    this.id = id;
    this.parts = parts;
    this.md5_checksum = md5_checksum;
    this.key_fingerprint = key_fingerprint;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x64bd0306);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    try { TL.writeString(buffer, md5_checksum.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(key_fingerprint);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputEncryptedFileUploaded: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + TL.length(md5_checksum.getBytes());
  }
  
  public String toString() {
    return "(inputEncryptedFileUploaded id:" + String.format("0x%016x", id) + " parts:" + parts + " md5_checksum:" + "md5_checksum" + " key_fingerprint:" + key_fingerprint + ")";
  }
}
