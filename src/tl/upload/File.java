package tl.upload;

import tl.TL;
import java.nio.ByteBuffer;

public class File extends tl.upload.TFile {

  
  public File(ByteBuffer buffer) {
    type = (tl.storage.TFileType) TL.read(buffer);
    mtime = buffer.getInt();
    bytes = TL.readString(buffer);
  }
  
  public File(tl.storage.TFileType type, int mtime, byte[] bytes) {
    this.type = type;
    this.mtime = mtime;
    this.bytes = bytes;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x96a18d5);
    }
    type.writeTo(buffer, true);
    buffer.putInt(mtime);
    TL.writeString(buffer, bytes, false);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at File: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + type.length() + TL.length(bytes);
  }
  
  public String toString() {
    return "(upload.file type:" + type + " mtime:" + mtime + " bytes:" + TL.toString(bytes) + ")";
  }
}
