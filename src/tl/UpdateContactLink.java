package tl;

import java.nio.ByteBuffer;

public class UpdateContactLink extends tl.TUpdate {

  
  public UpdateContactLink(ByteBuffer buffer) throws Exception {
    user_id = buffer.getInt();
    my_link = (tl.contacts.TMyLink) TL.read(buffer);
    foreign_link = (tl.contacts.TForeignLink) TL.read(buffer);
  }
  
  public UpdateContactLink(int user_id, tl.contacts.TMyLink my_link, tl.contacts.TForeignLink foreign_link) {
    this.user_id = user_id;
    this.my_link = my_link;
    this.foreign_link = foreign_link;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x51a48a9a);
    }
    buffer.putInt(user_id);
    my_link.writeTo(buffer, true);
    foreign_link.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UpdateContactLink: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 12 + my_link.length() + foreign_link.length();
  }
  
  public String toString() {
    return "(updateContactLink user_id:" + user_id + " my_link:" + my_link + " foreign_link:" + foreign_link + ")";
  }
}
