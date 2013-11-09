package ru.denull.wire;

import java.awt.*;
import java.awt.Dialog;

import javax.swing.JFrame;
import javax.swing.JList;

import java.awt.BorderLayout;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Random;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.AbstractListModel;

//import com.apple.eawt.Application;










import ru.denull.mtproto.DataService;
import ru.denull.mtproto.DataService.OnUpdateListener;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Auth.AuthCallback;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.model.*;
import tl.*;
import tl.Message;
import tl.TChat;
import tl.TMessage;
import tl.contacts.TForeignLink;
import tl.contacts.TMyLink;
import tl.messages.*;

public class Main implements OnUpdateListener {

  private JFrame frame;
  private JList dialogList, messageList;
  private JTextField searchField;
  private TInputPeer currentPeer;
  
  public static DataService service;
  public static Main window;
  
  private MessageListModel messageListModel;
  private DialogListModel dialogListModel;
  private DialogCellRenderer dialogListRenderer;
  private ContactListModel contactListModel;
  private ContactListRenderer contactListRenderer;
  private JTextField messageField;
  private JToggleButton dialogsBtn;
  private JToggleButton contactsBtn;
  

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    //Application app = Application.getApplication();
    //app.setDockIconImage( Utils.getImage("icon_128x128.png"));
    
    if (System.getProperty("os.name").contains("Mac")) {
      try {
        Object app = 
          Class.forName("com.apple.eawt.Application")
          .getMethod("getApplication", (Class[]) null)
          .invoke(null, (Object[]) null);
        
        app.getClass()
          .getMethod("setDockIconImage", new Class[] { Image.class })
          .invoke(app, new Object[] { Utils.getImage("icon_128x128.png") });
      } catch (Exception e) {
        //fail quietly
      }
    } else {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    System.setProperty("awt.useSystemAAFontSettings","on");
    System.setProperty("swing.aatext", "true");
    
    //UIManager.put("List.lockToPositionOnScroll", Boolean.FALSE);
    
    Font font = new Font("Tahoma", java.awt.Font.PLAIN, 12);
    Enumeration keys = UIManager.getDefaults().keys();
    
    while (keys.hasMoreElements()) {  
       Object key = keys.nextElement();  
       Object value = UIManager.get(key);  
       if (value instanceof Font) {  
           UIManager.put(key, font);  
       }  
    }
   //SwingUtilities.updateComponentTreeUI(frame);
    
    service = DataService.getInstance();
    
    service.connectAndPrepare(false, false, new AuthCallback() {
      public void error() {
        System.out.println("Unable to generate auth key");
      }
      
      public void done(Server server, byte[] auth_key) {
        System.out.println("Auth key generated");
        Notifier.enter(Notifier.MAIN_SERVER_AUTH_KEY);
        
        /*ui(new Runnable() {
          public void run() {
            if (chatlist != null && chatlist.getListAdapter() != null) {
              ((ChatListAdapter) chatlist.getListAdapter()).preload(true);
            }
          }
        }, true);*/
        
        EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              window = new Main();
              window.frame.setVisible(true);
              service.updateListener = window;
              //service.me = null;
              
              if (service.me != null) {
                window.dialogListModel.reloadDialogs();
                window.contactListModel.reload();
              } else {
                AuthDialog authDialog = new AuthDialog(window.frame, Dialog.ModalityType.DOCUMENT_MODAL);
                authDialog.setVisible(true);
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
        
        
      }
    });
    
    /*try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }*/
  }

  /**
   * Create the application.
   */
  public Main() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(200, 200, 820, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    frame.setTitle("Wire");    
    
    JSplitPane splitPane = new JSplitPane();
    splitPane.setContinuousLayout(true);
    splitPane.setDividerSize(1);
    splitPane.setBackground(Color.decode("0xe0e0e0"));
    splitPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    frame.getContentPane().add(splitPane, BorderLayout.CENTER);
    
    JPanel panel = new JPanel();
    splitPane.setLeftComponent(panel);
    panel.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_2 = new JPanel();
    panel.add(panel_2, BorderLayout.NORTH);
    panel_2.setBackground(Color.decode("0xf9f9f9"));
    panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
    
    dialogsBtn = new JToggleButton("");
    dialogsBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    dialogsBtn.putClientProperty("JButton.segmentPosition", "first");
    dialogsBtn.setFocusable(false);
    dialogsBtn.setSelected(true);
    dialogsBtn.setPreferredSize(new Dimension(24, 14));
    dialogsBtn.setMinimumSize(new Dimension(24, 14));
    dialogsBtn.setIcon(new ImageIcon(Utils.getImage("dialogs_up.png")));
    dialogsBtn.setSelectedIcon(new ImageIcon(Utils.getImage("dialogs_down.png")));
    //dialogsBtn.setPressedIcon(new ImageIcon(Utils.getImage("dialogs_down.png")));
    dialogsBtn.setIconTextGap(0);
    dialogsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialogsBtn.setSelected(true);
        contactsBtn.setSelected(false);
        
        dialogList.setCellRenderer(null);
        dialogList.setModel(dialogListModel);
        dialogList.setCellRenderer(dialogListRenderer);
        restoreDialogSelection();
      }
    });
    panel_2.add(dialogsBtn);
    
    contactsBtn = new JToggleButton("");
    contactsBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    contactsBtn.putClientProperty("JButton.segmentPosition", "last");
    contactsBtn.setFocusable(false);
    contactsBtn.setPreferredSize(new Dimension(24, 14));
    contactsBtn.setMinimumSize(new Dimension(24, 14));
    contactsBtn.setIcon(new ImageIcon(Utils.getImage("contacts_up.png")));
    contactsBtn.setSelectedIcon(new ImageIcon(Utils.getImage("contacts_down.png")));
    //contactsBtn.setPressedIcon(new ImageIcon(Utils.getImage("contacts_down.png")));
    contactsBtn.setIconTextGap(0);
    contactsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialogsBtn.setSelected(false);
        contactsBtn.setSelected(true);

        dialogList.setCellRenderer(null);
        dialogList.setModel(contactListModel);
        dialogList.setCellRenderer(contactListRenderer);
        restoreDialogSelection();
      }
    });
    panel_2.add(contactsBtn);
    
    searchField = new JTextField();
    searchField.putClientProperty("JTextField.variant", "search");
    searchField.putClientProperty("JTextField.Search.Prompt", "Найти...");
    panel_2.add(searchField);
    searchField.setColumns(10);
    
    dialogList = new JList() {
      public boolean getScrollableTracksViewportWidth() {
        return true;
      }
    };
    dialogList.setBackground(UIManager.getColor("Panel.background"));
    //dialogList.setBorder(UIManager.getBorder("List.sourceListBackgroundPainter"));
    dialogListRenderer = new DialogCellRenderer(service);
    dialogList.setCellRenderer(dialogListRenderer);
    dialogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    dialogList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (dialogList.getSelectedIndex() > -1) {
          if (dialogsBtn.isSelected()) {
            tl.Dialog dialog = service.dialogManager.get(dialogList.getSelectedIndex());
            selectDialog(dialog);
          } else
          if (contactsBtn.isSelected()) {
            TUser user = service.userManager.get((Integer) contactListModel.getElementAt(dialogList.getSelectedIndex()));
            selectDialog(user);
          }
        }
      }
    });
    dialogListModel = new DialogListModel(service, dialogList);
    dialogList.setModel(dialogListModel);
    JScrollPane scrollPane = new JScrollPane(dialogList);
    scrollPane.setBackground(Color.decode("0xf9f9f9"));
    scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    panel.add(scrollPane, BorderLayout.CENTER);
    
    contactListModel = new ContactListModel(service, dialogList);
    contactListRenderer = new ContactListRenderer(service);
    
    JPanel panel_1 = new JPanel();
    splitPane.setRightComponent(panel_1);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    messageList = new JList() {
      public boolean getScrollableTracksViewportWidth() {
        return true;
      }

      @Override
      public int getScrollableUnitIncrement(Rectangle visibleRect,
          int orientation, int direction) {
        //return super.getScrollableUnitIncrement(visibleRect, orientation, direction);
        return 20;
      }

      @Override
      public int getScrollableBlockIncrement(Rectangle visibleRect,
          int orientation, int direction) {
        return super.getScrollableBlockIncrement(visibleRect, orientation, direction);
      }
      
      
    };
    //System.out.println(messageList.getUI().toString());
    messageList.setBackground(Color.decode("0xdfe8ef"));
    messageList.addComponentListener(new ComponentListener() {
      public void componentShown(ComponentEvent e) {
      }
      
      public void componentResized(ComponentEvent e) {
        messageList.setFixedCellHeight(0);
        messageList.setFixedCellHeight(-1);
        //messageList.revalidate();
      }
      
      public void componentMoved(ComponentEvent e) {
      }
      
      public void componentHidden(ComponentEvent e) {
      }
    });
    scrollPane = new JScrollPane(messageList);
    scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBackground(Color.decode("0xd6e4ef"));
    scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        if (messageListModel != null && messageList != null && !e.getValueIsAdjusting()) {
          messageListModel.scrolled(messageList.getFirstVisibleIndex(), messageList.getLastVisibleIndex());
        }
      }
    });
    panel_1.add(scrollPane, BorderLayout.CENTER);
    
    messageField = new JTextField();
    panel_1.add(messageField, BorderLayout.SOUTH);
    //textArea.setBorder(new JTextField().getBorder());
    
    messageField.addKeyListener(new KeyListener() {
      
      @Override
      public void keyTyped(KeyEvent e) {
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          String message = messageField.getText();
          if (currentPeer != null && message.length() > 0) {
            sendMessage(message, currentPeer);
            messageField.setText("");
          }
        }
      }

      @Override
      public void keyPressed(KeyEvent e) {
      }
    });
    
    new DropTarget(messageList, new DropTargetListener() {
      public void dropActionChanged(DropTargetDragEvent dtde) {
      }
      
      public void drop(DropTargetDropEvent dtde) {
        
      }
      
      public void dragOver(DropTargetDragEvent dtde) {
      }
      
      public void dragExit(DropTargetEvent dte) {
      }
      
      public void dragEnter(DropTargetDragEvent dtde) {
        
      }
    });
  }
  
  public void sendMessage(String message, TInputPeer inputPeer) {    
    int random_id = -(new Random()).nextInt(0x10000000);
    int peer_id = Utils.getPeerID(inputPeer, service.me);
    TPeer peer;
    if (peer_id > 0) {
      peer = new PeerUser(peer_id);
    } else {
      peer = new PeerChat(-peer_id);
    }
    final TMessage newmsg = new Message(random_id, service.me.id, peer, true, true, (int) (System.currentTimeMillis() / 1000), message, new MessageMediaEmpty());
    newmsg.sending = true;
    service.messageManager.store(newmsg);
    dialogListModel.addMessage(newmsg);
    messageListModel.addMessage(newmsg);
    restoreDialogSelection();
    dialogList.repaint();
    messageList.repaint();
    
    service.mainServer.call(new SendMessage(currentPeer, message, random_id), new RPCCallback<SentMessage>() {
      public void done(SentMessage result) {
        newmsg.id = result.id;
        newmsg.date = result.date;
        newmsg.sending = false;
        service.messageManager.store(newmsg);
        restoreDialogSelection();
        dialogList.repaint();
        messageList.repaint();
      }
      public void error(int code, String message) {
        newmsg.failed = true;
      }
    });
  }
  
  public void selectDialog(TChat chat) {
    selectDialog(new InputPeerChat(chat.id));
  }
  
  public void selectDialog(TUser user) {
    TInputPeer peer = null;
    if (user instanceof UserForeign || user instanceof UserRequest) {
      peer = new InputPeerForeign(user.id, user.access_hash);
    } else {
      peer = new InputPeerContact(user.id);
    }
    selectDialog(peer);
  }
  
  public void selectDialog(final TInputPeer peer) {
    if (currentPeer != null &&
        ((peer instanceof InputPeerChat && currentPeer instanceof InputPeerChat && peer.chat_id == currentPeer.chat_id) ||
         (!(peer instanceof InputPeerChat) && !(currentPeer instanceof InputPeerChat) && peer.user_id == currentPeer.user_id))) {
      return;
    }
    
    currentPeer = peer;
    messageListModel = new MessageListModel(service, messageList, peer);
    
    messageList.setModel(messageListModel);
    messageList.setCellRenderer(new MessageCellRenderer(service, peer));
    
    
 // TODO: check first two conditions
    //if (service.mainServer == null || !service.mainServer.transport.isConnected() || loading) return;
    
    // prevent from loading two times at once
    /*loading = true;
    // TODO: use max_id instead of offset?
    //Log.i(TAG, "Loading history up to id " + first_id);
    service.mainServer.call(new GetHistory(peer, 0, first_id, (first_id == 0) ? PRELOAD_COUNT : LOAD_COUNT), new RPCCallback<TMessages>() {
      public void done(final TMessages result) {
        if (result.messages.length > 0 && result.messages[0].unread) {
          for (TMessage message : result.messages) {
            message.unread = false;
          }
          service.dialogManager.resetUnread(peer);
          service.mainServer.call(new ReadHistory(peer, result.messages[0].id, 0), new Server.RPCCallback<TLObject>() {
            public void done(TLObject result) {
            }
            public void error(int code, String message) {
            }
          });
        }
        
        service.chatManager.store(result.chats);
        service.userManager.store(result.users);
        
        final boolean forceCached = (service.messageManager.get(result.messages[result.messages.length - 1].id) != service.messageManager.empty);
        service.messageManager.store(result.messages);
        
        if (result instanceof Messages) {
          total = result.messages.length;
        } else {
          total = ((MessagesSlice) result).count;
        }
        
        activity.ui(new Runnable() {
          public void run() {*/
            /*if (cachedData) {
              items.clear();
              cachedData = false;
            }*/
            /*cachedData = add(result.messages); // if loaded data overlaps data from cache, we can continue loading from cache
            if (cachedData) {
              Log.i(TAG, "Overlaps cached data, will now load from cache");
            } else
            if (forceCached) {
              cachedData = true;
              Log.i(TAG, "Last loaded item is already in cache, will now load from cache");
            }
            loading = false;
            
            checkForPreload();
          }
        }, true);
      }
      public void error(int code, String message) {
        Log.e(TAG, "Error while loading messages");
      }
    });*/
  }
  
  public void selectDialog(tl.Dialog dialog) {
    if (dialog.peer instanceof PeerUser) {
      selectDialog(service.userManager.get(((PeerUser) dialog.peer).user_id));
    } else {
      selectDialog(new InputPeerChat(((PeerChat) dialog.peer).chat_id));
    }
  }

  public void authorized(TUser user) {
    service.logged((UserSelf) user);
    dialogListModel.reloadDialogs();
    contactListModel.reload();
  }

  @Override
  public void onNewMessage(TMessage message, boolean fresh) {
    // TODO Auto-generated method stub
    restoreDialogSelection();
    dialogListModel.updateContents();
    if (currentPeer != null &&
        ((message.to_id instanceof PeerChat && currentPeer instanceof InputPeerChat && message.to_id.chat_id == currentPeer.chat_id) ||
         (message.to_id instanceof PeerUser && !(currentPeer instanceof InputPeerChat) && message.to_id.user_id == currentPeer.user_id))) {
      messageListModel.addMessage(message);
      //messageList
      
      service.dialogManager.resetUnread(currentPeer);
      service.mainServer.call(new ReadHistory(currentPeer, message.id, 0), new Server.RPCCallback<TLObject>() {
        public void done(TLObject result) {
        }
        public void error(int code, String message) {
        }
      });
    }
  }

  @Override
  public void onMessageID(int id, long random_id, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onReadMessages(int[] messages, boolean fresh) {
    // TODO Auto-generated method stub
    dialogList.repaint();
  }

  @Override
  public void onDeleteMessages(int[] messages, boolean fresh) {
    // TODO Auto-generated method stub
    restoreDialogSelection();
    dialogList.repaint();
  }

  @Override
  public void onRestoreMessages(int[] messages, boolean fresh) {
    // TODO Auto-generated method stub
    restoreDialogSelection();
    dialogList.repaint();
  }

  @Override
  public void onUserTyping(int user_id, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onChatUserTyping(int chat_id, int user_id, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onChatParticipants(TChatParticipants participants, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onUserStatus(int user_id, TUserStatus status, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onUserName(int user_id, String first_name, String last_name,
      boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onUserPhoto(int user_id, TUserProfilePhoto photo, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onContactRegistered(int user_id, int date, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onContactLink(int user_id, TMyLink my_link,
      TForeignLink foreign_link, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onActivation(int user_id, boolean fresh) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onNewAuthorization(long auth_key_id, int date, String device,
      String location, boolean fresh) {
    // TODO Auto-generated method stub
    
  }
  
  public void restoreDialogSelection() {
    if (currentPeer == null) return;
    if (dialogsBtn.isSelected()) {
      for (int i = 0; i < service.dialogManager.loaded.size(); i++) {
        tl.Dialog d = service.dialogManager.loaded.get(i);
        if ((d.peer instanceof PeerChat && currentPeer instanceof InputPeerChat && d.peer.chat_id == currentPeer.chat_id) ||
            (!(d.peer instanceof PeerChat) && !(currentPeer instanceof InputPeerChat) && d.peer.user_id == currentPeer.user_id)) {
          dialogList.setSelectedIndex(i);
          break;
        }      
      }
    } else {
      for (int i = 0; i < contactListModel.getSize(); i++) {
        int user_id = (Integer) contactListModel.getElementAt(i);
        if (!(currentPeer instanceof InputPeerChat) && user_id == currentPeer.user_id) {
          dialogList.setSelectedIndex(i);
          return;
        }      
      }
      //dialogList.setSelectedIndex(-1);
    }
  }
}
