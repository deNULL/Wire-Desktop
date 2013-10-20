package tl.updates;

public abstract class TDifference extends tl.TLObject {
  public tl.TUser[] users;
  public tl.TMessage[] new_messages;
  public tl.updates.TState state;
  public int seq;
  public tl.TEncryptedMessage[] new_encrypted_messages;
  public tl.updates.TState intermediate_state;
  public int date;
  public tl.TChat[] chats;
  public tl.TUpdate[] other_updates;
}
