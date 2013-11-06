package tl;

import java.nio.ByteBuffer;

public class DcOption extends tl.TDcOption {

  
  public DcOption(ByteBuffer buffer) {
    id = buffer.getInt();
    try {  hostname = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    try {  ip_address = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
    port = buffer.getInt();
  }
  
  public DcOption(int id, String hostname, String ip_address, int port) {
    this.id = id;
    this.hostname = hostname;
    this.ip_address = ip_address;
    this.port = port;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x2ec2a43c);
    }
    buffer.putInt(id);
    try { TL.writeString(buffer, hostname.getBytes("UTF8"), false); } catch (Exception e) { };
    try { TL.writeString(buffer, ip_address.getBytes("UTF8"), false); } catch (Exception e) { };
    buffer.putInt(port);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DcOption: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 8 + TL.length(hostname.getBytes()) + TL.length(ip_address.getBytes());
  }
  
  public String toString() {
    return "(dcOption id:" + id + " hostname:" + "hostname" + " ip_address:" + "ip_address" + " port:" + port + ")";
  }
}