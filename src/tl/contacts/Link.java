package tl.contacts;

import tl.TL;
import java.nio.ByteBuffer;

public class Link extends tl.contacts.TLink {

  
  public Link(ByteBuffer buffer) throws Exception {
    my_link = (tl.contacts.TMyLink) TL.read(buffer);
    foreign_link = (tl.contacts.TForeignLink) TL.read(buffer);
    user = (tl.TUser) TL.read(buffer);
  }
  
  public Link(tl.contacts.TMyLink my_link, tl.contacts.TForeignLink foreign_link, tl.TUser user) {
    this.my_link = my_link;
    this.foreign_link = foreign_link;
    this.user = user;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xeccea3f5);
    }
    my_link.writeTo(buffer, true);
    foreign_link.writeTo(buffer, true);
    user.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Link: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + my_link.length() + foreign_link.length() + user.length();
  }
  
  public String toString() {
    return "(contacts.link my_link:" + my_link + " foreign_link:" + foreign_link + " user:" + user + ")";
  }
}
