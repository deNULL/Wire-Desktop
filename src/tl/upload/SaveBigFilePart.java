package tl.upload;

import tl.TL;
import java.nio.ByteBuffer;

public class SaveBigFilePart extends tl.TLFunction {
  public long file_id;
  public int file_part;
  public int file_total_parts;
  public byte[] bytes;
  
  public SaveBigFilePart(ByteBuffer buffer) throws Exception {
    file_id = buffer.getLong();
    file_part = buffer.getInt();
    file_total_parts = buffer.getInt();
    bytes = TL.readString(buffer);
  }
  
  public SaveBigFilePart(long file_id, int file_part, int file_total_parts, byte[] bytes) {
    this.file_id = file_id;
    this.file_part = file_part;
    this.file_total_parts = file_total_parts;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xde7b673d);
    }
    buffer.putLong(file_id);
    buffer.putInt(file_part);
    buffer.putInt(file_total_parts);
    TL.writeString(buffer, bytes, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at SaveBigFilePart: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 16 + TL.length(bytes);
  }
  
  public String toString() {
    return "(upload.saveBigFilePart file_id:" + String.format("0x%016x", file_id) + " file_part:" + file_part + " file_total_parts:" + file_total_parts + " bytes:" + TL.toString(bytes) + ")";
  }
}
