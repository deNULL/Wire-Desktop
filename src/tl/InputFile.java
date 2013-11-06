package tl;

import java.nio.ByteBuffer;

public class InputFile extends tl.TInputFile {

  
  public InputFile(ByteBuffer buffer) {
    id = buffer.getLong();
    parts = buffer.getInt();
    try {  name = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  md5_checksum = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
  }
  
  public InputFile(long id, int parts, String name, String md5_checksum) {
    this.id = id;
    this.parts = parts;
    this.name = name;
    this.md5_checksum = md5_checksum;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf52ff27f);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    try { TL.writeString(buffer, name.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, md5_checksum.getBytes("UTF8"), false); } catch (Exception e) { };
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputFile: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 12 + TL.length(name.getBytes()) + TL.length(md5_checksum.getBytes());
  }
  
  public String toString() {
    return "(inputFile id:" + String.format("0x%016x", id) + " parts:" + parts + " name:" + "name" + " md5_checksum:" + "md5_checksum" + ")";
  }
}
