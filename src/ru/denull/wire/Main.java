package ru.denull.wire;

import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.FlowLayout;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractListModel;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Auth.AuthCallback;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.model.Notifier;
import tl.TUser;
import tl.UserSelf;
import tl.messages.Dialogs;
import tl.messages.DialogsSlice;
import tl.messages.GetDialogs;
import tl.messages.TDialogs;

public class Main {

  private JFrame frame;
  private JList dialogList, messageList;
  private JTextField searchField;
  
  public static DataService service;
  public static Main window;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
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
              
              if (service.me != null) {
                window.reloadDialogs();
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
    splitPane.setDividerSize(2);
    splitPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    frame.getContentPane().add(splitPane, BorderLayout.CENTER);
    
    JPanel panel = new JPanel();
    splitPane.setLeftComponent(panel);
    panel.setLayout(new BorderLayout(0, 0));
    
    JPanel panel_2 = new JPanel();
    panel.add(panel_2, BorderLayout.NORTH);
    panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
    
    JToggleButton dialogsBtn = new JToggleButton("");
    dialogsBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    dialogsBtn.putClientProperty("JButton.segmentPosition", "first");
    dialogsBtn.setRequestFocusEnabled(false);
    dialogsBtn.setSelected(true);
    panel_2.add(dialogsBtn);
    
    JToggleButton contactsBtn = new JToggleButton("");
    contactsBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    contactsBtn.putClientProperty("JButton.segmentPosition", "last");
    contactsBtn.setRequestFocusEnabled(false);
    panel_2.add(contactsBtn);
    
    searchField = new JTextField();
    searchField.putClientProperty("JTextField.variant", "search");
    panel_2.add(searchField);
    searchField.setColumns(10);
    
    dialogList = new JList();
    dialogList.setModel(new AbstractListModel() {
      String[] values = new String[] {};
      public int getSize() {
        return values.length;
      }
      public Object getElementAt(int index) {
        return values[index];
      }
    });
    dialogList.setCellRenderer(new DialogCellRenderer(service));
    panel.add(dialogList, BorderLayout.CENTER);
    
    JPanel panel_1 = new JPanel();
    splitPane.setRightComponent(panel_1);
    panel_1.setLayout(new BorderLayout(0, 0));
    
    messageList = new JList();
    panel_1.add(messageList, BorderLayout.CENTER);
    
    JTextArea textArea = new JTextArea();
    panel_1.add(textArea, BorderLayout.SOUTH);
    
    
  }
  
  public void reloadDialogs() {
    boolean force = true;
    final boolean cachedData = false;
    
    if (service.mainServer == null || !service.mainServer.transport.isConnected()){
      //Log.i(TAG, "Not yet connected, stop");
      return;
    }
    //Log.i(TAG, "loading = " + service.dialogManager.loading);
    if (service.dialogManager.loading) return;
    if (!force && service.dialogManager.total > -1 && service.dialogManager.loaded.size() >= service.dialogManager.total) return;
    
    service.dialogManager.loading = true;
    service.mainServer.call(new GetDialogs(cachedData ? 0 : service.dialogManager.loaded.size(), 0, 30), new RPCCallback<TDialogs>() {
      public void done(TDialogs result) {
        if (result instanceof Dialogs) {
          Dialogs dialogs = (Dialogs) result;
          service.dialogManager.total = dialogs.dialogs.length;
          service.dialogManager.store(dialogs.dialogs, true);
          service.chatManager.store(dialogs.chats);
          service.userManager.store(dialogs.users);
          service.messageManager.store(dialogs.messages);
        } else {
          DialogsSlice dialogs = (DialogsSlice) result;
          service.dialogManager.total = dialogs.count;
          service.dialogManager.store(dialogs.dialogs, cachedData);
          service.chatManager.store(dialogs.chats);
          service.userManager.store(dialogs.users);
          service.messageManager.store(dialogs.messages);
        }
        service.dialogManager.loading = false;
        //cachedData = false;
        
        dialogList.setModel(new AbstractListModel() {
          String[] values = new String[service.dialogManager.total];
          public int getSize() {
            return values.length;
          }
          public Object getElementAt(int index) {
            return values[index];
          }
        });
      }
      public void error(int code, String message) {
        //Log.e(TAG, "Error while loading dialogs");
      }
    });
  }
  
  public void reloadContacts() {
    
  }

  public void authorized(TUser user) {
    service.logged((UserSelf) user);
    reloadDialogs();
  }
  
}
