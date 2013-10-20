package tl;

import java.nio.ByteBuffer;

public class InputFile extends tl.TInputFile {
  
  public InputFile(ByteBuffer buffer) {
    id = buffer.getLong();
    parts = buffer.getInt();
    name = new String(TL.readString(buffer));
    md5_checksum = new String(TL.readString(buffer));
  }
  
  public InputFile(long id, int parts, String name, String md5_checksum) {
    this.id = id;
    this.parts = parts;
    this.name = name;
    this.md5_checksum = md5_checksum;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0xf52ff27f);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    TL.writeString(buffer, name.getBytes(), false);
    TL.writeString(buffer, md5_checksum.getBytes(), false);
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(name.getBytes()) + TL.length(md5_checksum.getBytes());
  }
  
  public String toString() {
    return "(InputFile id:" + String.format("0x%016x", id) + " parts:" + parts + " name:" + "name" + " md5_checksum:" + "md5_checksum" + ")";
  }
}
