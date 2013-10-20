package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class DifferenceSlice extends tl.updates.TDifference {

  
  public DifferenceSlice(ByteBuffer buffer) {
    new_messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    new_encrypted_messages = TL.readVector(buffer, true, new tl.TEncryptedMessage[0]);
    other_updates = TL.readVector(buffer, true, new tl.TUpdate[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    intermediate_state = (tl.updates.TState) TL.read(buffer);
  }
  
  public DifferenceSlice(tl.TMessage[] new_messages, tl.TEncryptedMessage[] new_encrypted_messages, tl.TUpdate[] other_updates, tl.TChat[] chats, tl.TUser[] users, tl.updates.TState intermediate_state) {
    this.new_messages = new_messages;
    this.new_encrypted_messages = new_encrypted_messages;
    this.other_updates = other_updates;
    this.chats = chats;
    this.users = users;
    this.intermediate_state = intermediate_state;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xa8fb1981);
    }
    TL.writeVector(buffer, new_messages, true, true);
    TL.writeVector(buffer, new_encrypted_messages, true, true);
    TL.writeVector(buffer, other_updates, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    intermediate_state.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at DifferenceSlice: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 44 + TL.length(new_messages) + TL.length(new_encrypted_messages) + TL.length(other_updates) + TL.length(chats) + TL.length(users) + intermediate_state.length();
  }
  
  public String toString() {
    return "(updates.differenceSlice new_messages:" + TL.toString(new_messages) + " new_encrypted_messages:" + TL.toString(new_encrypted_messages) + " other_updates:" + TL.toString(other_updates) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " intermediate_state:" + intermediate_state + ")";
  }
}
