package tl;

import java.nio.ByteBuffer;

public class UserProfilePhotoEmpty extends tl.TUserProfilePhoto {

  
  public UserProfilePhotoEmpty(ByteBuffer buffer) throws Exception {

  }
  
  public UserProfilePhotoEmpty() {

  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) throws Exception {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0x4f11bae1);
    }

    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at UserProfilePhotoEmpty: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() throws Exception {
    return 0;
  }
  
  public String toString() {
    return "(userProfilePhotoEmpty)";
  }
}
