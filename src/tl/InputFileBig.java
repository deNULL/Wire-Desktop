package tl;

import java.nio.ByteBuffer;

public class InputFileBig extends tl.TInputFile {

  
  public InputFileBig(ByteBuffer buffer) throws Exception {
    id = buffer.getLong();
    parts = buffer.getInt();
    name = new String(TL.readString(buffer), "UTF8");
  }
  
  public InputFileBig(long id, int parts, String name) {
    this.id = id;
    this.parts = parts;
    this.name = name;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xfa4f0bb5);
    }
    buffer.putLong(id);
    buffer.putInt(parts);
    TL.writeString(buffer, name.getBytes("UTF8"), false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at InputFileBig: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + TL.length(name.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(inputFileBig id:" + String.format("0x%016x", id) + " parts:" + parts + " name:" + "name" + ")";
  }
}
