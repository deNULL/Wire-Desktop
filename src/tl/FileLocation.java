package tl;

import java.nio.ByteBuffer;

public class FileLocation extends tl.TFileLocation {

  
  public FileLocation(ByteBuffer buffer) {
    dc_id = buffer.getInt();
    volume_id = buffer.getLong();
    local_id = buffer.getInt();
    secret = buffer.getLong();
  }
  
  public FileLocation(int dc_id, long volume_id, int local_id, long secret) {
    this.dc_id = dc_id;
    this.volume_id = volume_id;
    this.local_id = local_id;
    this.secret = secret;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x53d69076);
    }
    buffer.putInt(dc_id);
    buffer.putLong(volume_id);
    buffer.putInt(local_id);
    buffer.putLong(secret);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at FileLocation: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 24;
  }
  
  public String toString() {
    return "(fileLocation dc_id:" + dc_id + " volume_id:" + String.format("0x%016x", volume_id) + " local_id:" + local_id + " secret:" + String.format("0x%016x", secret) + ")";
  }
}
