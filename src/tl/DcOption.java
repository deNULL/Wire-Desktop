package tl;

import java.nio.ByteBuffer;

public class DcOption extends tl.TDcOption {

  
  public DcOption(ByteBuffer buffer) throws Exception {
    id = buffer.getInt();
    hostname = new String(TL.readString(buffer), "UTF8");
    ip_address = new String(TL.readString(buffer), "UTF8");
    port = buffer.getInt();
  }
  
  public DcOption(int id, String hostname, String ip_address, int port) {
    this.id = id;
    this.hostname = hostname;
    this.ip_address = ip_address;
    this.port = port;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2ec2a43c);
    }
    buffer.putInt(id);
    TL.writeString(buffer, hostname.getBytes("UTF8"), false);
    TL.writeString(buffer, ip_address.getBytes("UTF8"), false);
    buffer.putInt(port);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DcOption: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 8 + TL.length(hostname.getBytes("UTF8")) + TL.length(ip_address.getBytes("UTF8"));
  }
  
  public String toString() {
    return "(dcOption id:" + id + " hostname:" + "hostname" + " ip_address:" + "ip_address" + " port:" + port + ")";
  }
}
