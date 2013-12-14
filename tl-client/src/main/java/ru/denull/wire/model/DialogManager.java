package ru.denull.wire.model;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.Utils;
import ru.denull.wire.stub.tl.TMessage;
import ru.denull.wire.stub.tl.messages.*;

import javax.swing.*;
import java.util.LinkedList;

public class DialogManager extends AbstractListModel {
    private static final long serialVersionUID = 5885327904689475365L;
    private DataService service;

    boolean loading = false, loadingFromCache = false;
    boolean initializing = false;
    boolean cachedData = true;
    int total = -1;
    int loaded = 0;
    int first_id = 0;

    public LinkedList<Dialog> all = new LinkedList<Dialog>();
    public LinkedList<Dialog> filtered = null;
    String filterQuery = null;

    public int totalUnread = 0;

    public DialogManager(DataService service) {
        this.service = service;

        //peer_id = Utils.getPeerID(peer, service.me);
        //reloadDialogs();
    }

    public Object getElementAt(int index) {
        return (filtered == null) ? (index < all.size() ? all.get(index) : "У вас ещё нет диалогов") : (index < filtered.size() ? filtered.get(index) : "Ничего не найдено");
    }

    public int getSize() {
        return Math.max(1, (filtered == null) ? all.size() : filtered.size());
    }

    public boolean isEmpty() {
        return (filtered == null) ? all.isEmpty() : filtered.isEmpty();
    }

    public void filter(String query) {
        filter(query, false);
    }

    public void filter(String query, boolean force) {
        if (query != null) {
            query = query.trim().toLowerCase();
        }

        if (!force && filterQuery != null && filterQuery.equals(query)) {
            return;
        }

        if (query == null || query.length() == 0) {
            filtered = null;
            filterQuery = null;
            fireContentsChanged(this, 0, getSize() - 1);
            return;
        }

        filtered = new LinkedList<Dialog>();
        for (Dialog dialog : all) {
            boolean matches = false;
            if (dialog.peer instanceof PeerChat) {
                matches = service.chatManager.get(dialog.peer.chat_id).title.toLowerCase().indexOf(query) >= 0;
            } else {
                TUser user = service.userManager.get(dialog.peer.user_id);
                matches = (user.first_name + " " + user.last_name).toLowerCase().indexOf(query) >= 0 || (user.last_name + " " + user.first_name).toLowerCase().indexOf(query) >= 0;
            }

            if (matches) {
                filtered.add(dialog);
            }
        }
        filterQuery = query;
        fireContentsChanged(this, 0, getSize() - 1);
    }

    public void updateContents() {
        fireContentsChanged(this, 0, getSize() - 1);
    }

    public void updateContents(int index) {
        fireContentsChanged(this, index, index);
    }

    public void reloadDialogs() {
        boolean force = true;
        final boolean cachedData = false;

        if (service.mainServer == null/* || !service.mainServer.transport.isConnected()*/) {
            //Log.i(TAG, "Not yet connected, stop");
            return;
        }
        //Log.i(TAG, "loading = " + service.dialogManager.loading);
        if (service.dialogManager.loading) return;
        if (!force && service.dialogManager.total > -1 && all.size() >= service.dialogManager.total) return;

        service.dialogManager.loading = true;
        service.mainServer.call(new GetDialogs(cachedData ? 0 : all.size(), 0, 100), new RPCCallback<TDialogs>() {
            public void done(TDialogs result) {
                if (result instanceof Dialogs) {
                    Dialogs dialogs = (Dialogs) result;
                    service.dialogManager.total = dialogs.dialogs.length;
                    store(dialogs.dialogs, true);
                    service.chatManager.store(dialogs.chats);
                    service.userManager.store(dialogs.users);
                    service.messageManager.store(dialogs.messages);
                } else {
                    DialogsSlice dialogs = (DialogsSlice) result;
                    service.dialogManager.total = dialogs.count;
                    store(dialogs.dialogs, cachedData);
                    service.chatManager.store(dialogs.chats);
                    service.userManager.store(dialogs.users);
                    service.messageManager.store(dialogs.messages);
                }
                service.dialogManager.loading = false;
                //cachedData = false;
        
        /*dialogList.setModel(new AbstractListModel() {
          String[] values = new String[service.dialogManager.total];
          public int getSize() {
            return values.length;
          }
          public Object getElementAt(int index) {
            return values[index];
          }
        });*/
                //fireContentsChanged(list, 0, service.dialogManager.loaded.size() - 1);

                filter(filterQuery, true);
            }

            public void error(int code, String message) {
                //Log.e(TAG, "Error while loading dialogs");
            }
        });
    }

    public void addMessage(TMessage message) {
        int index = 0;
        for (Dialog dialog : all) {
            if ((dialog.peer instanceof PeerUser && message.to_id instanceof PeerUser && (dialog.peer.user_id == message.from_id || dialog.peer.user_id == message.to_id.user_id)) ||
                    (dialog.peer instanceof PeerChat && message.to_id instanceof PeerChat && dialog.peer.chat_id == message.to_id.chat_id)) {
                break;
            }
            index++;
        }

        Dialog dialog;
        if (index < all.size()) {
            if (message.id > 0 && all.get(index).top_message > message.id) return;
            dialog = all.remove(index);
            dialog.top_message = message.id;
            if (!message.out) {
                dialog.unread_count++;
                updateUnreadCount(totalUnread + 1);
            }
        } else {
            dialog = new Dialog(message.to_id instanceof PeerChat ? message.to_id : new PeerUser(message.out ? message.to_id.user_id : message.from_id), message.id, 1);
        }

        all.addFirst(dialog);
        // TODO: store at DB
    }

    public void store(TDialog[] dialogs, boolean reset) {
        if (reset) {
            all.clear();
            totalUnread = 0;
        }

        for (TDialog dialog : dialogs) {
            all.add((Dialog) dialog);
            totalUnread += dialog.unread_count;
        }
    }

    public void resetUnread(TInputPeer peer) {
        int peer_id = Utils.getPeerID(peer, service.me);
        for (Dialog dialog : all) {
            if ((dialog.peer instanceof PeerUser && ((PeerUser) dialog.peer).user_id == peer_id) ||
                    (dialog.peer instanceof PeerChat && ((PeerChat) dialog.peer).chat_id == -peer_id)) {
                updateUnreadCount(Math.max(0, totalUnread - dialog.unread_count));
                dialog.unread_count = 0;
                break;
            }
        }
    }

    public void updateUnreadCount(int unread) {
        totalUnread = unread;

        //Application app = Application.getApplication();
        //app.setDockIconBadge();
        if (System.getProperty("os.name").contains("Mac")) {
            try {
                Object app =
                        Class.forName("com.apple.eawt.Application")
                                .getMethod("getApplication", (Class[]) null)
                                .invoke(null, (Object[]) null);

                app.getClass()
                        .getMethod("setDockIconBadge", new Class[]{String.class})
                        .invoke(app, new Object[]{totalUnread > 0 ? Utils.toCount(totalUnread) : ""});
            } catch (Exception e) {
                //fail quietly
            }
        }
    }

}
