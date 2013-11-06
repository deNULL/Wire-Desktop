package tl;

import static ru.denull.mtproto.CryptoUtils.CRC32;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.zip.GZIPInputStream;

import ru.denull.mtproto.Base64;

public class TL {	
  private static final String TAG = "TL";
  private static int lastNum = 0, prevNum = 0;
  
	// Read method (boxed)
	public static TLObject read(ByteBuffer buffer) {
		int number = buffer.getInt();
		prevNum = lastNum;
		lastNum = number;
		
		switch (number) {
			case 0x3072cfa1: { // autounpack gzipped container
				GzipPacked pack = new GzipPacked(buffer);
				try {
					GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(pack.packed_data));
					ByteArrayOutputStream output = new ByteArrayOutputStream(pack.packed_data.length * 2);
					byte[] temp = new byte[1024];
					while (stream.available() > 0) {
					  int count = stream.read(temp);
					  if (count <= 0) break;
					  output.write(temp, 0, count);
					}
					stream.close();
					ByteBuffer buf = ByteBuffer.wrap(output.toByteArray());
					buf.order(ByteOrder.LITTLE_ENDIAN);
					return read(buf);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			case 0x60469778: return new tl.ReqPq(buffer);
			case 0x05162463: return new tl.ResPQ(buffer);
			case 0xd712e4be: return new tl.ReqDHParams(buffer);
			case 0x83c95aec: return new tl.PQInnerData(buffer);
			case 0x79cb045d: return new tl.ServerDHParamsFail(buffer);
			case 0xd0e8075c: return new tl.ServerDHParamsOk(buffer);
			case 0xb5890dba: return new tl.ServerDHInnerData(buffer);
			case 0xf5045f1f: return new tl.SetClientDHParams(buffer);
			case 0x6643b654: return new tl.ClientDHInnerData(buffer);
			case 0x3bcbf734: return new tl.DhGenOk(buffer);
			case 0x46dc1fb9: return new tl.DhGenRetry(buffer);
			case 0xa69dae02: return new tl.DhGenFail(buffer);
			case 0xf35c6d01: return new tl.RpcResult(buffer);
			case 0x2144ca19: return new tl.RpcError(buffer);
			case 0x58e4a740: return new tl.RpcDropAnswer(buffer);
			case 0x5e2ad36e: return new tl.RpcAnswerUnknown(buffer);
			case 0xcd78e586: return new tl.RpcAnswerDroppedRunning(buffer);
			case 0xa43ad8b7: return new tl.RpcAnswerDropped(buffer);
			case 0xb921bd04: return new tl.GetFutureSalts(buffer);
			case 0x0949d9dc: return new tl.FutureSalt(buffer);
			case 0xae500895: return new tl.FutureSalts(buffer);
			case 0x7abe77ec: return new tl.Ping(buffer);
			case 0x347773c5: return new tl.Pong(buffer);
			case 0xe7512126: return new tl.DestroySession(buffer);
			case 0xe22045fc: return new tl.DestroySessionOk(buffer);
			case 0x62d350c9: return new tl.DestroySessionNone(buffer);
			case 0x9ec20908: return new tl.NewSessionCreated(buffer);
			case 0x73f1f8dc: return new tl.MsgContainer(buffer);
			case 0xe06046b2: return new tl.MsgCopy(buffer);
			case 0x9299359f: return new tl.HttpWait(buffer);
			case 0x62d6b459: return new tl.MsgsAck(buffer);
			case 0xa7eff811: return new tl.BadMsgNotification(buffer);
			case 0xedab447b: return new tl.BadServerSalt(buffer);
			case 0xda69fb52: return new tl.MsgsStateReq(buffer);
			case 0x04deb57d: return new tl.MsgsStateInfo(buffer);
			case 0x8cc0d131: return new tl.MsgsAllInfo(buffer);
			case 0x276d3ec6: return new tl.MsgDetailedInfo(buffer);
			case 0x809db6df: return new tl.MsgNewDetailedInfo(buffer);
			case 0x7d861a08: return new tl.MsgResendReq(buffer);
			case 0xbc799737: return new tl.BoolFalse(buffer);
			case 0x997275b5: return new tl.BoolTrue(buffer);
			case 0xc4b9f9bb: return new tl.Error(buffer);
			case 0x56730bcc: return new tl.Null(buffer);
			case 0x7f3b18ea: return new tl.InputPeerEmpty(buffer);
			case 0x7da07ec9: return new tl.InputPeerSelf(buffer);
			case 0x1023dbe8: return new tl.InputPeerContact(buffer);
			case 0x9b447325: return new tl.InputPeerForeign(buffer);
			case 0x179be863: return new tl.InputPeerChat(buffer);
			case 0xb98886cf: return new tl.InputUserEmpty(buffer);
			case 0xf7c1b13f: return new tl.InputUserSelf(buffer);
			case 0x86e94f65: return new tl.InputUserContact(buffer);
			case 0x655e74ff: return new tl.InputUserForeign(buffer);
			case 0xf392b7f4: return new tl.InputPhoneContact(buffer);
			case 0xf52ff27f: return new tl.InputFile(buffer);
			case 0x9664f57f: return new tl.InputMediaEmpty(buffer);
			case 0x2dc53a7d: return new tl.InputMediaUploadedPhoto(buffer);
			case 0x8f2ab2ec: return new tl.InputMediaPhoto(buffer);
			case 0xf9c44144: return new tl.InputMediaGeoPoint(buffer);
			case 0xa6e45987: return new tl.InputMediaContact(buffer);
			case 0x4847d92a: return new tl.InputMediaUploadedVideo(buffer);
			case 0xe628a145: return new tl.InputMediaUploadedThumbVideo(buffer);
			case 0x7f023ae6: return new tl.InputMediaVideo(buffer);
			case 0x1ca48f57: return new tl.InputChatPhotoEmpty(buffer);
			case 0x94254732: return new tl.InputChatUploadedPhoto(buffer);
			case 0xb2e1bf08: return new tl.InputChatPhoto(buffer);
			case 0xe4c123d6: return new tl.InputGeoPointEmpty(buffer);
			case 0xf3b7acc9: return new tl.InputGeoPoint(buffer);
			case 0x1cd7bf0d: return new tl.InputPhotoEmpty(buffer);
			case 0xfb95c6c4: return new tl.InputPhoto(buffer);
			case 0x5508ec75: return new tl.InputVideoEmpty(buffer);
			case 0xee579652: return new tl.InputVideo(buffer);
			case 0x14637196: return new tl.InputFileLocation(buffer);
			case 0x3d0364ec: return new tl.InputVideoFileLocation(buffer);
			case 0xade6b004: return new tl.InputPhotoCropAuto(buffer);
			case 0xd9915325: return new tl.InputPhotoCrop(buffer);
			case 0x770656a8: return new tl.InputAppEvent(buffer);
			case 0x9db1bc6d: return new tl.PeerUser(buffer);
			case 0xbad0e5bb: return new tl.PeerChat(buffer);
			case 0xaa963b05: return new tl.storage.FileUnknown(buffer);
			case 0x7efe0e: return new tl.storage.FileJpeg(buffer);
			case 0xcae1aadf: return new tl.storage.FileGif(buffer);
			case 0xa4f63c0: return new tl.storage.FilePng(buffer);
			case 0x528a0677: return new tl.storage.FileMp3(buffer);
			case 0x4b09ebbc: return new tl.storage.FileMov(buffer);
			case 0x40bc6f52: return new tl.storage.FilePartial(buffer);
			case 0xb3cea0e4: return new tl.storage.FileMp4(buffer);
			case 0x1081464c: return new tl.storage.FileWebp(buffer);
			case 0x7c596b46: return new tl.FileLocationUnavailable(buffer);
			case 0x53d69076: return new tl.FileLocation(buffer);
			case 0x200250ba: return new tl.UserEmpty(buffer);
			case 0x720535ec: return new tl.UserSelf(buffer);
			case 0xf2fb8319: return new tl.UserContact(buffer);
			case 0x22e8ceb0: return new tl.UserRequest(buffer);
			case 0x5214c89d: return new tl.UserForeign(buffer);
			case 0xb29ad7cc: return new tl.UserDeleted(buffer);
			case 0x4f11bae1: return new tl.UserProfilePhotoEmpty(buffer);
			case 0xd559d8c8: return new tl.UserProfilePhoto(buffer);
			case 0x9d05049: return new tl.UserStatusEmpty(buffer);
			case 0xedb93949: return new tl.UserStatusOnline(buffer);
			case 0x8c703f: return new tl.UserStatusOffline(buffer);
			case 0x9ba2d800: return new tl.ChatEmpty(buffer);
			case 0x6e9c9bc7: return new tl.Chat(buffer);
			case 0xfb0ccc41: return new tl.ChatForbidden(buffer);
			case 0x630e61be: return new tl.ChatFull(buffer);
			case 0xc8d7493e: return new tl.ChatParticipant(buffer);
			case 0xfd2bb8a: return new tl.ChatParticipantsForbidden(buffer);
			case 0x7841b415: return new tl.ChatParticipants(buffer);
			case 0x37c1011c: return new tl.ChatPhotoEmpty(buffer);
			case 0x6153276a: return new tl.ChatPhoto(buffer);
			case 0x83e5de54: return new tl.MessageEmpty(buffer);
			case 0x22eb6aba: return new tl.Message(buffer);
			case 0x5f46804: return new tl.MessageForwarded(buffer);
			case 0x9f8d60bb: return new tl.MessageService(buffer);
			case 0x3ded6320: return new tl.MessageMediaEmpty(buffer);
			case 0xc8c45a2a: return new tl.MessageMediaPhoto(buffer);
			case 0xa2d24290: return new tl.MessageMediaVideo(buffer);
			case 0x56e0d474: return new tl.MessageMediaGeo(buffer);
			case 0x5e7d2f39: return new tl.MessageMediaContact(buffer);
			case 0x29632a36: return new tl.MessageMediaUnsupported(buffer);
			case 0xb6aef7b0: return new tl.MessageActionEmpty(buffer);
			case 0xa6638b9a: return new tl.MessageActionChatCreate(buffer);
			case 0xb5a1ce5a: return new tl.MessageActionChatEditTitle(buffer);
			case 0x7fcb13a8: return new tl.MessageActionChatEditPhoto(buffer);
			case 0x95e3fbef: return new tl.MessageActionChatDeletePhoto(buffer);
			case 0x5e3cfc4b: return new tl.MessageActionChatAddUser(buffer);
			case 0xb2ae9b0c: return new tl.MessageActionChatDeleteUser(buffer);
			case 0x214a8cdf: return new tl.Dialog(buffer);
			case 0x2331b22d: return new tl.PhotoEmpty(buffer);
			case 0x22b56751: return new tl.Photo(buffer);
			case 0xe17e23c: return new tl.PhotoSizeEmpty(buffer);
			case 0x77bfb61b: return new tl.PhotoSize(buffer);
			case 0xe9a734fa: return new tl.PhotoCachedSize(buffer);
			case 0xc10658a8: return new tl.VideoEmpty(buffer);
			case 0x5a04a49f: return new tl.Video(buffer);
			case 0x1117dd5f: return new tl.GeoPointEmpty(buffer);
			case 0x2049d70c: return new tl.GeoPoint(buffer);
			case 0xe300cc3b: return new tl.auth.CheckedPhone(buffer);
			case 0x2215bcbd: return new tl.auth.SentCode(buffer);
			case 0xf6b673a4: return new tl.auth.Authorization(buffer);
			case 0xdf969c2d: return new tl.auth.ExportedAuthorization(buffer);
			case 0xb8bc5b0c: return new tl.InputNotifyPeer(buffer);
			case 0x193b4417: return new tl.InputNotifyUsers(buffer);
			case 0x4a95e84e: return new tl.InputNotifyChats(buffer);
			case 0xa429b886: return new tl.InputNotifyAll(buffer);
			case 0xf03064d8: return new tl.InputPeerNotifyEventsEmpty(buffer);
			case 0xe86a2c74: return new tl.InputPeerNotifyEventsAll(buffer);
			case 0x46a2ce98: return new tl.InputPeerNotifySettings(buffer);
			case 0xadd53cb3: return new tl.PeerNotifyEventsEmpty(buffer);
			case 0x6d1ded88: return new tl.PeerNotifyEventsAll(buffer);
			case 0x70a68512: return new tl.PeerNotifySettingsEmpty(buffer);
			case 0x8d5e11ee: return new tl.PeerNotifySettings(buffer);
			case 0xccb03657: return new tl.WallPaper(buffer);
			case 0x771095da: return new tl.UserFull(buffer);
			case 0xf911c994: return new tl.Contact(buffer);
			case 0xd0028438: return new tl.ImportedContact(buffer);
			case 0x561bc879: return new tl.ContactBlocked(buffer);
			case 0xea879f95: return new tl.ContactFound(buffer);
			case 0x3de191a1: return new tl.ContactSuggested(buffer);
			case 0xaa77b873: return new tl.ContactStatus(buffer);
			case 0x3631cf4c: return new tl.ChatLocated(buffer);
			case 0x133421f8: return new tl.contacts.ForeignLinkUnknown(buffer);
			case 0xa7801f47: return new tl.contacts.ForeignLinkRequested(buffer);
			case 0x1bea8ce1: return new tl.contacts.ForeignLinkMutual(buffer);
			case 0xd22a1c60: return new tl.contacts.MyLinkEmpty(buffer);
			case 0x6c69efee: return new tl.contacts.MyLinkRequested(buffer);
			case 0xc240ebd9: return new tl.contacts.MyLinkContact(buffer);
			case 0xeccea3f5: return new tl.contacts.Link(buffer);
			case 0x6f8b8cb2: return new tl.contacts.Contacts(buffer);
			case 0xb74ba9d2: return new tl.contacts.ContactsNotModified(buffer);
			case 0xd1cd0a4c: return new tl.contacts.ImportedContacts(buffer);
			case 0x1c138d15: return new tl.contacts.Blocked(buffer);
			case 0x900802a1: return new tl.contacts.BlockedSlice(buffer);
			case 0x566000e: return new tl.contacts.Found(buffer);
			case 0x5649dcc5: return new tl.contacts.Suggested(buffer);
			case 0x15ba6c40: return new tl.messages.Dialogs(buffer);
			case 0x71e094f3: return new tl.messages.DialogsSlice(buffer);
			case 0x8c718e87: return new tl.messages.Messages(buffer);
			case 0xb446ae3: return new tl.messages.MessagesSlice(buffer);
			case 0x3f4e0648: return new tl.messages.MessageEmpty(buffer);
			case 0xff90c417: return new tl.messages.Message(buffer);
			case 0x969478bb: return new tl.messages.StatedMessages(buffer);
			case 0xd07ae726: return new tl.messages.StatedMessage(buffer);
			case 0xd1f4d35c: return new tl.messages.SentMessage(buffer);
			case 0x40e9002a: return new tl.messages.Chat(buffer);
			case 0x8150cbd8: return new tl.messages.Chats(buffer);
			case 0xe5d7d19c: return new tl.messages.ChatFull(buffer);
			case 0xb7de36f2: return new tl.messages.AffectedHistory(buffer);
			case 0x57e2f66c: return new tl.InputMessagesFilterEmpty(buffer);
			case 0x9609a51c: return new tl.InputMessagesFilterPhotos(buffer);
			case 0x9fc00e65: return new tl.InputMessagesFilterVideo(buffer);
			case 0x56e9f0e4: return new tl.InputMessagesFilterPhotoVideo(buffer);
			case 0x13abdb3: return new tl.UpdateNewMessage(buffer);
			case 0x4e90bfd6: return new tl.UpdateMessageID(buffer);
			case 0xc6649e31: return new tl.UpdateReadMessages(buffer);
			case 0xa92bfe26: return new tl.UpdateDeleteMessages(buffer);
			case 0xd15de04d: return new tl.UpdateRestoreMessages(buffer);
			case 0x6baa8508: return new tl.UpdateUserTyping(buffer);
			case 0x3c46cfe6: return new tl.UpdateChatUserTyping(buffer);
			case 0x7761198: return new tl.UpdateChatParticipants(buffer);
			case 0x1bfbd823: return new tl.UpdateUserStatus(buffer);
			case 0xda22d9ad: return new tl.UpdateUserName(buffer);
			case 0x95313b0c: return new tl.UpdateUserPhoto(buffer);
			case 0x2575bbb9: return new tl.UpdateContactRegistered(buffer);
			case 0x51a48a9a: return new tl.UpdateContactLink(buffer);
			case 0x6f690963: return new tl.UpdateActivation(buffer);
			case 0x8f06529a: return new tl.UpdateNewAuthorization(buffer);
			case 0xa56c2a3e: return new tl.updates.State(buffer);
			case 0x5d75a138: return new tl.updates.DifferenceEmpty(buffer);
			case 0xf49ca0: return new tl.updates.Difference(buffer);
			case 0xa8fb1981: return new tl.updates.DifferenceSlice(buffer);
			case 0xe317af7e: return new tl.UpdatesTooLong(buffer);
			case 0xd3f45784: return new tl.UpdateShortMessage(buffer);
			case 0x2b2fbd4e: return new tl.UpdateShortChatMessage(buffer);
			case 0x78d4dec1: return new tl.UpdateShort(buffer);
			case 0x725b04c3: return new tl.UpdatesCombined(buffer);
			case 0x74ae4240: return new tl.Updates(buffer);
			case 0x8dca6aa5: return new tl.photos.Photos(buffer);
			case 0x15051f54: return new tl.photos.PhotosSlice(buffer);
			case 0x20212ca8: return new tl.photos.Photo(buffer);
			case 0x96a18d5: return new tl.upload.File(buffer);
			case 0x2ec2a43c: return new tl.DcOption(buffer);
			case 0x232d5905: return new tl.Config(buffer);
			case 0x8e1a1775: return new tl.NearestDc(buffer);
			case 0x18cb9f78: return new tl.help.InviteText(buffer);
			case 0x3e74f5c6: return new tl.messages.StatedMessagesLinks(buffer);
			case 0xa9af2881: return new tl.messages.StatedMessageLink(buffer);
			case 0xe9db4a3f: return new tl.messages.SentMessageLink(buffer);
			case 0x74d456fa: return new tl.InputGeoChat(buffer);
			case 0x4d8ddec8: return new tl.InputNotifyGeoChatPeer(buffer);
			case 0x75eaea5a: return new tl.GeoChat(buffer);
			case 0x60311a9b: return new tl.GeoChatMessageEmpty(buffer);
			case 0x4505f8e1: return new tl.GeoChatMessage(buffer);
			case 0xd34fa24e: return new tl.GeoChatMessageService(buffer);
			case 0x17b1578b: return new tl.geochats.StatedMessage(buffer);
			case 0x48feb267: return new tl.geochats.Located(buffer);
			case 0xd1526db1: return new tl.geochats.Messages(buffer);
			case 0xbc5863e8: return new tl.geochats.MessagesSlice(buffer);
			case 0x6f038ebc: return new tl.MessageActionGeoChatCreate(buffer);
			case 0xc7d53de: return new tl.MessageActionGeoChatCheckin(buffer);
			case 0x5a68e3f7: return new tl.UpdateNewGeoChatMessage(buffer);
			case 0x63117f24: return new tl.WallPaperSolid(buffer);
			case 0x12bcbd9a: return new tl.UpdateNewEncryptedMessage(buffer);
			case 0x1710f156: return new tl.UpdateEncryptedChatTyping(buffer);
			case 0xb4a2e88d: return new tl.UpdateEncryption(buffer);
			case 0x38fe25b7: return new tl.UpdateEncryptedMessagesRead(buffer);
			case 0xab7ec0a0: return new tl.EncryptedChatEmpty(buffer);
			case 0x3bf703dc: return new tl.EncryptedChatWaiting(buffer);
			case 0xfda9a7b7: return new tl.EncryptedChatRequested(buffer);
			case 0x6601d14f: return new tl.EncryptedChat(buffer);
			case 0x13d6dd27: return new tl.EncryptedChatDiscarded(buffer);
			case 0xf141b5e1: return new tl.InputEncryptedChat(buffer);
			case 0xc21f497e: return new tl.EncryptedFileEmpty(buffer);
			case 0x4a70994c: return new tl.EncryptedFile(buffer);
			case 0x1837c364: return new tl.InputEncryptedFileEmpty(buffer);
			case 0x64bd0306: return new tl.InputEncryptedFileUploaded(buffer);
			case 0x5a17b5e5: return new tl.InputEncryptedFile(buffer);
			case 0xf5235d55: return new tl.InputEncryptedFileLocation(buffer);
			case 0xed18c118: return new tl.EncryptedMessage(buffer);
			case 0x23734b06: return new tl.EncryptedMessageService(buffer);
			case 0x99a438cf: return new tl.DecryptedMessageLayer(buffer);
			case 0x1f814f1f: return new tl.DecryptedMessage(buffer);
			case 0xaa48327d: return new tl.DecryptedMessageService(buffer);
			case 0x89f5c4a: return new tl.DecryptedMessageMediaEmpty(buffer);
			case 0x32798a8c: return new tl.DecryptedMessageMediaPhoto(buffer);
			case 0x4cee6ef3: return new tl.DecryptedMessageMediaVideo(buffer);
			case 0x35480a59: return new tl.DecryptedMessageMediaGeoPoint(buffer);
			case 0x588a0a97: return new tl.DecryptedMessageMediaContact(buffer);
			case 0xa1733aec: return new tl.DecryptedMessageActionSetMessageTTL(buffer);
			case 0xc0e24635: return new tl.messages.DhConfigNotModified(buffer);
			case 0x2c221edd: return new tl.messages.DhConfig(buffer);
			case 0x560f8935: return new tl.messages.SentEncryptedMessage(buffer);
			case 0x9493ff32: return new tl.messages.SentEncryptedFile(buffer);
			case 0xcb9f372d: return new tl.InvokeAfterMsg(buffer);
			case 0x3dc4b4f0: return new tl.InvokeAfterMsgs(buffer);
			case 0x53835315: return new tl.InvokeWithLayer1(buffer);
			case 0x3fc12e08: return new tl.InitConnection(buffer);
			case 0x6fe51dfb: return new tl.auth.CheckPhone(buffer);
			case 0x768d5f4d: return new tl.auth.SendCode(buffer);
			case 0x3c51564: return new tl.auth.SendCall(buffer);
			case 0x1b067634: return new tl.auth.SignUp(buffer);
			case 0xbcd51581: return new tl.auth.SignIn(buffer);
			case 0x5717da40: return new tl.auth.LogOut(buffer);
			case 0x9fab0d1a: return new tl.auth.ResetAuthorizations(buffer);
			case 0x771c1d97: return new tl.auth.SendInvites(buffer);
			case 0xe5bfffcd: return new tl.auth.ExportAuthorization(buffer);
			case 0xe3ef9613: return new tl.auth.ImportAuthorization(buffer);
			case 0x446c712c: return new tl.account.RegisterDevice(buffer);
			case 0x65c55b40: return new tl.account.UnregisterDevice(buffer);
			case 0x84be5b93: return new tl.account.UpdateNotifySettings(buffer);
			case 0x12b3ad31: return new tl.account.GetNotifySettings(buffer);
			case 0xdb7e1747: return new tl.account.ResetNotifySettings(buffer);
			case 0xf0888d68: return new tl.account.UpdateProfile(buffer);
			case 0x6628562c: return new tl.account.UpdateStatus(buffer);
			case 0xc04cfac2: return new tl.account.GetWallPapers(buffer);
			case 0xd91a548: return new tl.users.GetUsers(buffer);
			case 0xca30a5b1: return new tl.users.GetFullUser(buffer);
			case 0xc4a353ee: return new tl.contacts.GetStatuses(buffer);
			case 0x22c6aa08: return new tl.contacts.GetContacts(buffer);
			case 0xda30b32d: return new tl.contacts.ImportContacts(buffer);
			case 0x11f812d8: return new tl.contacts.Search(buffer);
			case 0xcd773428: return new tl.contacts.GetSuggested(buffer);
			case 0x8e953744: return new tl.contacts.DeleteContact(buffer);
			case 0x59ab389e: return new tl.contacts.DeleteContacts(buffer);
			case 0x332b49fc: return new tl.contacts.Block(buffer);
			case 0xe54100bd: return new tl.contacts.Unblock(buffer);
			case 0xf57c350f: return new tl.contacts.GetBlocked(buffer);
			case 0x4222fa74: return new tl.messages.GetMessages(buffer);
			case 0xeccf1df6: return new tl.messages.GetDialogs(buffer);
			case 0x92a1df2f: return new tl.messages.GetHistory(buffer);
			case 0x7e9f2ab: return new tl.messages.Search(buffer);
			case 0xb04f2510: return new tl.messages.ReadHistory(buffer);
			case 0xf4f8fb61: return new tl.messages.DeleteHistory(buffer);
			case 0x14f2dd0a: return new tl.messages.DeleteMessages(buffer);
			case 0x395f9d7e: return new tl.messages.RestoreMessages(buffer);
			case 0x28abcb68: return new tl.messages.ReceivedMessages(buffer);
			case 0x719839e9: return new tl.messages.SetTyping(buffer);
			case 0x4cde0aab: return new tl.messages.SendMessage(buffer);
			case 0xa3c85d76: return new tl.messages.SendMedia(buffer);
			case 0x514cd10f: return new tl.messages.ForwardMessages(buffer);
			case 0x3c6aa187: return new tl.messages.GetChats(buffer);
			case 0x3b831c66: return new tl.messages.GetFullChat(buffer);
			case 0xb4bc68b5: return new tl.messages.EditChatTitle(buffer);
			case 0xd881821d: return new tl.messages.EditChatPhoto(buffer);
			case 0x2ee9ee9e: return new tl.messages.AddChatUser(buffer);
			case 0xc3c5cd23: return new tl.messages.DeleteChatUser(buffer);
			case 0x419d9aee: return new tl.messages.CreateChat(buffer);
			case 0xedd4882a: return new tl.updates.GetState(buffer);
			case 0xa041495: return new tl.updates.GetDifference(buffer);
			case 0xeef579a0: return new tl.photos.UpdateProfilePhoto(buffer);
			case 0xd50f9c88: return new tl.photos.UploadProfilePhoto(buffer);
			case 0xb304a621: return new tl.upload.SaveFilePart(buffer);
			case 0xe3a6cfb5: return new tl.upload.GetFile(buffer);
			case 0xc4f9186b: return new tl.help.GetConfig(buffer);
			case 0x1fb33026: return new tl.help.GetNearestDc(buffer);
			case 0x6f02f748: return new tl.help.SaveAppLog(buffer);
			case 0xa4a95186: return new tl.help.GetInviteText(buffer);
			case 0xb7ee553c: return new tl.photos.GetUserPhotos(buffer);
			case 0x289dd1f6: return new tl.InvokeWithLayer2(buffer);
			case 0x3f3f4f2: return new tl.messages.ForwardMessage(buffer);
			case 0x41bb0972: return new tl.messages.SendBroadcast(buffer);
			case 0xb7475268: return new tl.InvokeWithLayer3(buffer);
			case 0x7f192d8f: return new tl.geochats.GetLocated(buffer);
			case 0xe1427e6f: return new tl.geochats.GetRecents(buffer);
			case 0x55b3e8fb: return new tl.geochats.Checkin(buffer);
			case 0x6722dd6f: return new tl.geochats.GetFullChat(buffer);
			case 0x4c8e2273: return new tl.geochats.EditChatTitle(buffer);
			case 0x35d81a95: return new tl.geochats.EditChatPhoto(buffer);
			case 0xcfcdc44d: return new tl.geochats.Search(buffer);
			case 0xb53f7a68: return new tl.geochats.GetHistory(buffer);
			case 0x8b8a729: return new tl.geochats.SetTyping(buffer);
			case 0x61b0044: return new tl.geochats.SendMessage(buffer);
			case 0xb8f0deff: return new tl.geochats.SendMedia(buffer);
			case 0xe092e16: return new tl.geochats.CreateGeoChat(buffer);
			case 0xdea0d430: return new tl.InvokeWithLayer4(buffer);
			case 0x417a57ae: return new tl.InvokeWithLayer5(buffer);
			case 0x3a64d54d: return new tl.InvokeWithLayer6(buffer);
			case 0xa5be56d3: return new tl.InvokeWithLayer7(buffer);
			case 0x26cf8950: return new tl.messages.GetDhConfig(buffer);
			case 0xf64daf43: return new tl.messages.RequestEncryption(buffer);
			case 0x3dbc0415: return new tl.messages.AcceptEncryption(buffer);
			case 0xedd923c5: return new tl.messages.DiscardEncryption(buffer);
			case 0x791451ed: return new tl.messages.SetEncryptedTyping(buffer);
			case 0x7f4b690a: return new tl.messages.ReadEncryptedHistory(buffer);
			case 0xa9776773: return new tl.messages.SendEncrypted(buffer);
			case 0x9a901b66: return new tl.messages.SendEncryptedFile(buffer);
			case 0x32d439a4: return new tl.messages.SendEncryptedService(buffer);
			case 0x55a5bb66: return new tl.messages.ReceivedQueue(buffer);
			case 0xe9abd9fd: return new tl.InvokeWithLayer8(buffer);

		}
		
		
		System.err.println("Unable to decode TLObject with constructor 0x" + Integer.toHexString(number) + ", previous 0x" + Integer.toHexString(prevNum));
		//Log.e(TAG, "Buffer dump (error at " + buffer.position() + "): " + toString(buffer));
		return null;
	}
	
	public static TLObject read(byte[] buffer) {
	  ByteBuffer buf = ByteBuffer.wrap(buffer);
	  buf.order(ByteOrder.LITTLE_ENDIAN);
	  return read(buf);
	}
	
	public static TLObject read(String base64) {
	  return read(Base64.decodeFast(base64));
	}
	
	// Read methods for simple types (unboxed)	
	public static byte[] readString(ByteBuffer buffer) {
		int len = buffer.get();
		byte[] value = null;
		
    if (len == -2) {
    	len = (((int) buffer.get()) & 0xFF) | ((((int) buffer.get()) & 0xFF) << 8) | ((((int) buffer.get()) & 0xFF) << 16);
    	value = new byte[len];
	    buffer.get(value);
	    
	    int padlen = (4 - len % 4) % 4;
	    byte[] pad = new byte[padlen];
	    buffer.get(pad);
    } else {
      len &= 0xFF;
    	value = new byte[len];
	    buffer.get(value);
	    
	    int padlen = (4 - (len + 1) % 4) % 4;
	    byte[] pad = new byte[padlen];
	    buffer.get(pad);
    }
    return value;
	}

	public static byte[] readInt128(ByteBuffer buffer) {
		byte[] value = new byte[16];
		buffer.get(value);
		return value;
	}
	
	public static byte[] readInt256(ByteBuffer buffer) {
		byte[] value = new byte[32];
		buffer.get(value);
		return value;
	}

	@SuppressWarnings("unchecked")
	public static <T extends TLObject> T[] readVector(ByteBuffer buffer, boolean boxed, T[] a) {
		if (boxed) {
			buffer.getInt();
		}
		
		int size = buffer.getInt();
		ArrayList<T> value = new ArrayList<T>(size);
		for (int i = 0; i < size; i++) {
			value.add((T) TL.read(buffer));
		}
		return value.toArray(a);
	}
	
	//TODO: remove this single exception (now only TransportMessage can be inner unboxed type)
	public static TransportMessage[] readVectorMessage(ByteBuffer buffer, boolean boxed) {
		if (boxed) {
			buffer.getInt();
		}
		TransportMessage[] value = new TransportMessage[buffer.getInt()];
		for (int i = 0; i < value.length; i++) {
			value[i] = new TransportMessage(buffer);
		}
		return value;
	}
	
	public static int[] readVectorInt(ByteBuffer buffer, boolean boxed) {
		if (boxed) {
			buffer.getInt(); // check if it's actually vector long
		}
		
		int[] value = new int[buffer.getInt()];
		for (int i = 0; i < value.length; i++) {
			value[i] = buffer.getInt();
		}
		return value;
	}
	
	public static long[] readVectorLong(ByteBuffer buffer, boolean boxed) {
		if (boxed) {
			buffer.getInt(); // check if it's actually vector long
		}
		
		long[] value = new long[buffer.getInt()];
		for (int i = 0; i < value.length; i++) {
			value[i] = buffer.getLong();
		}
		return value;
	}
	
	public static String[] readVectorString(ByteBuffer buffer, boolean boxed) {
		if (boxed) {
			buffer.getInt(); // check if it's actually vector long
		}
		
		String[] value = new String[buffer.getInt()];
		for (int i = 0; i < value.length; i++) {
		  try { value[i] = new String(TL.readString(buffer), "UTF8"); } catch (Exception e) { };
		}
		return value;
	}
	
	// Write methods for simple types
	
	public static void writeString(ByteBuffer buffer, byte[] value, boolean boxed) {
		if (boxed) {
			buffer.putInt(CRC32("string ? = String"));
		}
		
		if (value.length <= 253) {
	    int padlen = (4 - (value.length + 1) % 4) % 4;
	    buffer.put((byte) value.length);
	    buffer.put(value);
	    byte[] pad = new byte[padlen];
	    buffer.put(pad);
  	} else {
  		int padlen = (4 - value.length % 4) % 4;
  		buffer.putInt(0xFE + (value.length << 8));
	    buffer.put(value);
	    byte[] pad = new byte[padlen];
	    buffer.put(pad);
  	}
	}

	public static void writeVector(ByteBuffer buffer, TLObject[] value, boolean outerBoxed, boolean innerBoxed) {
		if (outerBoxed) {
			//buffer.putInt(0xa351ae8e);
		  buffer.putInt(0x1cb5c415);
		}
		
		buffer.putInt(value.length);
		for (int i = 0; i < value.length; i++) {
			value[i].writeTo(buffer, innerBoxed);
		}
	}
	
	public static void writeVector(ByteBuffer buffer, int[] value, boolean outerBoxed, boolean innerBoxed) {
		if (outerBoxed) {
			buffer.putInt(CRC32("vector # [ int ] = Vector int"));
		}
		
		buffer.putInt(value.length);
		for (int i = 0; i < value.length; i++) {
			if (innerBoxed) {
				buffer.putInt(CRC32("int ? = Int"));
			}
			
			buffer.putInt(value[i]);
		}
	}

	public static void writeVector(ByteBuffer buffer, long[] value, boolean outerBoxed, boolean innerBoxed) {
		if (outerBoxed) {
			buffer.putInt(CRC32("vector # [ long ] = Vector long"));
		}
		
		buffer.putInt(value.length);
		for (int i = 0; i < value.length; i++) {
			if (innerBoxed) {
				buffer.putInt(CRC32("long ? = Long"));
			}
			
			buffer.putLong(value[i]);
		}
	}
	
	public static void writeVector(ByteBuffer buffer, String[] value, boolean outerBoxed, boolean innerBoxed) {
		if (outerBoxed) {
			buffer.putInt(CRC32("vector # [ string ] = Vector string"));
		}
		
		buffer.putInt(value.length);
		for (int i = 0; i < value.length; i++) {
			writeString(buffer, value[i].getBytes(), innerBoxed);
		}
	}
	
	// Length methods for simple types
	
	public static int length(byte[] value) {
		int len = value.length + (value.length < 253 ? 1 : 4);
		
		while (len % 4 != 0) len++;
		return len;
	}
	
	public static int length(TLObject[] value, boolean boxed) {
		int len = 0;
		for (TLObject object : value) {
			len += object.length() + (boxed ? 4 : 0);
		}
		return len;
	}	
	
	public static int length(TLObject[] value) {
	  return length(value, false);
  } 

	public static int length(String[] value) {
		int len = 0;
		for (String object : value) {
			len += TL.length(object.getBytes());
		}
		return len;
	}

	// toString methods for simple types
	public static String toString(TLObject[] value) {
		String str = "";
		for (TLObject object : value) {
			if (str.length() > 0) str += ", ";
			str += object;
		}
		return "[" + str + "]";
	}
	
	public static String toString(ByteBuffer value) {
    String str = "";
    for (int i = 0; i < value.limit(); i++) {
      if (i % 16 > 0) str += " "; else if (i > 0) str += "\n";
      str += String.format("%02x", value.get(i));
    }
    return "\n" + str + "\n";
  }

	public static String toString(byte[] value) {
		String str = "";
		for (int i = 0; i < value.length; i++) {
			if (i % 16 > 0) str += " "; else if (i > 0) str += "\n";
			str += String.format("%02x", value[i]);
		}
		return "\n" + str + "\n";
	}

	public static String toString(int[] value) {
		String str = "";
		for (int object : value) {
			if (str.length() > 0) str += ", ";
			str += object;
		}
		return "[" + str + "]";
	}

	public static String toString(long[] value) {
		String str = "";
		for (long object : value) {
			if (str.length() > 0) str += ", ";
			str += String.format("%016x", object);
		}
		return "[" + str + "]";
	}

	public static String toString(String[] value) {
		String str = "";
		for (String object : value) {
			if (str.length() > 0) str += ", ";
			str += "\"" + object + "\"";
		}
		return "[" + str + "]";
	}	
}
