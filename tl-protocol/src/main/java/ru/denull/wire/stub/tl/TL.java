package ru.denull.wire.stub.tl;

import ru.denull.wire.lib.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import static ru.denull.wire.lib.CryptoUtils.CRC32;

public class TL {
    private static final String TAG = "TL";
    private static int lastNum = 0, prevNum = 0;

    // Read method (boxed)
    public static TLObject read(ByteBuffer buffer) throws Exception {
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
            case 0x60469778:
                return new ReqPq(buffer);
            case 0x05162463:
                return new ResPQ(buffer);
            case 0xd712e4be:
                return new ReqDHParams(buffer);
            case 0x83c95aec:
                return new PQInnerData(buffer);
            case 0x79cb045d:
                return new ServerDHParamsFail(buffer);
            case 0xd0e8075c:
                return new ServerDHParamsOk(buffer);
            case 0xb5890dba:
                return new ServerDHInnerData(buffer);
            case 0xf5045f1f:
                return new SetClientDHParams(buffer);
            case 0x6643b654:
                return new ClientDHInnerData(buffer);
            case 0x3bcbf734:
                return new DhGenOk(buffer);
            case 0x46dc1fb9:
                return new DhGenRetry(buffer);
            case 0xa69dae02:
                return new DhGenFail(buffer);
            case 0xf35c6d01:
                return new RpcResult(buffer);
            case 0x2144ca19:
                return new RpcError(buffer);
            case 0x58e4a740:
                return new RpcDropAnswer(buffer);
            case 0x5e2ad36e:
                return new RpcAnswerUnknown(buffer);
            case 0xcd78e586:
                return new RpcAnswerDroppedRunning(buffer);
            case 0xa43ad8b7:
                return new RpcAnswerDropped(buffer);
            case 0xb921bd04:
                return new GetFutureSalts(buffer);
            case 0x0949d9dc:
                return new FutureSalt(buffer);
            case 0xae500895:
                return new FutureSalts(buffer);
            case 0x7abe77ec:
                return new Ping(buffer);
            case 0x347773c5:
                return new Pong(buffer);
            case 0xe22045fc:
                return new DestroySessionOk(buffer);
            case 0x62d350c9:
                return new DestroySessionNone(buffer);
            case 0x9ec20908:
                return new NewSessionCreated(buffer);
            case 0x73f1f8dc:
                return new MsgContainer(buffer);
            case 0xe06046b2:
                return new MsgCopy(buffer);
            case 0x9299359f:
                return new HttpWait(buffer);
            case 0x62d6b459:
                return new MsgsAck(buffer);
            case 0xa7eff811:
                return new BadMsgNotification(buffer);
            case 0xedab447b:
                return new BadServerSalt(buffer);
            case 0xda69fb52:
                return new MsgsStateReq(buffer);
            case 0x04deb57d:
                return new MsgsStateInfo(buffer);
            case 0x8cc0d131:
                return new MsgsAllInfo(buffer);
            case 0x276d3ec6:
                return new MsgDetailedInfo(buffer);
            case 0x809db6df:
                return new MsgNewDetailedInfo(buffer);
            case 0x7d861a08:
                return new MsgResendReq(buffer);
            case 0xbc799737:
                return new BoolFalse(buffer);
            case 0x997275b5:
                return new BoolTrue(buffer);
            case 0xc4b9f9bb:
                return new Error(buffer);
            case 0x56730bcc:
                return new Null(buffer);
            case 0x7f3b18ea:
                return new InputPeerEmpty(buffer);
            case 0x7da07ec9:
                return new InputPeerSelf(buffer);
            case 0x1023dbe8:
                return new InputPeerContact(buffer);
            case 0x9b447325:
                return new InputPeerForeign(buffer);
            case 0x179be863:
                return new InputPeerChat(buffer);
            case 0xb98886cf:
                return new InputUserEmpty(buffer);
            case 0xf7c1b13f:
                return new InputUserSelf(buffer);
            case 0x86e94f65:
                return new InputUserContact(buffer);
            case 0x655e74ff:
                return new InputUserForeign(buffer);
            case 0xf392b7f4:
                return new InputPhoneContact(buffer);
            case 0xf52ff27f:
                return new InputFile(buffer);
            case 0x9664f57f:
                return new InputMediaEmpty(buffer);
            case 0x2dc53a7d:
                return new InputMediaUploadedPhoto(buffer);
            case 0x8f2ab2ec:
                return new InputMediaPhoto(buffer);
            case 0xf9c44144:
                return new InputMediaGeoPoint(buffer);
            case 0xa6e45987:
                return new InputMediaContact(buffer);
            case 0x4847d92a:
                return new InputMediaUploadedVideo(buffer);
            case 0xe628a145:
                return new InputMediaUploadedThumbVideo(buffer);
            case 0x7f023ae6:
                return new InputMediaVideo(buffer);
            case 0x1ca48f57:
                return new InputChatPhotoEmpty(buffer);
            case 0x94254732:
                return new InputChatUploadedPhoto(buffer);
            case 0xb2e1bf08:
                return new InputChatPhoto(buffer);
            case 0xe4c123d6:
                return new InputGeoPointEmpty(buffer);
            case 0xf3b7acc9:
                return new InputGeoPoint(buffer);
            case 0x1cd7bf0d:
                return new InputPhotoEmpty(buffer);
            case 0xfb95c6c4:
                return new InputPhoto(buffer);
            case 0x5508ec75:
                return new InputVideoEmpty(buffer);
            case 0xee579652:
                return new InputVideo(buffer);
            case 0x14637196:
                return new InputFileLocation(buffer);
            case 0x3d0364ec:
                return new InputVideoFileLocation(buffer);
            case 0xade6b004:
                return new InputPhotoCropAuto(buffer);
            case 0xd9915325:
                return new InputPhotoCrop(buffer);
            case 0x770656a8:
                return new InputAppEvent(buffer);
            case 0x9db1bc6d:
                return new PeerUser(buffer);
            case 0xbad0e5bb:
                return new PeerChat(buffer);
            case 0xaa963b05:
                return new ru.denull.wire.stub.tl.storage.FileUnknown(buffer);
            case 0x7efe0e:
                return new ru.denull.wire.stub.tl.storage.FileJpeg(buffer);
            case 0xcae1aadf:
                return new ru.denull.wire.stub.tl.storage.FileGif(buffer);
            case 0xa4f63c0:
                return new ru.denull.wire.stub.tl.storage.FilePng(buffer);
            case 0x528a0677:
                return new ru.denull.wire.stub.tl.storage.FileMp3(buffer);
            case 0x4b09ebbc:
                return new ru.denull.wire.stub.tl.storage.FileMov(buffer);
            case 0x40bc6f52:
                return new ru.denull.wire.stub.tl.storage.FilePartial(buffer);
            case 0xb3cea0e4:
                return new ru.denull.wire.stub.tl.storage.FileMp4(buffer);
            case 0x1081464c:
                return new ru.denull.wire.stub.tl.storage.FileWebp(buffer);
            case 0x7c596b46:
                return new FileLocationUnavailable(buffer);
            case 0x53d69076:
                return new FileLocation(buffer);
            case 0x200250ba:
                return new UserEmpty(buffer);
            case 0x720535ec:
                return new UserSelf(buffer);
            case 0xf2fb8319:
                return new UserContact(buffer);
            case 0x22e8ceb0:
                return new UserRequest(buffer);
            case 0x5214c89d:
                return new UserForeign(buffer);
            case 0xb29ad7cc:
                return new UserDeleted(buffer);
            case 0x4f11bae1:
                return new UserProfilePhotoEmpty(buffer);
            case 0xd559d8c8:
                return new UserProfilePhoto(buffer);
            case 0x9d05049:
                return new UserStatusEmpty(buffer);
            case 0xedb93949:
                return new UserStatusOnline(buffer);
            case 0x8c703f:
                return new UserStatusOffline(buffer);
            case 0x9ba2d800:
                return new ChatEmpty(buffer);
            case 0x6e9c9bc7:
                return new ru.denull.wire.stub.tl.messages.Chat(buffer);
            case 0xfb0ccc41:
                return new ChatForbidden(buffer);
            case 0x630e61be:
                return new ru.denull.wire.stub.tl.messages.ChatFull(buffer);
            case 0xc8d7493e:
                return new ChatParticipant(buffer);
            case 0xfd2bb8a:
                return new ChatParticipantsForbidden(buffer);
            case 0x7841b415:
                return new ChatParticipants(buffer);
            case 0x37c1011c:
                return new ChatPhotoEmpty(buffer);
            case 0x6153276a:
                return new ChatPhoto(buffer);
            case 0x83e5de54:
                return new ru.denull.wire.stub.tl.messages.MessageEmpty(buffer);
            case 0x22eb6aba:
                return new ru.denull.wire.stub.tl.messages.Message(buffer);
            case 0x5f46804:
                return new MessageForwarded(buffer);
            case 0x9f8d60bb:
                return new MessageService(buffer);
            case 0x3ded6320:
                return new MessageMediaEmpty(buffer);
            case 0xc8c45a2a:
                return new MessageMediaPhoto(buffer);
            case 0xa2d24290:
                return new MessageMediaVideo(buffer);
            case 0x56e0d474:
                return new MessageMediaGeo(buffer);
            case 0x5e7d2f39:
                return new MessageMediaContact(buffer);
            case 0x29632a36:
                return new MessageMediaUnsupported(buffer);
            case 0xb6aef7b0:
                return new MessageActionEmpty(buffer);
            case 0xa6638b9a:
                return new MessageActionChatCreate(buffer);
            case 0xb5a1ce5a:
                return new MessageActionChatEditTitle(buffer);
            case 0x7fcb13a8:
                return new MessageActionChatEditPhoto(buffer);
            case 0x95e3fbef:
                return new MessageActionChatDeletePhoto(buffer);
            case 0x5e3cfc4b:
                return new MessageActionChatAddUser(buffer);
            case 0xb2ae9b0c:
                return new MessageActionChatDeleteUser(buffer);
            case 0x214a8cdf:
                return new Dialog(buffer);
            case 0x2331b22d:
                return new PhotoEmpty(buffer);
            case 0x22b56751:
                return new ru.denull.wire.stub.tl.photos.Photo(buffer);
            case 0xe17e23c:
                return new PhotoSizeEmpty(buffer);
            case 0x77bfb61b:
                return new PhotoSize(buffer);
            case 0xe9a734fa:
                return new PhotoCachedSize(buffer);
            case 0xc10658a8:
                return new VideoEmpty(buffer);
            case 0x5a04a49f:
                return new Video(buffer);
            case 0x1117dd5f:
                return new GeoPointEmpty(buffer);
            case 0x2049d70c:
                return new GeoPoint(buffer);
            case 0xe300cc3b:
                return new ru.denull.wire.stub.tl.auth.CheckedPhone(buffer);
            case 0x2215bcbd:
                return new ru.denull.wire.stub.tl.auth.SentCode(buffer);
            case 0xf6b673a4:
                return new ru.denull.wire.stub.tl.auth.Authorization(buffer);
            case 0xdf969c2d:
                return new ru.denull.wire.stub.tl.auth.ExportedAuthorization(buffer);
            case 0xb8bc5b0c:
                return new InputNotifyPeer(buffer);
            case 0x193b4417:
                return new InputNotifyUsers(buffer);
            case 0x4a95e84e:
                return new InputNotifyChats(buffer);
            case 0xa429b886:
                return new InputNotifyAll(buffer);
            case 0xf03064d8:
                return new InputPeerNotifyEventsEmpty(buffer);
            case 0xe86a2c74:
                return new InputPeerNotifyEventsAll(buffer);
            case 0x46a2ce98:
                return new InputPeerNotifySettings(buffer);
            case 0xadd53cb3:
                return new PeerNotifyEventsEmpty(buffer);
            case 0x6d1ded88:
                return new PeerNotifyEventsAll(buffer);
            case 0x70a68512:
                return new PeerNotifySettingsEmpty(buffer);
            case 0x8d5e11ee:
                return new PeerNotifySettings(buffer);
            case 0xccb03657:
                return new WallPaper(buffer);
            case 0x771095da:
                return new UserFull(buffer);
            case 0xf911c994:
                return new Contact(buffer);
            case 0xd0028438:
                return new ImportedContact(buffer);
            case 0x561bc879:
                return new ContactBlocked(buffer);
            case 0xea879f95:
                return new ContactFound(buffer);
            case 0x3de191a1:
                return new ContactSuggested(buffer);
            case 0xaa77b873:
                return new ContactStatus(buffer);
            case 0x3631cf4c:
                return new ChatLocated(buffer);
            case 0x133421f8:
                return new ru.denull.wire.stub.tl.contacts.ForeignLinkUnknown(buffer);
            case 0xa7801f47:
                return new ru.denull.wire.stub.tl.contacts.ForeignLinkRequested(buffer);
            case 0x1bea8ce1:
                return new ru.denull.wire.stub.tl.contacts.ForeignLinkMutual(buffer);
            case 0xd22a1c60:
                return new ru.denull.wire.stub.tl.contacts.MyLinkEmpty(buffer);
            case 0x6c69efee:
                return new ru.denull.wire.stub.tl.contacts.MyLinkRequested(buffer);
            case 0xc240ebd9:
                return new ru.denull.wire.stub.tl.contacts.MyLinkContact(buffer);
            case 0xeccea3f5:
                return new ru.denull.wire.stub.tl.contacts.Link(buffer);
            case 0x6f8b8cb2:
                return new ru.denull.wire.stub.tl.contacts.Contacts(buffer);
            case 0xb74ba9d2:
                return new ru.denull.wire.stub.tl.contacts.ContactsNotModified(buffer);
            case 0xd1cd0a4c:
                return new ru.denull.wire.stub.tl.contacts.ImportedContacts(buffer);
            case 0x1c138d15:
                return new ru.denull.wire.stub.tl.contacts.Blocked(buffer);
            case 0x900802a1:
                return new ru.denull.wire.stub.tl.contacts.BlockedSlice(buffer);
            case 0x566000e:
                return new ru.denull.wire.stub.tl.contacts.Found(buffer);
            case 0x5649dcc5:
                return new ru.denull.wire.stub.tl.contacts.Suggested(buffer);
            case 0x15ba6c40:
                return new ru.denull.wire.stub.tl.messages.Dialogs(buffer);
            case 0x71e094f3:
                return new ru.denull.wire.stub.tl.messages.DialogsSlice(buffer);
            case 0x8c718e87:
                return new ru.denull.wire.stub.tl.messages.Messages(buffer);
            case 0xb446ae3:
                return new ru.denull.wire.stub.tl.messages.MessagesSlice(buffer);
            case 0x3f4e0648:
                return new ru.denull.wire.stub.tl.messages.MessageEmpty(buffer);
            case 0xff90c417:
                return new ru.denull.wire.stub.tl.messages.Message(buffer);
            case 0x969478bb:
                return new ru.denull.wire.stub.tl.messages.StatedMessages(buffer);
            case 0xd07ae726:
                return new ru.denull.wire.stub.tl.messages.StatedMessage(buffer);
            case 0xd1f4d35c:
                return new ru.denull.wire.stub.tl.messages.SentMessage(buffer);
            case 0x40e9002a:
                return new ru.denull.wire.stub.tl.messages.Chat(buffer);
            case 0x8150cbd8:
                return new ru.denull.wire.stub.tl.messages.Chats(buffer);
            case 0xe5d7d19c:
                return new ru.denull.wire.stub.tl.messages.ChatFull(buffer);
            case 0xb7de36f2:
                return new ru.denull.wire.stub.tl.messages.AffectedHistory(buffer);
            case 0x57e2f66c:
                return new InputMessagesFilterEmpty(buffer);
            case 0x9609a51c:
                return new InputMessagesFilterPhotos(buffer);
            case 0x9fc00e65:
                return new InputMessagesFilterVideo(buffer);
            case 0x56e9f0e4:
                return new InputMessagesFilterPhotoVideo(buffer);
            case 0x13abdb3:
                return new UpdateNewMessage(buffer);
            case 0x4e90bfd6:
                return new UpdateMessageID(buffer);
            case 0xc6649e31:
                return new UpdateReadMessages(buffer);
            case 0xa92bfe26:
                return new UpdateDeleteMessages(buffer);
            case 0xd15de04d:
                return new UpdateRestoreMessages(buffer);
            case 0x6baa8508:
                return new UpdateUserTyping(buffer);
            case 0x3c46cfe6:
                return new UpdateChatUserTyping(buffer);
            case 0x7761198:
                return new UpdateChatParticipants(buffer);
            case 0x1bfbd823:
                return new UpdateUserStatus(buffer);
            case 0xda22d9ad:
                return new UpdateUserName(buffer);
            case 0x95313b0c:
                return new UpdateUserPhoto(buffer);
            case 0x2575bbb9:
                return new UpdateContactRegistered(buffer);
            case 0x51a48a9a:
                return new UpdateContactLink(buffer);
            case 0x6f690963:
                return new UpdateActivation(buffer);
            case 0x8f06529a:
                return new UpdateNewAuthorization(buffer);
            case 0xa56c2a3e:
                return new ru.denull.wire.stub.tl.updates.State(buffer);
            case 0x5d75a138:
                return new ru.denull.wire.stub.tl.updates.DifferenceEmpty(buffer);
            case 0xf49ca0:
                return new ru.denull.wire.stub.tl.updates.Difference(buffer);
            case 0xa8fb1981:
                return new ru.denull.wire.stub.tl.updates.DifferenceSlice(buffer);
            case 0xe317af7e:
                return new UpdatesTooLong(buffer);
            case 0xd3f45784:
                return new UpdateShortMessage(buffer);
            case 0x2b2fbd4e:
                return new UpdateShortChatMessage(buffer);
            case 0x78d4dec1:
                return new UpdateShort(buffer);
            case 0x725b04c3:
                return new UpdatesCombined(buffer);
            case 0x74ae4240:
                return new Updates(buffer);
            case 0x8dca6aa5:
                return new ru.denull.wire.stub.tl.photos.Photos(buffer);
            case 0x15051f54:
                return new ru.denull.wire.stub.tl.photos.PhotosSlice(buffer);
            case 0x20212ca8:
                return new ru.denull.wire.stub.tl.photos.Photo(buffer);
            case 0x96a18d5:
                return new ru.denull.wire.stub.tl.upload.File(buffer);
            case 0x2ec2a43c:
                return new DcOption(buffer);
            case 0x232d5905:
                return new Config(buffer);
            case 0x8e1a1775:
                return new NearestDc(buffer);
            case 0x18cb9f78:
                return new ru.denull.wire.stub.tl.help.InviteText(buffer);
            case 0x3e74f5c6:
                return new ru.denull.wire.stub.tl.messages.StatedMessagesLinks(buffer);
            case 0xa9af2881:
                return new ru.denull.wire.stub.tl.messages.StatedMessageLink(buffer);
            case 0xe9db4a3f:
                return new ru.denull.wire.stub.tl.messages.SentMessageLink(buffer);
            case 0x74d456fa:
                return new InputGeoChat(buffer);
            case 0x4d8ddec8:
                return new InputNotifyGeoChatPeer(buffer);
            case 0x75eaea5a:
                return new GeoChat(buffer);
            case 0x60311a9b:
                return new GeoChatMessageEmpty(buffer);
            case 0x4505f8e1:
                return new GeoChatMessage(buffer);
            case 0xd34fa24e:
                return new GeoChatMessageService(buffer);
            case 0x17b1578b:
                return new ru.denull.wire.stub.tl.geochats.StatedMessage(buffer);
            case 0x48feb267:
                return new ru.denull.wire.stub.tl.geochats.Located(buffer);
            case 0xd1526db1:
                return new ru.denull.wire.stub.tl.geochats.Messages(buffer);
            case 0xbc5863e8:
                return new ru.denull.wire.stub.tl.geochats.MessagesSlice(buffer);
            case 0x6f038ebc:
                return new MessageActionGeoChatCreate(buffer);
            case 0xc7d53de:
                return new MessageActionGeoChatCheckin(buffer);
            case 0x5a68e3f7:
                return new UpdateNewGeoChatMessage(buffer);
            case 0x63117f24:
                return new WallPaperSolid(buffer);
            case 0x12bcbd9a:
                return new UpdateNewEncryptedMessage(buffer);
            case 0x1710f156:
                return new UpdateEncryptedChatTyping(buffer);
            case 0xb4a2e88d:
                return new UpdateEncryption(buffer);
            case 0x38fe25b7:
                return new UpdateEncryptedMessagesRead(buffer);
            case 0xab7ec0a0:
                return new EncryptedChatEmpty(buffer);
            case 0x3bf703dc:
                return new EncryptedChatWaiting(buffer);
            case 0xfda9a7b7:
                return new EncryptedChatRequested(buffer);
            case 0x6601d14f:
                return new EncryptedChat(buffer);
            case 0x13d6dd27:
                return new EncryptedChatDiscarded(buffer);
            case 0xf141b5e1:
                return new InputEncryptedChat(buffer);
            case 0xc21f497e:
                return new EncryptedFileEmpty(buffer);
            case 0x4a70994c:
                return new EncryptedFile(buffer);
            case 0x1837c364:
                return new InputEncryptedFileEmpty(buffer);
            case 0x64bd0306:
                return new InputEncryptedFileUploaded(buffer);
            case 0x5a17b5e5:
                return new InputEncryptedFile(buffer);
            case 0xf5235d55:
                return new InputEncryptedFileLocation(buffer);
            case 0xed18c118:
                return new EncryptedMessage(buffer);
            case 0x23734b06:
                return new EncryptedMessageService(buffer);
            case 0x99a438cf:
                return new DecryptedMessageLayer(buffer);
            case 0x1f814f1f:
                return new DecryptedMessage(buffer);
            case 0xaa48327d:
                return new DecryptedMessageService(buffer);
            case 0x89f5c4a:
                return new DecryptedMessageMediaEmpty(buffer);
            case 0x32798a8c:
                return new DecryptedMessageMediaPhoto(buffer);
            case 0x4cee6ef3:
                return new DecryptedMessageMediaVideo(buffer);
            case 0x35480a59:
                return new DecryptedMessageMediaGeoPoint(buffer);
            case 0x588a0a97:
                return new DecryptedMessageMediaContact(buffer);
            case 0xa1733aec:
                return new DecryptedMessageActionSetMessageTTL(buffer);
            case 0xc0e24635:
                return new ru.denull.wire.stub.tl.messages.DhConfigNotModified(buffer);
            case 0x2c221edd:
                return new ru.denull.wire.stub.tl.messages.DhConfig(buffer);
            case 0x560f8935:
                return new ru.denull.wire.stub.tl.messages.SentEncryptedMessage(buffer);
            case 0x9493ff32:
                return new ru.denull.wire.stub.tl.messages.SentEncryptedFile(buffer);
            case 0xfa4f0bb5:
                return new InputFileBig(buffer);
            case 0x2dc173c8:
                return new InputEncryptedFileBigUploaded(buffer);
            case 0xe7512126:
                return new DestroySession(buffer);
            case 0xcb9f372d:
                return new InvokeAfterMsg(buffer);
            case 0x3dc4b4f0:
                return new InvokeAfterMsgs(buffer);
            case 0x53835315:
                return new InvokeWithLayer1(buffer);
            case 0x3fc12e08:
                return new InitConnection(buffer);
            case 0x6fe51dfb:
                return new ru.denull.wire.stub.tl.auth.CheckPhone(buffer);
            case 0x768d5f4d:
                return new ru.denull.wire.stub.tl.auth.SendCode(buffer);
            case 0x3c51564:
                return new ru.denull.wire.stub.tl.auth.SendCall(buffer);
            case 0x1b067634:
                return new ru.denull.wire.stub.tl.auth.SignUp(buffer);
            case 0xbcd51581:
                return new ru.denull.wire.stub.tl.auth.SignIn(buffer);
            case 0x5717da40:
                return new ru.denull.wire.stub.tl.auth.LogOut(buffer);
            case 0x9fab0d1a:
                return new ru.denull.wire.stub.tl.auth.ResetAuthorizations(buffer);
            case 0x771c1d97:
                return new ru.denull.wire.stub.tl.auth.SendInvites(buffer);
            case 0xe5bfffcd:
                return new ru.denull.wire.stub.tl.auth.ExportAuthorization(buffer);
            case 0xe3ef9613:
                return new ru.denull.wire.stub.tl.auth.ImportAuthorization(buffer);
            case 0x446c712c:
                return new ru.denull.wire.stub.tl.account.RegisterDevice(buffer);
            case 0x65c55b40:
                return new ru.denull.wire.stub.tl.account.UnregisterDevice(buffer);
            case 0x84be5b93:
                return new ru.denull.wire.stub.tl.account.UpdateNotifySettings(buffer);
            case 0x12b3ad31:
                return new ru.denull.wire.stub.tl.account.GetNotifySettings(buffer);
            case 0xdb7e1747:
                return new ru.denull.wire.stub.tl.account.ResetNotifySettings(buffer);
            case 0xf0888d68:
                return new ru.denull.wire.stub.tl.account.UpdateProfile(buffer);
            case 0x6628562c:
                return new ru.denull.wire.stub.tl.account.UpdateStatus(buffer);
            case 0xc04cfac2:
                return new ru.denull.wire.stub.tl.account.GetWallPapers(buffer);
            case 0xd91a548:
                return new ru.denull.wire.stub.tl.users.GetUsers(buffer);
            case 0xca30a5b1:
                return new ru.denull.wire.stub.tl.users.GetFullUser(buffer);
            case 0xc4a353ee:
                return new ru.denull.wire.stub.tl.contacts.GetStatuses(buffer);
            case 0x22c6aa08:
                return new ru.denull.wire.stub.tl.contacts.GetContacts(buffer);
            case 0xda30b32d:
                return new ru.denull.wire.stub.tl.contacts.ImportContacts(buffer);
            case 0x11f812d8:
                return new ru.denull.wire.stub.tl.contacts.Search(buffer);
            case 0xcd773428:
                return new ru.denull.wire.stub.tl.contacts.GetSuggested(buffer);
            case 0x8e953744:
                return new ru.denull.wire.stub.tl.contacts.DeleteContact(buffer);
            case 0x59ab389e:
                return new ru.denull.wire.stub.tl.contacts.DeleteContacts(buffer);
            case 0x332b49fc:
                return new ru.denull.wire.stub.tl.contacts.Block(buffer);
            case 0xe54100bd:
                return new ru.denull.wire.stub.tl.contacts.Unblock(buffer);
            case 0xf57c350f:
                return new ru.denull.wire.stub.tl.contacts.GetBlocked(buffer);
            case 0x4222fa74:
                return new ru.denull.wire.stub.tl.messages.GetMessages(buffer);
            case 0xeccf1df6:
                return new ru.denull.wire.stub.tl.messages.GetDialogs(buffer);
            case 0x92a1df2f:
                return new ru.denull.wire.stub.tl.messages.GetHistory(buffer);
            case 0x7e9f2ab:
                return new ru.denull.wire.stub.tl.messages.Search(buffer);
            case 0xb04f2510:
                return new ru.denull.wire.stub.tl.messages.ReadHistory(buffer);
            case 0xf4f8fb61:
                return new ru.denull.wire.stub.tl.messages.DeleteHistory(buffer);
            case 0x14f2dd0a:
                return new ru.denull.wire.stub.tl.messages.DeleteMessages(buffer);
            case 0x395f9d7e:
                return new ru.denull.wire.stub.tl.messages.RestoreMessages(buffer);
            case 0x28abcb68:
                return new ru.denull.wire.stub.tl.messages.ReceivedMessages(buffer);
            case 0x719839e9:
                return new ru.denull.wire.stub.tl.messages.SetTyping(buffer);
            case 0x4cde0aab:
                return new ru.denull.wire.stub.tl.messages.SendMessage(buffer);
            case 0xa3c85d76:
                return new ru.denull.wire.stub.tl.messages.SendMedia(buffer);
            case 0x514cd10f:
                return new ru.denull.wire.stub.tl.messages.ForwardMessages(buffer);
            case 0x3c6aa187:
                return new ru.denull.wire.stub.tl.messages.GetChats(buffer);
            case 0x3b831c66:
                return new ru.denull.wire.stub.tl.messages.GetFullChat(buffer);
            case 0xb4bc68b5:
                return new ru.denull.wire.stub.tl.messages.EditChatTitle(buffer);
            case 0xd881821d:
                return new ru.denull.wire.stub.tl.messages.EditChatPhoto(buffer);
            case 0x2ee9ee9e:
                return new ru.denull.wire.stub.tl.messages.AddChatUser(buffer);
            case 0xc3c5cd23:
                return new ru.denull.wire.stub.tl.messages.DeleteChatUser(buffer);
            case 0x419d9aee:
                return new ru.denull.wire.stub.tl.messages.CreateChat(buffer);
            case 0xedd4882a:
                return new ru.denull.wire.stub.tl.updates.GetState(buffer);
            case 0xa041495:
                return new ru.denull.wire.stub.tl.updates.GetDifference(buffer);
            case 0xeef579a0:
                return new ru.denull.wire.stub.tl.photos.UpdateProfilePhoto(buffer);
            case 0xd50f9c88:
                return new ru.denull.wire.stub.tl.photos.UploadProfilePhoto(buffer);
            case 0xb304a621:
                return new ru.denull.wire.stub.tl.upload.SaveFilePart(buffer);
            case 0xe3a6cfb5:
                return new ru.denull.wire.stub.tl.upload.GetFile(buffer);
            case 0xc4f9186b:
                return new ru.denull.wire.stub.tl.help.GetConfig(buffer);
            case 0x1fb33026:
                return new ru.denull.wire.stub.tl.help.GetNearestDc(buffer);
            case 0x6f02f748:
                return new ru.denull.wire.stub.tl.help.SaveAppLog(buffer);
            case 0xa4a95186:
                return new ru.denull.wire.stub.tl.help.GetInviteText(buffer);
            case 0xb7ee553c:
                return new ru.denull.wire.stub.tl.photos.GetUserPhotos(buffer);
            case 0x289dd1f6:
                return new InvokeWithLayer2(buffer);
            case 0x3f3f4f2:
                return new ru.denull.wire.stub.tl.messages.ForwardMessage(buffer);
            case 0x41bb0972:
                return new ru.denull.wire.stub.tl.messages.SendBroadcast(buffer);
            case 0xb7475268:
                return new InvokeWithLayer3(buffer);
            case 0x7f192d8f:
                return new ru.denull.wire.stub.tl.geochats.GetLocated(buffer);
            case 0xe1427e6f:
                return new ru.denull.wire.stub.tl.geochats.GetRecents(buffer);
            case 0x55b3e8fb:
                return new ru.denull.wire.stub.tl.geochats.Checkin(buffer);
            case 0x6722dd6f:
                return new ru.denull.wire.stub.tl.geochats.GetFullChat(buffer);
            case 0x4c8e2273:
                return new ru.denull.wire.stub.tl.geochats.EditChatTitle(buffer);
            case 0x35d81a95:
                return new ru.denull.wire.stub.tl.geochats.EditChatPhoto(buffer);
            case 0xcfcdc44d:
                return new ru.denull.wire.stub.tl.geochats.Search(buffer);
            case 0xb53f7a68:
                return new ru.denull.wire.stub.tl.geochats.GetHistory(buffer);
            case 0x8b8a729:
                return new ru.denull.wire.stub.tl.geochats.SetTyping(buffer);
            case 0x61b0044:
                return new ru.denull.wire.stub.tl.geochats.SendMessage(buffer);
            case 0xb8f0deff:
                return new ru.denull.wire.stub.tl.geochats.SendMedia(buffer);
            case 0xe092e16:
                return new ru.denull.wire.stub.tl.geochats.CreateGeoChat(buffer);
            case 0xdea0d430:
                return new InvokeWithLayer4(buffer);
            case 0x417a57ae:
                return new InvokeWithLayer5(buffer);
            case 0x3a64d54d:
                return new InvokeWithLayer6(buffer);
            case 0xa5be56d3:
                return new InvokeWithLayer7(buffer);
            case 0x26cf8950:
                return new ru.denull.wire.stub.tl.messages.GetDhConfig(buffer);
            case 0xf64daf43:
                return new ru.denull.wire.stub.tl.messages.RequestEncryption(buffer);
            case 0x3dbc0415:
                return new ru.denull.wire.stub.tl.messages.AcceptEncryption(buffer);
            case 0xedd923c5:
                return new ru.denull.wire.stub.tl.messages.DiscardEncryption(buffer);
            case 0x791451ed:
                return new ru.denull.wire.stub.tl.messages.SetEncryptedTyping(buffer);
            case 0x7f4b690a:
                return new ru.denull.wire.stub.tl.messages.ReadEncryptedHistory(buffer);
            case 0xa9776773:
                return new ru.denull.wire.stub.tl.messages.SendEncrypted(buffer);
            case 0x9a901b66:
                return new ru.denull.wire.stub.tl.messages.SendEncryptedFile(buffer);
            case 0x32d439a4:
                return new ru.denull.wire.stub.tl.messages.SendEncryptedService(buffer);
            case 0x55a5bb66:
                return new ru.denull.wire.stub.tl.messages.ReceivedQueue(buffer);
            case 0xe9abd9fd:
                return new InvokeWithLayer8(buffer);
            case 0xde7b673d:
                return new ru.denull.wire.stub.tl.upload.SaveBigFilePart(buffer);
            case 0x69796de9:
                return new InitConnection(buffer);
            case 0x76715a63:
                return new InvokeWithLayer9(buffer);
        }


        System.err.println("Unable to decode TLObject with constructor 0x" + Integer.toHexString(number) + ", previous 0x" + Integer.toHexString(prevNum));
        //Log.e(TAG, "Buffer dump (error at " + buffer.position() + "): " + toString(buffer));
        return null;
    }

    public static TLObject read(byte[] buffer) throws Exception {
        ByteBuffer buf = ByteBuffer.wrap(buffer);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        return read(buf);
    }

    public static TLObject read(String base64) throws Exception {
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
    public static <T extends TLObject> T[] readVector(ByteBuffer buffer, boolean boxed, T[] a) throws Exception {
        if (boxed) {
            buffer.getInt();
        }

        int size = buffer.getInt();
        ArrayList<T> value = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            value.add((T) TL.read(buffer));
        }
        return value.toArray(a);
    }

    //TODO: remove this single exception (now only TransportMessage can be inner unboxed type)
    public static TransportMessage[] readVectorMessage(ByteBuffer buffer, boolean boxed) throws Exception {
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
            try {
                value[i] = new String(TL.readString(buffer), "UTF8");
            } catch (Exception e) {
            }
            ;
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

    public static void writeVector(ByteBuffer buffer, TLObject[] value, boolean outerBoxed, boolean innerBoxed) throws Exception {
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

    public static int length(TLObject[] value, boolean boxed) throws Exception {
        int len = 0;
        for (TLObject object : value) {
            len += object.length() + (boxed ? 4 : 0);
        }
        return len;
    }

    public static int length(TLObject[] value) throws Exception {
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
            if (i % 16 > 0) str += " ";
            else if (i > 0) str += "\n";
            str += String.format("%02x", value.get(i));
        }
        return "\n" + str + "\n";
    }

    public static String toString(byte[] value) {
        String str = "";
        for (int i = 0; i < value.length; i++) {
            if (i % 16 > 0) str += " ";
            else if (i > 0) str += "\n";
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
