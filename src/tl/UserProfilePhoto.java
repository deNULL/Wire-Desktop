package tl;

import java.nio.ByteBuffer;

public class UserProfilePhoto extends tl.TUserProfilePhoto {

  
  public UserProfilePhoto(ByteBuffer buffer) {
    photo_id = buffer.getLong();
    photo_small = (tl.TFileLocation) TL.read(buffer);
    photo_big = (tl.TFileLocation) TL.read(buffer);
  }
  
  public UserProfilePhoto(long photo_id, tl.TFileLocation photo_small, tl.TFileLocation photo_big) {
    this.photo_id = photo_id;
    this.photo_small = photo_small;
    this.photo_big = photo_big;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xd559d8c8);
    }
    buffer.putLong(photo_id);
    photo_small.writeTo(buffer, true);
    photo_big.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserProfilePhoto: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 16 + photo_small.length() + photo_big.length();
  }
  
  public String toString() {
    return "(userProfilePhoto photo_id:" + String.format("0x%016x", photo_id) + " photo_small:" + photo_small + " photo_big:" + photo_big + ")";
  }
}
