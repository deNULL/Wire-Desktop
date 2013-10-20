package tl;

import java.nio.ByteBuffer;

public class DcOption extends tl.TDcOption {
  
  public DcOption(ByteBuffer buffer) {
    id = buffer.getInt();
    hostname = new String(TL.readString(buffer));
    ip_address = new String(TL.readString(buffer));
    port = buffer.getInt();
  }
  
  public DcOption(int id, String hostname, String ip_address, int port) {
    this.id = id;
    this.hostname = hostname;
    this.ip_address = ip_address;
    this.port = port;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    if (boxed) {
      buffer.putInt(0x2ec2a43c);
    }
    buffer.putInt(id);
    TL.writeString(buffer, hostname.getBytes(), false);
    TL.writeString(buffer, ip_address.getBytes(), false);
    buffer.putInt(port);
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(hostname.getBytes()) + TL.length(ip_address.getBytes());
  }
  
  public String toString() {
    return "(DcOption id:" + id + " hostname:" + "hostname" + " ip_address:" + "ip_address" + " port:" + port + ")";
  }
}
