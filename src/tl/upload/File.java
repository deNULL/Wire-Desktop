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
    if (boxed) {
      buffer.putInt(0x96a18d5);
    }
    type.writeTo(buffer, false);
    buffer.putInt(mtime);
    TL.writeString(buffer, bytes, false);
  	return buffer;
  }
  
  public int length() {
    return 8 + type.length() + TL.length(bytes);
  }
  
  public String toString() {
    return "(File type:" + type + " mtime:" + mtime + " bytes:" + TL.toString(bytes) + ")";
  }
}
