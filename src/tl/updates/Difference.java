package tl.updates;

import tl.TL;
import java.nio.ByteBuffer;

public class Difference extends tl.updates.TDifference {

  
  public Difference(ByteBuffer buffer) {
    new_messages = TL.readVector(buffer, true, new tl.TMessage[0]);
    new_encrypted_messages = TL.readVector(buffer, true, new tl.TEncryptedMessage[0]);
    other_updates = TL.readVector(buffer, true, new tl.TUpdate[0]);
    chats = TL.readVector(buffer, true, new tl.TChat[0]);
    users = TL.readVector(buffer, true, new tl.TUser[0]);
    state = (tl.updates.TState) TL.read(buffer);
  }
  
  public Difference(tl.TMessage[] new_messages, tl.TEncryptedMessage[] new_encrypted_messages, tl.TUpdate[] other_updates, tl.TChat[] chats, tl.TUser[] users, tl.updates.TState state) {
    this.new_messages = new_messages;
    this.new_encrypted_messages = new_encrypted_messages;
    this.other_updates = other_updates;
    this.chats = chats;
    this.users = users;
    this.state = state;
  }
  
  public ByteBuffer writeTo(ByteBuffer buffer, boolean boxed) {
    int oldPos = buffer.position();
    if (boxed) {
      buffer.putInt(0xf49ca0);
    }
    TL.writeVector(buffer, new_messages, true, true);
    TL.writeVector(buffer, new_encrypted_messages, true, true);
    TL.writeVector(buffer, other_updates, true, true);
    TL.writeVector(buffer, chats, true, true);
    TL.writeVector(buffer, users, true, true);
    state.writeTo(buffer, true);
    if (oldPos + length() + (boxed ? 4 : 0) != buffer.position()) {
      System.err.println("Invalid length at Difference: expected " + (length() + (boxed ? 4 : 0)) + " bytes, got " + (buffer.position() - oldPos));
    }
  	return buffer;
  }
  
  public int length() {
    return 44 + TL.length(new_messages) + TL.length(new_encrypted_messages) + TL.length(other_updates) + TL.length(chats) + TL.length(users) + state.length();
  }
  
  public String toString() {
    return "(updates.difference new_messages:" + TL.toString(new_messages) + " new_encrypted_messages:" + TL.toString(new_encrypted_messages) + " other_updates:" + TL.toString(other_updates) + " chats:" + TL.toString(chats) + " users:" + TL.toString(users) + " state:" + state + ")";
  }
}
