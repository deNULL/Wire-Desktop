package tl;

import java.nio.ByteBuffer;

public class InputFile extends tl.TInputFile {

  
  public InputFile(ByteBuffer buffer) throws Exception {
    id = buffer.getLong();
    parts = buffer.getInt();
    name = new String(TL.readString(buffer), "UTF8");
    md5_checksum = new String(TL.readString(buffer), "UTF8");
  }
  
  public InputFile(long id, int parts, String name, String md5_checksum) {
    this.id = id;
    this.parts = parts;
    this.name = name;
    this.md5_checksum = md5_checksum;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf52ff27f);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    TL.writeString(buffer, name.getBytes("UTF8"), false);
    TL.writeString(buffer, md5_checksum.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputFile: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + TL.length(name.getBytes("UTF8")) + TL.length(md5_checksum.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(inputFile id:" + String.format("0x%016x", id) + " parts:" + parts + " name:" + "name" + " md5_checksum:" + "md5_checksum" + ")";
  }
}
