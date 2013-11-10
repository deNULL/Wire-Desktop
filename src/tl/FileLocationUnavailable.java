package tl;

import java.nio.ByteBuffer;

public class FileLocationUnavailable extends tl.TFileLocation {

  
  public FileLocationUnavailable(ByteBuffer buffer) throws Exception {
    volume_id = buffer.getLong();
    local_id = buffer.getInt();
    secret = buffer.getLong();
  }
  
  public FileLocationUnavailable(long volume_id, int local_id, long secret) {
    this.volume_id = volume_id;
    this.local_id = local_id;
    this.secret = secret;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x7c596b46);
    }
    buffer.putLong(volume_id);
    buffer.putInt(local_id);
    buffer.putLong(secret);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileLocationUnavailable: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 20;
  }
  
  public String toString() {
    return "(fileLocationUnavailable volume_id:" + String.format("0x%016x", volume_id) + " local_id:" + local_id + " secret:" + String.format("0x%016x", secret) + ")";
  }
}
