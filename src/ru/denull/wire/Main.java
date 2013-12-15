package ru.denull.wire;

import static ru.denull.mtproto.CryptoUtils.*;

import java.awt.*;
import java.awt.Cursor;
import java.awt.Dialog;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.AbstractListModel;




import javax.swing.JButton;






































import javax.swing.JOptionPane;

//import com.apple.eawt.Application;
import ru.denull.mtproto.DataService;
import ru.denull.mtproto.DataService.OnUpdateListener;
import ru.denull.mtproto.Server;
import ru.denull.mtproto.Auth.AuthCallback;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.model.*;
import ru.denull.wire.model.Config;
import ru.denull.wire.model.DialogManager.EncryptedDialog;
import ru.denull.wire.model.FileManager.FileUploadingProgressiveCallback;
import ru.denull.wire.model.TypingManager.TypingCallback;
import sun.misc.IOUtils;
import tl.*;
import tl.Chat;
import tl.ChatFull;
import tl.Message;
import tl.TChat;
import tl.TMessage;
import tl.contacts.*;
import tl.messages.*;

public class Main implements OnUpdateListener, TypingCallback {

  private JFrame frame;
  private JList dialogList, messageList;
  private JTextField searchField;
  private TInputPeer currentPeer;
  private int currentEncryptedChat = -1;
  private ChatFull currentChat;
  
  public static DataService service;
  public static Main window;
  
  private MessageListModel messageListModel;
  private DialogCellRenderer dialogListRenderer;
  static public ContactListModel contactListModel;
  private ContactListRenderer contactListRenderer;
  private JTextField messageField;
  private JToggleButton dialogsBtn;
  private JToggleButton contactsBtn;
  
  final JFileChooser fc = new JFileChooser();
  FileDialog fd, importDialog;
  public static FileDialog saveDialog;
  private JPanel sendPanel;
  public static int currentMods = 0;
  private JButton actionBtn;
  private JPanel chatPanel;
  private JLabel titleLabel;
  private JLabel titleStatus;
  private JButton titleInfoBtn;
  private JButton titleActionBtn;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    //Application app = Application.getApplication();
    //app.setDockIconImage( Utils.getImage("icon_128x128.png"));
    
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Wire");
    
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
    
    Font font = new Font(Utils.fontName, java.awt.Font.PLAIN, 12);
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
                service.dialogManager.reloadDialogs();
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
  
  public void setMultipleMode() {
    /*try {
      fd.setMultipleMode(true);
    } catch (Exception e) {
      e.printStackTrace();
    }*/
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setBounds((dim.width - 1100) / 2, (dim.height - 660) / 2, 1100, 660);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(0, 0));
    frame.setTitle("Wire"); 
    
    fd = new FileDialog(frame, "Выберите изображения или видеозаписи для загрузки", FileDialog.LOAD);
    importDialog = new FileDialog(frame, "Выберите список контактов для импорта", FileDialog.LOAD);
    saveDialog = new FileDialog(frame, "Сохранить как...", FileDialog.SAVE);
    /*try {
      fd.getClass().getMethod("setMultipleMode", new Class[] { Boolean.class } ).invoke(fd, true);
    } catch (Exception e1) {
      e1.printStackTrace();
    }*/
    setMultipleMode();
    fd.setFilenameFilter(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        name = name.toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".avi") || name.endsWith(".mov") || name.endsWith(".mp4") || name.endsWith(".webp") || name.endsWith(".mp3");
      }
    });
    importDialog.setFilenameFilter(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        name = name.toLowerCase();
        return name.endsWith(".csv") || name.endsWith(".vcf");
      }
    });
    
    JMenuBar menuBar = new JMenuBar();

    JMenu menu;
    JMenuItem menuItem;
    

    menu = new JMenu("Настройки");
    menuBar.add(menu);
  
    menuItem = new JMenuItem("Сбросить авторизацию и выйти");
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Preferences pref = Preferences.userRoot().node("wire");
        try {
          pref.removeNode();
        } catch (BackingStoreException e1) {
          e1.printStackTrace();
        }
        System.out.println("Removed all preferences, exiting");
        System.exit(0);
      }
    });
    menu.add(menuItem);
    
    menu = new JMenu("Контакты");
    menuBar.add(menu);
    
    menuItem = new JMenuItem("Импорт...");
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        importContacts();
      }
    });
    menu.add(menuItem);
    menuItem = new JMenuItem("Импорт с заменой...");
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        importContacts(true);
      }
    });
    menu.add(menuItem);
    menuItem = new JMenuItem("Добавить контакт...");
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addContact();
      }
    });
    menu.add(menuItem);
    
    
    frame.setJMenuBar(menuBar);
    
    JSplitPane splitPane = new JSplitPane();
    splitPane.setContinuousLayout(true);
    splitPane.setDividerSize(1);
    splitPane.setBackground(Color.decode("0xe0e0e0"));
    splitPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    splitPane.setDividerLocation(320);
    frame.getContentPane().add(splitPane, BorderLayout.CENTER);
    
    JPanel listPanel = new JPanel();
    splitPane.setLeftComponent(listPanel);
    listPanel.setLayout(new BorderLayout(0, 0));
    
    JPanel searchPanel = new JPanel();
    listPanel.add(searchPanel, BorderLayout.NORTH);
    searchPanel.setBackground(Color.decode("0xf9f9f9"));
    searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
    searchPanel.add(Box.createRigidArea(new Dimension(6, 40)));
    
    searchField = new JTextField();
    searchField.putClientProperty("JTextField.variant", "search");
    //searchField.putClientProperty("JTextField.Search.Prompt", "Найти...");
    searchField.setAlignmentY(Component.CENTER_ALIGNMENT);
    searchField.setPreferredSize(new Dimension(0, 26));
    searchField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
    searchField.getDocument().addDocumentListener(new DocumentListener() {
      public void removeUpdate(DocumentEvent e) {
        String query = searchField.getForeground().equals(Color.LIGHT_GRAY) ? null : searchField.getText();
        service.dialogManager.filter(query);
        contactListModel.filter(query);
        restoreDialogSelection();
      }
      public void insertUpdate(DocumentEvent e) {
        String query = searchField.getForeground().equals(Color.LIGHT_GRAY) ? null : searchField.getText();
        service.dialogManager.filter(query);
        contactListModel.filter(query);
        restoreDialogSelection();
      }
      public void changedUpdate(DocumentEvent e) {
        String query = searchField.getForeground().equals(Color.LIGHT_GRAY) ? null : searchField.getText();
        service.dialogManager.filter(query);
        contactListModel.filter(query);
        restoreDialogSelection();
      }
    });
    searchPanel.add(searchField);
    searchField.setColumns(10);

    searchPanel.add(Box.createRigidArea(new Dimension(3, 0)));
    
    dialogsBtn = new JToggleButton("");
    dialogsBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    dialogsBtn.putClientProperty("JButton.segmentPosition", "first");
    dialogsBtn.setFocusable(false);
    dialogsBtn.setSelected(true);
    dialogsBtn.setPreferredSize(new Dimension(32, 26));
    dialogsBtn.setMinimumSize(new Dimension(32, 26));
    dialogsBtn.setMaximumSize(new Dimension(32, 26));
    //dialogsBtn.setIcon(new ImageIcon(Utils.getImage("dialogs_up.png")));
    //dialogsBtn.setSelectedIcon(new ImageIcon(Utils.getImage("dialogs_down.png")));
    dialogsBtn.setIcon(new ImageIcon(Utils.getImage("chats.png")));
    dialogsBtn.setSelectedIcon(new ImageIcon(Utils.getImage("chats_selected.png")));
    //dialogsBtn.setPressedIcon(new ImageIcon(Utils.getImage("dialogs_down.png")));
    dialogsBtn.setIconTextGap(0);
    dialogsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialogsBtn.setSelected(true);
        contactsBtn.setSelected(false);
        
        actionBtn.setText("Групповой чат...");
        
        dialogList.setCellRenderer(null);
        dialogList.setFixedCellHeight(66);
        dialogList.setModel(service.dialogManager);
        dialogList.setCellRenderer(dialogListRenderer);
        restoreDialogSelection();
      }
    });
    searchPanel.add(dialogsBtn);
    
    contactsBtn = new JToggleButton("");
    contactsBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    contactsBtn.putClientProperty("JButton.segmentPosition", "last");
    contactsBtn.setFocusable(false);
    contactsBtn.setPreferredSize(new Dimension(32, 26));
    contactsBtn.setMinimumSize(new Dimension(32, 26));
    contactsBtn.setMaximumSize(new Dimension(32, 26));
    //contactsBtn.setIcon(new ImageIcon(Utils.getImage("contacts_up.png")));
    //contactsBtn.setSelectedIcon(new ImageIcon(Utils.getImage("contacts_down.png")));
    contactsBtn.setIcon(new ImageIcon(Utils.getImage("contacts.png")));
    contactsBtn.setSelectedIcon(new ImageIcon(Utils.getImage("contacts_selected.png")));
    //contactsBtn.setPressedIcon(new ImageIcon(Utils.getImage("contacts_down.png")));
    contactsBtn.setIconTextGap(0);
    contactsBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialogsBtn.setSelected(false);
        contactsBtn.setSelected(true);
        
        actionBtn.setText("Добавить контакт...");

        dialogList.setCellRenderer(null);
        dialogList.setFixedCellHeight(-1);
        dialogList.setModel(contactListModel);
        dialogList.setCellRenderer(contactListRenderer);
        restoreDialogSelection();
      }
    });
    searchPanel.add(contactsBtn);
    
    searchPanel.add(Box.createRigidArea(new Dimension(6, 0)));
    
    dialogList = new InteractiveList() {
      public boolean getScrollableTracksViewportWidth() {
        return true;
      }
    };
    dialogList.setBackground(Color.WHITE);
    //dialogList.setBorder(UIManager.getBorder("List.sourceListBackgroundPainter"));
    dialogListRenderer = new DialogCellRenderer(service);
    dialogList.setCellRenderer(dialogListRenderer);
    dialogList.setFixedCellHeight(66);
    dialogList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    dialogList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (dialogList.getSelectedIndex() > -1) {
          if (chatPanel.isVisible()) {
            /*if (contactListModel.isEmpty()) return;
            
            chatMemberModel.add((Integer) contactListModel.getElementAt(dialogList.getSelectedIndex()), true);
            chatActionBtn.setEnabled(!chatTitleField.getForeground().equals(Color.LIGHT_GRAY) && !chatTitleField.getText().isEmpty() && !chatMemberModel.isEmpty());
            //dialogList.clearSelection();*/
          } else
          if (dialogsBtn.isSelected()) {
            if (service.dialogManager.isEmpty()) return;
            
            tl.Dialog dialog = (tl.Dialog) service.dialogManager.getElementAt(dialogList.getSelectedIndex());
            selectDialog(dialog);
          } else
          if (contactsBtn.isSelected()) {
            if (contactListModel.isEmpty()) return;
            
            TUser user = service.userManager.get((Integer) contactListModel.getElementAt(dialogList.getSelectedIndex()));
            selectDialog(user, null, null);
          }
        }
      }
    });
    dialogList.setModel(service.dialogManager);
    JScrollPane scrollPane = new JScrollPane(dialogList);
    scrollPane.setBackground(Color.decode("0xf9f9f9"));
    scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.decode("0xe0e0e0")));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    listPanel.add(scrollPane, BorderLayout.CENTER);
    
    contactListModel = new ContactListModel(service, dialogList);
    contactListRenderer = new ContactListRenderer(service);
    
    
    JPanel actionPanel = new JPanel();
    //actionPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("0xe0e0e0")));
    actionPanel.setBackground(Color.decode("0xf9f9f9"));
    actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
    listPanel.add(actionPanel, BorderLayout.SOUTH);
    actionPanel.add(Box.createRigidArea(new Dimension(6, 40)));
    
    actionBtn = new JButton("Групповой чат...");
    actionBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    actionBtn.setFocusable(false);
    //actionBtn.putClientProperty("JButton.buttonType", "roundRect");
    actionBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    actionBtn.putClientProperty("JButton.segmentPosition", "only");
    actionBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (chatPanel.isVisible()) {
          cancelCreateChat();
        } else
        if (dialogsBtn.isSelected()) {
          createChat();
        } else
        if (contactsBtn.isSelected()) {
          addContact();
        }
      }
    });
    actionPanel.add(actionBtn);
    actionPanel.add(Box.createRigidArea(new Dimension(6, 40)));
    
    JLayeredPane layeredPane = new JLayeredPane();
    layeredPane.setLayout(new OverlayLayout(layeredPane));
    splitPane.setRightComponent(layeredPane);
    
    contentPanel = new JPanel();
    layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);
    contentPanel.setLayout(new BorderLayout(0, 0));
    contentPanel.setAlignmentX(0);
    
    titlePanel = new JPanel();
    contentPanel.add(titlePanel, BorderLayout.NORTH);
    titlePanel.setBackground(Color.decode("0xf9f9f9"));
    titlePanel.setLayout(new GridBagLayout());
    titlePanel.setPreferredSize(new Dimension(0, 40));
    
    titleIcon = new ImagePanel();
    titleIcon.setPreferredSize(new Dimension(40, 40));
    titleIcon.setBorder(new EmptyBorder(4, 4, 4, 4));
    titlePanel.add(titleIcon, Utils.GBConstraints(0, 0, 1, 2));
    
    titleLabel = new JLabel();
    GridBagConstraints constraints = Utils.GBConstraints(1, 0, 1, 1);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    titleLabel.setFont(new Font(Utils.fontName, Font.PLAIN, 14));
    titleLabel.setBorder(new EmptyBorder(3, 0, 0, 0));
    titlePanel.add(titleLabel, constraints);
    
    titleStatus = new JLabel();
    constraints = Utils.GBConstraints(1, 1, 1, 1);
    constraints.weightx = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    titleStatus.setForeground(Color.decode("0x808080"));
    titlePanel.add(titleStatus, constraints);    
    
    titleInfoBtn = new JButton();
    titleInfoBtn.setFocusable(false);
    titleInfoBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    titleInfoBtn.putClientProperty("JButton.segmentPosition", "only");
    titleInfoBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        titleInfoBtn.setSelected(!titleInfoBtn.isSelected());
        
        if (currentPeer != null) {
          if (currentPeer instanceof InputPeerChat) {
            if (titleInfoBtn.isSelected()) {
              editChat();
              
            } else {
              cancelCreateChat();
            }
          } else {
            toggleTitlePanel(titleInfoBtn.isSelected());
          }
        }
      }
    });
    constraints = Utils.GBConstraints(2, 0, 1, 2);
    constraints.anchor = GridBagConstraints.PAGE_START;
    constraints.insets = new Insets(6, 0, 0, 0);
    titlePanel.add(titleInfoBtn, constraints);
    
    titlePanel.add(Box.createRigidArea(new Dimension(6, 40)), Utils.GBConstraints(3, 0, 1, 2));
    
    titleActionBtn = new JButton();
    titleActionBtn.setFocusable(false);
    titleActionBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    titleActionBtn.putClientProperty("JButton.segmentPosition", "only");
    titleActionBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentPeer != null) {
          if (currentPeer instanceof InputPeerChat) {
            TChat chat = service.chatManager.get(currentPeer.chat_id);
            if (!(chat instanceof ChatForbidden) && chat.left) {
              service.mainServer.call(new tl.messages.AddChatUser(currentPeer.chat_id, new InputUserSelf(), 20), new RPCCallback<StatedMessage>() {
                public void done(StatedMessage result) {
                  service.chatManager.store(result.chats);
                  service.userManager.store(result.users);
                  service.messageManager.store(result.message);
                  service.dialogManager.addMessage(result.message);
                  messageListModel.addMessage(result.message);
                  service.dialogManager.updateContents();
                  restoreDialogSelection();
                  
                  titleInfoBtn.setVisible(true);
                  titleActionBtn.setText("покинуть чат");
                  sendPanel.setVisible(true);
                }
                public void error(int code, String message) {
                }
              });
            } else
            if (JOptionPane.showOptionDialog(frame, "Покинув чат, вы не сможете вернуться в него обратно.", "Подтвердите действие", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] { "Отмена", "Выйти из чата" }, "Отмена") == 1) {
              //System.out.println("leaving");
              titleActionBtn.setVisible(false);
              sendPanel.setVisible(false);
              
              service.mainServer.call(new tl.messages.DeleteChatUser(currentPeer.chat_id, new InputUserSelf()), new RPCCallback<StatedMessage>() {
                public void done(StatedMessage result) {
                  service.chatManager.store(result.chats);
                  service.userManager.store(result.users);
                  service.messageManager.store(result.message);
                  service.dialogManager.addMessage(result.message);
                  messageListModel.addMessage(result.message);
                  service.dialogManager.updateContents();
                  restoreDialogSelection();
                  
                  titleActionBtn.setVisible(!(service.chatManager.get(currentPeer.chat_id) instanceof ChatForbidden));
                  titleActionBtn.setText("вернуться в чат");
                }
                public void error(int code, String message) {
                }
              });
            }
          } else {
            if (JOptionPane.showOptionDialog(frame, "Создать секретный чат с этим пользователем?", "Подтвердите действие", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] { "Отмена", "OK" }, "OK") == 1) {
              createEncryptedChat(currentPeer.user_id);
            }
          }
        }
      }
    });
    constraints = Utils.GBConstraints(4, 0, 1, 2);
    constraints.anchor = GridBagConstraints.PAGE_START;
    constraints.insets = new Insets(6, 0, 0, 0);
    titlePanel.add(titleActionBtn, constraints);
    
    titlePanel.add(Box.createRigidArea(new Dimension(6, 40)), Utils.GBConstraints(5, 0, 1, 2));
    
    messageList = new InteractiveList() {
      public boolean getScrollableTracksViewportWidth() {
        return true;
      }

      @Override
      public int getScrollableUnitIncrement(Rectangle visibleRect,
          int orientation, int direction) {
        //return super.getScrollableUnitIncrement(visibleRect, orientation, direction);
        return 30;
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
    scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.decode("0xccd5db")));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBackground(Color.decode("0xd6e4ef"));
    scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
        if (messageListModel != null && messageList != null && !e.getValueIsAdjusting()) {
          messageListModel.scrolled(messageList.getFirstVisibleIndex(), messageList.getLastVisibleIndex());
        }
      }
    });
    messageListModel = new MessageListModel(service, messageList, null);
    messageList.setModel(messageListModel);
    messageList.setCellRenderer(new MessageCellRenderer(service, null));
    messageList.setFocusable(false);
    contentPanel.add(scrollPane, BorderLayout.CENTER);
    
    sendPanel = new JPanel();
    sendPanel.setLayout(new BoxLayout(sendPanel, BoxLayout.X_AXIS));
    sendPanel.setBorder(new NinePatchBorder(Utils.getImage("input_border.png"), 12, 12, 12, 12, 10, 10, 11, 10));
    sendPanel.setMinimumSize(new Dimension(0, 1));
    contentPanel.add(sendPanel, BorderLayout.SOUTH);
    
    messageField = new JTextField();
    messageField.setBorder(new EmptyBorder(2, 2, 2, 2));
    addHint(messageField, "Новое сообщение...");
    sendPanel.add(messageField);
    //textArea.setBorder(new JTextField().getBorder());
    
    
    JButton attachBtn = new JButton(new ImageIcon(Utils.getImage("attach_photo.png")));
    attachBtn.setRolloverIcon(new ImageIcon(Utils.getImage("attach_photo_highlight.png")));
    attachBtn.setBorderPainted(false);
    attachBtn.setOpaque(false);
    attachBtn.setContentAreaFilled(false);
    attachBtn.setBorder(null);
    attachBtn.setPreferredSize(new Dimension(22, 18));
    attachBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    attachBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        /*int result = fc.showOpenDialog(frame);
        
        if (result == JFileChooser.APPROVE_OPTION) {
          sendFiles(fc.getSelectedFiles());
        }*/
        
        fd.setVisible(true); 
        /*try {
          File[] files = fd.getFiles();
          if (files != null && files.length > 0) {
            sendFiles(files);
          }
        } catch (Exception e1) {*/
          String filename = fd.getFile();
          if (filename != null) {
            sendFiles(new File[]{ new File(fd.getDirectory() + System.getProperty("file.separator") + fd.getFile()) });
          }
        //}
      }
    });
    sendPanel.add(attachBtn);
    
    sendPanel.setVisible(false);
    titlePanel.setVisible(false);
    
    chatPanel = new JPanel();
    layeredPane.add(chatPanel, JLayeredPane.PALETTE_LAYER);
    chatPanel.setLayout(new BorderLayout());
    chatPanel.setMinimumSize(new Dimension(30, 0));
    chatPanel.setMaximumSize(new Dimension(320, Integer.MAX_VALUE));
    chatPanel.setAlignmentX(0);
    chatPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("0xe0e0e0")));
    
    JPanel chatTitlePanel = new JPanel();
    //actionPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("0xe0e0e0")));
    chatTitlePanel.setBackground(Color.decode("0xf9f9f9"));
    chatTitlePanel.setLayout(new BoxLayout(chatTitlePanel, BoxLayout.X_AXIS));
    chatPanel.add(chatTitlePanel, BorderLayout.NORTH);
    chatTitlePanel.add(Box.createRigidArea(new Dimension(4, 40)));
    
    chatTitleField = new JTextField();
    chatTitleField.setAlignmentY(Component.CENTER_ALIGNMENT);
    chatTitleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    chatTitlePanel.add(chatTitleField);
    addHint(chatTitleField, "Название чата");
    chatTitlePanel.add(Box.createRigidArea(new Dimension(4, 40)));
    
    chatMemberList = new InteractiveList() {
      public boolean getScrollableTracksViewportWidth() {
        return true;
      }
    };
    chatMemberList.setBackground(Color.WHITE);
    memberListRenderer = new ContactListRenderer(service);
    chatMemberList.setCellRenderer(memberListRenderer);
    chatMemberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    /*chatMemberList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (chatMemberList.getSelectedIndex() > -1 && !chatMemberModel.isEmpty()) {
          chatMemberModel.remove(chatMemberList.getSelectedIndex());
          chatMemberList.clearSelection();
          chatActionBtn.setEnabled(!chatTitleField.getForeground().equals(Color.LIGHT_GRAY) && !chatTitleField.getText().isEmpty() && !chatMemberModel.isEmpty());
        }
      }
    });*/
    
    scrollPane = new JScrollPane(chatMemberList);
    scrollPane.setBackground(Color.decode("0xf9f9f9"));
    scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.decode("0xe0e0e0")));
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    chatPanel.add(scrollPane, BorderLayout.CENTER);
    
    JPanel chatActionPanel = new JPanel();
    //actionPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("0xe0e0e0")));
    chatActionPanel.setBackground(Color.decode("0xf9f9f9"));
    chatActionPanel.setLayout(new BoxLayout(chatActionPanel, BoxLayout.X_AXIS));
    chatPanel.add(chatActionPanel, BorderLayout.SOUTH);
    chatActionPanel.add(Box.createRigidArea(new Dimension(6, 40)));
    
    chatActionBtn = new JButton("Создать чат");
    chatActionBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    chatActionBtn.setFocusable(false);
    //chatActionBtn.putClientProperty("JButton.buttonType", "gradient");
    chatActionBtn.putClientProperty("JButton.buttonType", "segmentedCapsule");
    chatActionBtn.putClientProperty("JButton.segmentPosition", "only");
    chatActionBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentPeer != null && (currentPeer instanceof InputPeerChat) && titleInfoBtn.isSelected()) {
          saveChat();
        } else {
          doCreateChat();
        }
      }
    });
    chatActionPanel.add(chatActionBtn);
    chatActionPanel.add(Box.createRigidArea(new Dimension(6, 40)));
    
    chatTitleField.getDocument().addDocumentListener(new DocumentListener() {
      public void removeUpdate(DocumentEvent e) {
        chatActionBtn.setEnabled(!chatTitleField.getForeground().equals(Color.LIGHT_GRAY) && !chatTitleField.getText().isEmpty() && !chatMemberModel.isEmpty());
      }
      public void insertUpdate(DocumentEvent e) {
        chatActionBtn.setEnabled(!chatTitleField.getForeground().equals(Color.LIGHT_GRAY) && !chatTitleField.getText().isEmpty() && !chatMemberModel.isEmpty());
      }
      public void changedUpdate(DocumentEvent e) {
        chatActionBtn.setEnabled(!chatTitleField.getForeground().equals(Color.LIGHT_GRAY) && !chatTitleField.getText().isEmpty() && !chatMemberModel.isEmpty());
      }
    });
    
    chatPanel.setVisible(false);
    
    messageField.addKeyListener(new KeyListener() {
      
      @Override
      public void keyTyped(KeyEvent e) {
        if (currentPeer != null && !messageField.getText().isEmpty()) {
          service.typingManager.startTyping(currentPeer);
        }
      }
      
      @Override
      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          String message = messageField.getText();
          if (currentPeer != null && message.length() > 0 && !messageField.getForeground().equals(Color.LIGHT_GRAY)) {
            sendMessage(message, currentPeer, currentEncryptedChat);
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
        if (currentPeer == null) return;
        
        dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
      }
      
      public void drop(DropTargetDropEvent dtde) {
        if (currentPeer == null) return;
        
        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        
        Transferable t = dtde.getTransferable();
        try {
          java.util.List<File> fileList = (java.util.List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
          
          sendFiles(fileList.toArray(new File[1]));
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        dtde.dropComplete(true);
      }
      
      public void dragOver(DropTargetDragEvent dtde) {
        if (currentPeer == null) return;
        
        dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
      }
      
      public void dragExit(DropTargetEvent dte) {
      }
      
      public void dragEnter(DropTargetDragEvent dtde) {
        if (currentPeer == null) return;
        
        dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);        
      }
    });
    
    dialogList.addAncestorListener( new RequestFocusListener() );
    

    if (!System.getProperty("os.name").contains("Mac")) {
      addHint(searchField, "Найти...");
    }
    
    service.mainServer.call(new tl.account.UpdateStatus(false));
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      public void run() {
        service.mainServer.call(new tl.account.UpdateStatus(true));
      }
    }));
  }
  
  protected void createEncryptedChat(final int user_id) {
    final TUser user = service.userManager.get(user_id);
    service.mainServer.call(new tl.messages.GetDhConfig(0, 256), new RPCCallback<DhConfig>() {
      public void done(final DhConfig config) {
        final byte[] random = new byte[256];
        (new Random()).nextBytes(random); // TODO: add more entropy
        for (int i = 0; i < random.length; i++) {
          random[i] ^= config.random[i];
        }
        
        BigInteger g = BigInteger.valueOf(config.g);
        BigInteger a = new BigInteger(1, random);
        BigInteger dh_prime = new BigInteger(1, config.p);
        
        byte[] g_a = g.modPow(a, dh_prime).toByteArray();
        
        int random_id = (int) (Math.random() * 10000000);
        service.mainServer.call(new tl.messages.RequestEncryption(Utils.getInputUser(user), random_id, g_a), new RPCCallback<TEncryptedChat>() {
          public void done(TEncryptedChat chat) {
            service.dialogManager.addEncryptedChat(user_id, chat, random, config.p);
            selectDialog(user, chat, new byte[256]);
          }
          public void error(int code, String message) {
            if (message.equals("PARTICIPANT_VERSION_OUTDATED")) {
              JOptionPane.showMessageDialog(frame, "Не удается создать секретный чат: собеседник использует устаревшую версию приложения.");
            }
            System.out.println("Failed to create encrypted chat: " + code + ", " + message);
          }
        });
      }
      public void error(int code, String message) {
        System.out.println("Failed to get DH config: " + code + ", " + message);
      }
    });
  }

  protected void toggleTitlePanel(boolean visible) {
    titlePanel.setMinimumSize(new Dimension(0, visible ? 100 : 40));
    titlePanel.setPreferredSize(new Dimension(0, visible ? 100 : 40));
    titleIcon.setMinimumSize(new Dimension(visible ? 100 : 40, visible ? 100 : 40));
    titleIcon.setPreferredSize(new Dimension(visible ? 100 : 40, visible ? 100 : 40));
    titleIcon.setBorder(new EmptyBorder(visible ? 6 : 4, visible ? 6 : 4, visible ? 6 : 4, visible ? 6 : 4));
    
    int user_id = currentPeer.user_id;
    TUser user = service.userManager.get(user_id);
    String title = (user.first_name + " " + user.last_name).trim();
    
    titleLabel.setBorder(new EmptyBorder(visible ? 8 : 3, 0, 0, 0));
    titleLabel.setText(visible ? "<html>" + title + "<br/><br/>" + Utils.formatPhone(user.phone) + "</html>" : title);
  }

  protected void editChat() {
    contactsState = contactsBtn.isSelected();
    contactsBtn.doClick();
    
    searchField.setText("");
    chatPanel.setVisible(true);
    dialogsBtn.setVisible(false);
    contactsBtn.setVisible(false);
    actionBtn.setText("Отмена");
    chatActionBtn.setEnabled(false);
    chatActionBtn.setText("Применить");
    
    chatMemberModel = new ContactListModel(service, chatMemberList);
    chatMemberModel.setMissingText("Выберите участников из списка слева");
    if (currentChat != null) {
      chatMemberModel.add(currentChat.participants.participants);
    }
    memberListRenderer.buttonText = "исключить";
    memberListRenderer.dropCache();
    contactListRenderer.buttonText = "пригласить";
    contactListRenderer.dropCache();
    memberListRenderer.actionListener = new ContactListRenderer.ContactActionListener() {
      public void onContactSelected(final int user_id, int index) {
        if (JOptionPane.showOptionDialog(frame, "Вы уверены, что хотите исключить участника из чата?", "Подтвердите действие", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] { "Отмена", "Исключить из чата" }, "Отмена") == 1) {
          chatMemberModel.remove(index);
          
          TUser user = service.userManager.get(user_id);
          service.mainServer.call(new tl.messages.DeleteChatUser(currentPeer.chat_id, user instanceof UserContact ? new InputUserContact(user_id) : new InputUserForeign(user_id, user.access_hash)), new RPCCallback<StatedMessage>() {
            public void done(StatedMessage result) {
              service.chatManager.store(result.chats);
              service.userManager.store(result.users);
              service.messageManager.store(result.message);
              service.dialogManager.addMessage(result.message);
              messageListModel.addMessage(result.message);
              contactListRenderer.dropCache(user_id);
              contactListModel.updateContents(user_id);
              service.dialogManager.updateContents();
              restoreDialogSelection();
              
              service.mainServer.call(new GetFullChat(currentPeer.chat_id), new Server.RPCCallback<tl.messages.ChatFull>() {
                public void done(tl.messages.ChatFull result) {
                  if (result.full_chat.id == currentPeer.chat_id) {
                    service.userManager.store(result.users);
                    service.chatManager.store(result.chats);
                    currentChat = (ChatFull) result.full_chat;
                    updateStatus();
                  }
                }
                public void error(int code, String message) {
                  
                }
              });
            }
            public void error(int code, String message) {
            }
          });
        }
      }
      public boolean isVisible(int user_id, int index) {
        if (currentChat == null || currentChat.participants == null) return false;
        
        if (currentChat.participants.admin_id == service.me.id || user_id == service.me.id) {
          return true;
        }
        
        for (TChatParticipant part : currentChat.participants.participants) {
          if (part.user_id == user_id && part.inviter_id == service.me.id) {
            return true;
          }
        }
        
        return false;
      }
    };
    contactListRenderer.actionListener = new ContactListRenderer.ContactActionListener() {
      public void onContactSelected(final int user_id, int index) {
        chatMemberModel.add(user_id, true);
          
        TUser user = service.userManager.get(user_id);
        service.mainServer.call(new tl.messages.AddChatUser(currentPeer.chat_id, user instanceof UserContact ? new InputUserContact(user_id) : new InputUserForeign(user_id, user.access_hash), 10), new RPCCallback<StatedMessage>() {
          public void done(StatedMessage result) {
            service.chatManager.store(result.chats);
            service.userManager.store(result.users);
            service.messageManager.store(result.message);
            service.dialogManager.addMessage(result.message);
            messageListModel.addMessage(result.message);
            contactListRenderer.dropCache(user_id);
            contactListModel.updateContents(user_id);
            service.dialogManager.updateContents();
            restoreDialogSelection();
            
            service.mainServer.call(new GetFullChat(currentPeer.chat_id), new Server.RPCCallback<tl.messages.ChatFull>() {
              public void done(tl.messages.ChatFull result) {
                if (result.full_chat.id == currentPeer.chat_id) {
                  service.userManager.store(result.users);
                  service.chatManager.store(result.chats);
                  currentChat = (ChatFull) result.full_chat;
                  updateStatus();
                }
              }
              public void error(int code, String message) {
                
              }
            });
          }
          public void error(int code, String message) {
          }
        });
      }

      @Override
      public boolean isVisible(int user_id, int index) {
        if (currentChat == null || currentChat.participants == null) return false;
        
        for (TChatParticipant part : currentChat.participants.participants) {
          if (part.user_id == user_id) {
            return false;
          }
        }
        
        return true;
      }
    };
    chatMemberList.setModel(chatMemberModel);
    
    TChat chat = service.chatManager.get(currentPeer.chat_id);
    setHintedFieldText(chatTitleField, chat.title);
    
    //chatTitleField.requestFocusInWindow();
  }

  protected void saveChat() {
    if (currentPeer == null || !(currentPeer instanceof InputPeerChat)) return;
    
    final String title = chatTitleField.getText();
    if (title.length() == 0) return;
    chatActionBtn.setEnabled(false);
    
    service.mainServer.call(new tl.messages.EditChatTitle(currentPeer.chat_id, title), new RPCCallback<StatedMessage>() {

      public void done(final StatedMessage result) {
        service.chatManager.store(result.chats);
        service.userManager.store(result.users);
        service.messageManager.store(result.message);
        service.dialogManager.addMessage(result.message);
        messageListModel.addMessage(result.message);
        service.dialogManager.updateContents();
        titleLabel.setText(title);
        cancelCreateChat();
      }

      public void error(int code, String message) {
        //
      }
    });
  }

  private boolean contactsState;
  private JButton chatActionBtn;
  private JList chatMemberList;
  private ContactListModel chatMemberModel;
  private JPanel titlePanel;
  private ImagePanel titleIcon;
  private JPanel contentPanel;
  private JTextField chatTitleField;
  protected void cancelCreateChat() {
    searchField.setText("");
    chatPanel.setVisible(false);
    dialogsBtn.setVisible(true);
    contactsBtn.setVisible(true);
    
    memberListRenderer.buttonText = null;
    memberListRenderer.dropCache();
    contactListRenderer.buttonText = null;
    contactListRenderer.dropCache();
    
    if (contactsState) {
      contactsBtn.doClick();
    } else {
      dialogsBtn.doClick();
    }
    
    if (currentPeer != null && currentPeer instanceof InputPeerChat) {
      titleInfoBtn.setSelected(false);
    }
  }

  protected void doCreateChat() {
    chatActionBtn.setEnabled(false);
    String title = chatTitleField.getText();
    
    if (title.length() == 0) return;
    
    TInputUser[] users = new TInputUser[chatMemberModel.getSize()];
    for (int i = 0; i < users.length; i++) {
      users[i] = Utils.getInputUser(service.userManager.get((Integer) chatMemberModel.getElementAt(i)));
    }
    service.mainServer.call(new CreateChat(users, title), new RPCCallback<StatedMessage>() {

      public void done(final StatedMessage result) {
        service.chatManager.store(result.chats);
        service.userManager.store(result.users);
        service.dialogManager.addMessage(result.message);
        service.dialogManager.updateContents();
        
        selectDialog(result.chats[0]);
        cancelCreateChat();
        
        messageField.requestFocusInWindow();
      }

      public void error(int code, String message) {
        //
      }
    });
  }

  
  protected void createChat() {
    contactsState = contactsBtn.isSelected();
    contactsBtn.doClick();
    
    searchField.setText("");
    chatPanel.setVisible(true);
    dialogsBtn.setVisible(false);
    contactsBtn.setVisible(false);
    actionBtn.setText("Отмена");
    chatActionBtn.setEnabled(false);
    chatActionBtn.setText("Создать чат");
    
    chatMemberModel = new ContactListModel(service, chatMemberList);
    chatMemberModel.setMissingText("Выберите участников из списка слева");
    
    memberListRenderer.buttonText = "удалить";
    memberListRenderer.dropCache();
    contactListRenderer.buttonText = "добавить";
    contactListRenderer.dropCache();
    memberListRenderer.actionListener = new ContactListRenderer.ContactActionListener() {
      public void onContactSelected(final int user_id, int index) {
        chatMemberModel.remove(index);
        contactListRenderer.dropCache(user_id);
        contactListModel.updateContents(user_id);
      }
      public boolean isVisible(int user_id, int index) {
        return true;
      }
    };
    contactListRenderer.actionListener = new ContactListRenderer.ContactActionListener() {
      public void onContactSelected(final int user_id, int index) {
        chatMemberModel.add(user_id, true);
        contactListRenderer.dropCache(user_id);
        contactListModel.updateContents(user_id);
      }

      public boolean isVisible(int user_id, int index) {
        return !chatMemberModel.contains(user_id);
      }
    };
    
    chatMemberList.setModel(chatMemberModel);
    
    chatTitleField.requestFocusInWindow();
  }


  protected void importContacts() {
    importContacts(false);
  }
  protected void importContacts(boolean replace) {
    importDialog.setVisible(true);
    String filename = importDialog.getFile();
    
    ArrayList<String[]> contacts = new ArrayList<String[]>();
    try {
      BufferedReader cin = new BufferedReader(new InputStreamReader(new FileInputStream(importDialog.getDirectory() + System.getProperty("file.separator") + importDialog.getFile()), "UTF8"));
      String line = cin.readLine();
      
      if (line != null) {
        boolean vcf = line.trim().equals("BEGIN:VCARD") || filename.endsWith(".vcf");
        
        if (vcf) {
          String phone = null;
          String fname = "";
          String lname = "";
          while (true) {
            line = cin.readLine();
            if (line == null) break;
            
            if (line.trim().equals("END:VCARD")) {
              if (phone != null) {
                contacts.add(new String[] { phone, fname, lname });
              }
              phone = null;
              fname = "";
              lname = "";
            } else
            if (line.startsWith("N:")) {
              
              String[] comp = line.split(":", -1)[1].split(";", -1);
              lname = comp[0];
              fname = comp[1];
            } else
            if (line.startsWith("TEL:") || line.startsWith("TEL;")) {
              String[] comp = line.split(":", -1);
              phone = comp[1];
            }
          }
        } else {
          String[] comp = line.split(",", -1);
          int phoneIndex = -1;
          int fnameIndex = -1;
          int lnameIndex = -1;
          for (int i = 0; i < comp.length; i++) {
            String head = comp[i].trim().toLowerCase();
            if (head.equals("first name") || head.equals("given name")) {
              fnameIndex = i;
            } else
            if (head.equals("last name") || head.equals("family name")) {
              lnameIndex = i;
            } else
            if (head.equals("primary phone")) {
              phoneIndex = i;
            }
          }
          
          if (phoneIndex > -1) {
            while (true) {
              line = cin.readLine();
              if (line == null) break;
              comp = line.split(",", -1);
              String[] cont = new String[] { phoneIndex > -1 ? comp[phoneIndex] : "", fnameIndex > -1 ? comp[fnameIndex] : "", lnameIndex > -1 ? comp[lnameIndex] : "" };
              
              if (!cont[0].isEmpty()) {
                contacts.add(cont);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    for (String[] ss : contacts) {
      System.out.println("phone: " + ss[0] + ", fn: " + ss[1] + ", ln: " + ss[2]);
    }
    if (contacts.size() > 0) {
      importContacts(contacts, replace);
    }
  }

  protected void addContact() {
    JTextField phoneField = new JTextField(16);
    phoneField.setText("+7");
    phoneField.setCaretPosition(2);
    JTextField firstField = new JTextField(16);
    JTextField lastField = new JTextField(16);

    JPanel myPanel = new JPanel();
    GridBagConstraints constraints;
    myPanel.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));
    myPanel.setLayout(new GridBagLayout());
    JLabel label = new JLabel("<html>Укажите телефон и имя нового контакта. Если он уже зарегистрирован в Telegram, он будет добавлен в ваш список контактов.</html>");
    label.setMaximumSize(new Dimension(320, Integer.MAX_VALUE));
    label.setPreferredSize(new Dimension(320, 70));
    myPanel.add(label, Utils.GBConstraints(0, 0, 2, 1));
    constraints = Utils.GBConstraints(0, 1);
    constraints.anchor = GridBagConstraints.LINE_END;
    myPanel.add(new JLabel("Телефон:"), constraints);
    myPanel.add(phoneField, Utils.GBConstraints(1, 1));
    constraints = Utils.GBConstraints(0, 2);
    constraints.anchor = GridBagConstraints.LINE_END;
    myPanel.add(new JLabel("Имя:"), constraints);
    myPanel.add(firstField, Utils.GBConstraints(1, 2));
    constraints = Utils.GBConstraints(0, 3);
    constraints.anchor = GridBagConstraints.LINE_END;
    myPanel.add(new JLabel("Фамилия:"), constraints);
    myPanel.add(lastField, Utils.GBConstraints(1, 3));

    phoneField.addAncestorListener(new RequestFocusListener());
    int result = JOptionPane.showConfirmDialog(frame, myPanel, 
             "Добавление контакта", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      ArrayList<String[]> list = new ArrayList<String[]>();
      list.add(new String[] { phoneField.getText(), firstField.getText(), lastField.getText() });
      importContacts(list);
    }

  }

  private boolean preventHintUpdates = false; // Hack
  private ContactListRenderer memberListRenderer;
  private void addHint(final JTextField field, final String hint) {
    field.setForeground(Color.LIGHT_GRAY);
    field.setText(hint);
    field.addFocusListener(new FocusListener() {
      public void focusLost(FocusEvent e) {
        /*if (field.getText().isEmpty()) {
          field.setForeground(Color.LIGHT_GRAY);
          field.setText(hint);
        }*/
      }
      public void focusGained(FocusEvent e) {
        if (field.getForeground().equals(Color.LIGHT_GRAY)) {
          /*field.setForeground(Color.BLACK);
          field.setText("");*/
          field.setCaretPosition(0);
        }
      }
    });
    field.addCaretListener(new CaretListener() {
      public void caretUpdate(CaretEvent e) {
        if (field.getForeground().equals(Color.LIGHT_GRAY) && e.getDot() > 0) {
          field.setCaretPosition(0);
        }         
      }
    });
    field.getDocument().addDocumentListener(new DocumentListener() {
      boolean updating = false;
      
      public void removeUpdate(final DocumentEvent e) {
        if (updating || preventHintUpdates) return;
        if (field.getText().isEmpty()) {
          updating = true;
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              field.setForeground(Color.LIGHT_GRAY);
              field.setText(hint);
              field.setCaretPosition(0);
              updating = false;
            }
          });
        }
      }
      
      @Override
      public void insertUpdate(final DocumentEvent e) {
        if (updating || preventHintUpdates) return;
        if (!field.getText().isEmpty() && field.getForeground().equals(Color.LIGHT_GRAY)) {
          updating = true;
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              field.setForeground(Color.BLACK);
              field.setText(field.getText().substring(e.getOffset(), e.getOffset() + e.getLength()));
              updating = false;
            }
          });
        }
      }
      
      @Override
      public void changedUpdate(final DocumentEvent e) {
        if (updating || preventHintUpdates) return;
        if (field.getText().isEmpty()) {
          updating = true;
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              field.setForeground(Color.LIGHT_GRAY);
              field.setText(hint);
              field.setCaretPosition(0);
              updating = false;
            }
          });
        } else
        if (!field.getText().isEmpty() && field.getForeground().equals(Color.LIGHT_GRAY)) {
          updating = true;
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              field.setForeground(Color.BLACK);
              field.setText(field.getText().substring(e.getOffset(), e.getOffset() + e.getLength()));
              updating = false;
            }
          });
        }
      }
    });
  }

  public void sendMessage(String message, TInputPeer inputPeer, int encryptedChat) {    
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
    messageListModel.addMessage(newmsg);
    restoreDialogSelection();
    dialogList.repaint();
    messageList.repaint();
    
    TDecryptedMessage decrypted = null;
    if (encryptedChat > -1) {
      byte[] random_bytes;
      if (message.length() > 16) {
        random_bytes = new byte[0];
      } else {
        random_bytes = new byte[16];
        (new Random()).nextBytes(random_bytes);
      }
      decrypted = new DecryptedMessage(random_id, random_bytes, message, new DecryptedMessageMediaEmpty());
      sendEncryptedMessage(peer_id, encryptedChat, random_id, decrypted, newmsg);
      return;
    } else {
      service.dialogManager.addMessage(newmsg);
    }
    
    service.mainServer.call(new SendMessage(currentPeer, message, random_id), new RPCCallback<SentMessage>() {
      public void done(SentMessage result) {
        newmsg.id = result.id;
        newmsg.date = result.date;
        newmsg.sending = false;
        service.messageManager.store(newmsg);
        restoreDialogSelection();
        dialogList.repaint();
        messageListModel.updateContentsID(result.id);
      } 
      public void error(int code, String message) {
        newmsg.failed = true;
      }
    });
  }
  public void sendEncryptedMessage(int user_id, final int encryptedChat, final int random_id, final TDecryptedMessage decrypted, final TMessage stub) {
    EncryptedDialog dialog = service.dialogManager.chats.get(encryptedChat);
    if (dialog != null) {
      if (dialog.chat instanceof EncryptedChat) {
        try {
          //DecryptedMessageLayer layer = new DecryptedMessageLayer(8, decrypted);
          TDecryptedMessage layer = decrypted;
          int size = 4 + layer.length(true);
          while (size % 16 != 0) size++;
          
          ByteBuffer inner = ByteBuffer.allocate(size);
          inner.order(ByteOrder.LITTLE_ENDIAN);
          inner.putInt(layer.length(true));
          layer.writeTo(inner, true);
          while (inner.position() < size) inner.put((byte) 0);
          inner.rewind();
          
          byte[] msg_key = substr(SHA1(inner, 0, inner.capacity()), 4, 16);
          
          int x = 0;
          byte[] sha1_a = SHA1(concat(msg_key, substr(dialog.key, x, 32)));
          byte[] sha1_b = SHA1(concat(substr(dialog.key, 32 + x, 16), msg_key, substr(dialog.key, 48 + x, 16)));
          byte[] sha1_c = SHA1(concat(substr(dialog.key, 64 + x, 32), msg_key));
          byte[] sha1_d = SHA1(concat(msg_key, substr(dialog.key, 96 + x, 32)));
          
          byte[] aes_key = concat(substr(sha1_a, 0, 8), substr(sha1_b, 8, 12), substr(sha1_c, 4, 12));
          byte[] aes_iv = concat(substr(sha1_a, 8, 12), substr(sha1_b, 0, 8), substr(sha1_c, 16, 4), substr(sha1_d, 0, 8));
          
          ByteBuffer buffer = ByteBuffer.allocate(size + 24);
          buffer.order(ByteOrder.LITTLE_ENDIAN);
          buffer.putLong(dialog.chat.key_fingerprint);
          buffer.put(msg_key);
          buffer.put(AESEncrypt(inner, 0, size, aes_key, aes_iv));
          buffer.rewind();
          
          final byte[] bytes = new byte[buffer.capacity()];
          buffer.get(bytes);
          
          //TEncryptedMessage encrypted = new EncryptedMessage(random_id, dialog.chat.id, (int) ((new Date()).getTime() / 1000), bytes, new EncryptedFileEmpty());
          //service.dialogManager.addEncryptedMessage(encryptedChat, service.me.id, encrypted, decrypted);
          
          service.mainServer.call(new SendEncrypted(new InputEncryptedChat(encryptedChat, dialog.chat.access_hash), random_id, bytes), new RPCCallback<TSentEncryptedMessage>() {
            public void done(TSentEncryptedMessage result) {
              TEncryptedMessage encrypted = new EncryptedMessage(random_id, encryptedChat, result.date, bytes, new EncryptedFileEmpty());
              service.dialogManager.addEncryptedMessage(encryptedChat, service.me.id, encrypted, decrypted);
              stub.date = result.date;
              stub.sending = false;
              restoreDialogSelection();
              dialogList.repaint();
              messageListModel.updateContentsID(random_id);
            } 
            public void error(int code, String message) {
              stub.failed = true;
              System.out.println("Error while sending encrypted message: " + code + " (" + message + ")");
            }
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } 
  }
  
  public void selectDialog(TChat chat) {
    selectDialog(new InputPeerChat(chat.id), null, null);
  }
  
  public void selectDialog(TUser user, TEncryptedChat chat, byte[] key) {
    TInputPeer peer = null;
    if (user instanceof UserForeign || user instanceof UserRequest) {
      peer = new InputPeerForeign(user.id, user.access_hash);
    } else {
      peer = new InputPeerContact(user.id);
    }
    selectDialog(peer, chat, key);
  }
  
  public void selectDialog(final TInputPeer peer, TEncryptedChat echat, byte[] key) {
    if (currentPeer != null &&
        ((peer instanceof InputPeerChat && currentPeer instanceof InputPeerChat && peer.chat_id == currentPeer.chat_id) ||
         (!(peer instanceof InputPeerChat) && !(currentPeer instanceof InputPeerChat) && peer.user_id == currentPeer.user_id))) {
      return;
    }
    
    currentPeer = peer;
    currentEncryptedChat = (echat == null) ? -1 : echat.id;
    messageListModel = new MessageListModel(service, messageList, peer, echat == null ? null : service.dialogManager.chats.get(echat.id));
    
    messageList.setModel(messageListModel);
    messageList.setCellRenderer(new MessageCellRenderer(service, peer));
    
    titleInfoBtn.setSelected(false);
    toggleTitlePanel(false);
    
    if (peer instanceof InputPeerChat) {
      int chat_id = peer.chat_id;
      TChat chat = service.chatManager.get(chat_id);
      //System.out.println(chat);
      service.chatManager.getImage(chat_id, titleIcon, false);
      
      titleLabel.setText(chat.title.trim());
      
      service.mainServer.call(new GetFullChat(chat_id), new Server.RPCCallback<tl.messages.ChatFull>() {
        public void done(tl.messages.ChatFull result) {
          if (result.full_chat.id == currentPeer.chat_id) {
            service.userManager.store(result.users);
            service.chatManager.store(result.chats);
            currentChat = (ChatFull) result.full_chat;
            updateStatus();
          }
        }
        public void error(int code, String message) {
          
        }
      });

      titleInfoBtn.setText("управление...");
      titleActionBtn.setText(chat.left ? "вернуться в чат" : "покинуть чат");
      titleActionBtn.setVisible(!(chat instanceof ChatForbidden));

      sendPanel.setVisible(!(chat instanceof ChatForbidden) && !chat.left);
      titleInfoBtn.setVisible(!(chat instanceof ChatForbidden) && !chat.left);
    } else {
      int user_id = peer.user_id;
      TUser user = service.userManager.get(user_id);
      service.userManager.getUserpic(user_id, titleIcon, false);
      
      titleLabel.setText((user.first_name + " " + user.last_name).trim());
      
      titleInfoBtn.setText("информация...");
      titleInfoBtn.setVisible(true);
      titleActionBtn.setText("создать секретный чат");
      titleActionBtn.setVisible(true);

      sendPanel.setVisible((echat == null) || (echat instanceof EncryptedChat));
    }
    currentChat = null;
    updateStatus();
    
    service.typingManager.callback = this;
    
    titlePanel.setVisible(true);
  }
  
  public void updateStatus() {
    if (currentPeer == null) return;
    if (currentPeer instanceof InputPeerChat) {
      int chat_id = currentPeer.chat_id;
      TChat chat = service.chatManager.get(chat_id);
      String status = service.typingManager.getStatus(-chat_id, true);
      if (status == null) {
        String online = "";
        if (currentChat != null) {
          int num = Utils.getChatOnline(service, currentChat);
          if (num > 0) {
            online = ", " + num + " в сети";
          }
        }
        titleStatus.setText(Utils.num(chat.participants_count, new String[] { " участник", " участника", " участников" }, true) + online);
      } else {
        titleStatus.setText(status);
      }
    } else {
      int user_id = currentPeer.user_id;
      TUser user = service.userManager.get(user_id);
      service.userManager.getUserpic(user_id, titleIcon, false);
      
      String status = service.typingManager.getStatus(user_id, true);
      titleStatus.setText(status == null ? Utils.toStatus(user.status, true) : status);
    }
  }
  
  public void selectDialog(tl.Dialog dialog) {
    if (dialog instanceof EncryptedDialog) {
      selectDialog(service.userManager.get(((PeerUser) dialog.peer).user_id), ((EncryptedDialog) dialog).chat, ((EncryptedDialog) dialog).key);
    } else
    if (dialog.peer instanceof PeerUser) {
      selectDialog(service.userManager.get(((PeerUser) dialog.peer).user_id), null, null);
    } else {
      selectDialog(new InputPeerChat(((PeerChat) dialog.peer).chat_id), null, null);
    }
  }

  public void authorized(TUser user) {
    service.logged((UserSelf) user);
    service.dialogManager.reloadDialogs();
    contactListModel.reload();
  }

  @Override
  public void onNewMessage(TMessage message, boolean fresh) {
    // TODO Auto-generated method stub
    restoreDialogSelection();
    service.dialogManager.updateContents();
    if (currentPeer != null &&
        ((message.to_id instanceof PeerChat && currentPeer instanceof InputPeerChat && message.to_id.chat_id == currentPeer.chat_id) ||
         (message.to_id instanceof PeerUser && !(currentPeer instanceof InputPeerChat) && (message.to_id.user_id == currentPeer.user_id || message.from_id == currentPeer.user_id)))) {
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
    if (currentPeer == null) return;
    //dialogListModel.updateContentsID(messages);
    messageListModel.updateContentsID(messages);
  }

  @Override
  public void onDeleteMessages(int[] messages, boolean fresh) {
    restoreDialogSelection();
    if (currentPeer == null) return;
    //dialogListModel.updateContents();
  }

  @Override
  public void onRestoreMessages(int[] messages, boolean fresh) {
    // TODO Auto-generated method stub
    restoreDialogSelection();
    if (currentPeer == null) return;
    //dialogListModel.updateContents();
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
    contactListRenderer.dropCache(user_id);
    memberListRenderer.dropCache(user_id);
    contactListModel.updateContents(user_id);
    updateStatus();
  }

  @Override
  public void onUserName(int user_id, String first_name, String last_name,
      boolean fresh) {
    contactListRenderer.dropCache(user_id);
    memberListRenderer.dropCache(user_id);
    contactListModel.updateContents(user_id);
  }

  @Override
  public void onUserPhoto(int user_id, TUserProfilePhoto photo, boolean fresh) {
    contactListRenderer.dropCache(user_id);
    memberListRenderer.dropCache(user_id);
    contactListModel.updateContents(user_id);
  }

  @Override
  public void onContactRegistered(int user_id, int date, boolean fresh) {
    contactListModel.add(user_id, true);
  }

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
      if (service.dialogManager.isEmpty()) return;
      
      for (int i = 0; i < service.dialogManager.getSize(); i++) {
        tl.Dialog d = (tl.Dialog) service.dialogManager.getElementAt(i);
        if ((currentEncryptedChat == -1 && !(d instanceof EncryptedDialog)) ||
            (currentEncryptedChat > -1 && d instanceof EncryptedDialog && currentEncryptedChat == ((EncryptedDialog) d).chat.id)) {
          if ((d.peer instanceof PeerChat && currentPeer instanceof InputPeerChat && d.peer.chat_id == currentPeer.chat_id) ||
              (!(d.peer instanceof PeerChat) && !(currentPeer instanceof InputPeerChat) && d.peer.user_id == currentPeer.user_id)) {
            dialogList.setSelectedIndex(i);
            break;
          }
        }
      }
    } else {
      if (contactListModel.isEmpty()) return;
      
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

  private void sendFile(final File file) {
    if (currentPeer == null) return;
    
    final TInputPeer peer = currentPeer;
    final int random_id = -(new Random()).nextInt(0x10000000);
    
    boolean isPhoto = false;
    Image bitmap = null;
    TPhotoSize size = null;
    try {
      bitmap = ImageIO.read(file);
      isPhoto = bitmap != null;
      if (isPhoto) {
        size = new PhotoSize("x", new FileLocation(service.mainServerID, 0, 0, random_id), bitmap.getWidth(null), bitmap.getHeight(null), 0);
      }
    } catch (Exception e) {
      // not image
    }
    
    int peer_id = Utils.getPeerID(peer, service.me);
    final TMessage futureMessage = new Message(
        random_id,
        service.me.id,
        peer_id > 0 ? new PeerUser(peer_id) : new PeerChat(-peer_id),
        true,
        true,
        (int) (System.currentTimeMillis() / 1000),
        "",
        isPhoto ?
            new MessageMediaPhoto(new Photo(random_id, 0, service.me.id, (int) (System.currentTimeMillis() / 1000), "", new GeoPointEmpty(), new TPhotoSize[] { size })) :
            new MessageMediaVideo(new Video(random_id, 0, service.me.id, (int) (System.currentTimeMillis() / 1000), "", 0, 0, size, service.mainServerID, 0, 0)));
    
    futureMessage.sending = true;
    futureMessage.preview = isPhoto ? bitmap : null;
    
    service.messageManager.store(futureMessage);
    service.dialogManager.addMessage(futureMessage);
    messageListModel.addMessage(futureMessage);
    restoreDialogSelection();
    
    // 2. start upload
    //ByteArrayOutputStream output = new ByteArrayOutputStream();
    //bitmap.compress(CompressFormat.JPEG, 85, output);
    
    try {
      service.fileManager.upload(random_id, IOUtils.readFully(new FileInputStream(file), -1, true), file.getName(), new FileUploadingProgressiveCallback() {
        public void fail() {
          System.out.println("Unable to upload photo");
          
          futureMessage.failed = true;
          if (currentPeer != null && Utils.getPeerID(currentPeer, service.me) == Utils.getPeerID(peer, service.me)) {
            messageListModel.updateContents();
          }
        }
        
        public void progress(int loaded, int size, final float percent) {
          /*if (futureMessage.row != null && futureMessage.row.get() != null) {
            ProgressBar progress = ViewHolder.get(futureMessage.row.get(), R.id.progress);
            progress.setProgress((int) (percent * 65535));
          }*/
        }
        
        public void complete(TInputFile ifile) {
          System.out.println("uploaded " + file.getName());
          // 3. send message with uploaded image to server
          service.mainServer.call(new SendMedia(peer, new InputMediaUploadedPhoto(ifile), random_id), new RPCCallback<StatedMessage>() {
            public void done(StatedMessage result) {
              // 4. replace fake message with real one
              futureMessage.id = result.message.id;
              futureMessage.date = result.message.date;
              futureMessage.sending = false;
              futureMessage.media = result.message.media;
              //futureMessage.preview = null;
              service.messageManager.store(futureMessage);
              
              if (currentPeer != null && Utils.getPeerID(currentPeer, service.me) == Utils.getPeerID(peer, service.me)) {
                messageListModel.updateContents();
              }
            }
            public void error(int code, String message) {
              futureMessage.failed = true;
              if (currentPeer != null && Utils.getPeerID(currentPeer, service.me) == Utils.getPeerID(peer, service.me)) {
                messageListModel.updateContents();
              }
            }
          });
        }
      });
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void setHintedFieldText(JTextField field, String text) {
    preventHintUpdates = true;
    field.setForeground(Color.BLACK);
    field.setText(text);
    preventHintUpdates = false;
  }
  
  private void sendFiles(File[] files) {
    if (currentPeer == null) return;
    System.out.println("sending " + files.length + " files...");
    
    for (File file : files) {
      sendFile(file);
    }
  }

  @Override
  public void update(int peer_id) { // Callback from TypingManager
    if ((currentPeer instanceof InputPeerChat && currentPeer.chat_id == -peer_id) ||
        (!(currentPeer instanceof InputPeerChat) && currentPeer.chat_id == peer_id)) {
      updateStatus();
    }
  }
  
  public void importContacts(ArrayList<String[]> contacts) {
    importContacts(contacts, false);
  }
  public void importContacts(ArrayList<String[]> contacts, boolean replace) {
    TInputContact[] arr = new TInputContact[contacts.size()];
    for (int i = 0; i < contacts.size(); i++) {
      String[] contact = contacts.get(i);
      arr[i] = new InputPhoneContact(i, contact[0], contact[1], contact[2]);
    }
    
    service.mainServer.call(new tl.contacts.ImportContacts(arr, replace), new Server.RPCCallback<ImportedContacts>() {
      public void done(ImportedContacts result) {
        contactListModel.add(result);
      }

      @Override
      public void error(int code, String message) {
      }
    });
  }

  public void onNewEncryptedMessage(TEncryptedMessage encrypted, TDecryptedMessage message, boolean fresh) {
    // TODO Auto-generated method stub
    restoreDialogSelection();
    service.dialogManager.updateContents();
    if (currentEncryptedChat == encrypted.chat_id) {
      messageListModel.addMessage(encrypted, message);
      //messageList
      
      //service.dialogManager.resetEncryptedUnread(currentPeer);
      /*service.mainServer.call(new ReadHistory(currentPeer, message.id, 0), new Server.RPCCallback<TLObject>() {
        public void done(TLObject result) {
        }
        public void error(int code, String message) {
        }
      });*/
    }
  }

  @Override
  public void onEncryptedChatTyping(int chat_id, boolean fresh) {
  }

  @Override
  public void onEncryption(final TEncryptedChat chat, int date, boolean fresh) {
    if (chat instanceof EncryptedChatRequested) { // Auto accept request (TODO: show dialog?)
      service.mainServer.call(new tl.messages.GetDhConfig(0, 256), new RPCCallback<DhConfig>() {
        public void done(final DhConfig config) {
          final byte[] random = new byte[256];
          (new Random()).nextBytes(random); // TODO: add more entropy
          for (int i = 0; i < random.length; i++) {
            random[i] ^= config.random[i];
          }
          
          BigInteger g_a = new BigInteger(1, chat.g_a);
          BigInteger g = BigInteger.valueOf(config.g);
          BigInteger b = new BigInteger(1, random);
          BigInteger dh_prime = new BigInteger(1, config.p);

          byte[] g_b = g.modPow(b, dh_prime).toByteArray();
          byte[] g_ab = g_a.modPow(b, dh_prime).toByteArray();
          final byte[] key = new byte[256];
          for (int i = 0; i < chat.nonce.length; i++) {
            key[i] = (byte) (g_ab[i + (g_ab.length - chat.nonce.length)] ^ chat.nonce[i]);
          }
          
          ByteBuffer tmp;
          try {
            tmp = ByteBuffer.wrap(SHA1(key));
            tmp.order(ByteOrder.LITTLE_ENDIAN);
            chat.key_fingerprint = tmp.getLong(12);
          } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
          }
          
          service.mainServer.call(new tl.messages.AcceptEncryption(new InputEncryptedChat(chat.id, chat.access_hash), g_b, chat.key_fingerprint), new RPCCallback<TEncryptedChat>() {
            public void done(TEncryptedChat chat) {
              service.dialogManager.updateEncryptedChat(chat, key);
              //selectDialog(service.userManager.get(chat.admin_id), chat, key);
            }
            public void error(int code, String message) {
              if (message.equals("PARTICIPANT_VERSION_OUTDATED")) {
                JOptionPane.showMessageDialog(frame, "Не удается создать секретный чат: собеседник использует устаревшую версию приложения.");
              }
              System.out.println("Failed to create encrypted chat: " + code + ", " + message);
            }
          });
        }
        public void error(int code, String message) {
          System.out.println("Failed to get DH config: " + code + ", " + message);
        }
      });
      //service.mainServer.call(new tl.messages.AcceptEncryption(peer, g_b, key_fingerprint));
    }
  }

  @Override
  public void onEncryptedMessagesRead(int chat_id, int max_date, int date, boolean fresh) {
  }
}
