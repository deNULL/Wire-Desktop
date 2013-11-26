package ru.denull.wire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import ru.denull.mtproto.DataService;
import ru.denull.wire.Utils;
import ru.denull.wire.model.ContactListModel;
import ru.denull.wire.model.DialogManager;
import ru.denull.wire.model.FileManager.FileLoadingCallback;
import tl.*;
import tl.Dialog;
import tl.storage.TFileType;

public class ContactListRenderer implements ListCellRenderer {
  
  private DataService service;
  public String buttonText = null;
  public ContactActionListener actionListener = null;
  public HashMap<Integer, Component> cache = new HashMap<Integer, Component>();
  public HashMap<Integer, Component> cacheSelected = new HashMap<Integer, Component>();
  
  public interface ContactActionListener {
    public boolean isVisible(int user_id, int index);
    public void onContactSelected(int user_id, int index);
  }
  
  public ContactListRenderer(DataService service) {
    this.service = service;
  }
  
  public void dropCache() {
    cache = new HashMap<Integer, Component>();
    cacheSelected = new HashMap<Integer, Component>();
  }
  
  public void dropCache(int user_id) {
    cache.remove(user_id);
    cacheSelected.remove(user_id);
  }
  
  private int times = 0;
  public Component getListCellRendererComponent(JList list, Object item, final int index, boolean selected, boolean focused) {
    if (item instanceof String) {
      JLabel label = new JLabel((String) item, SwingConstants.CENTER);
      label.setOpaque(true);
      label.setForeground(Color.decode("0x808080"));
      label.setBackground(Color.WHITE);
      label.setFont(new Font(Utils.fontName, Font.PLAIN, 16));
      label.setBorder(new EmptyBorder(30, 4, 30, 4));    
      return label;
    }
    Component comp = selected ? cacheSelected.get((Integer) item) : cache.get((Integer) item);
    if (comp != null) {
      //System.out.println("returned " + message.id + ": " + comp);
      return comp;
    }
    
    final ContactListModel model = (ContactListModel) list.getModel();
    
    final TUser user = service.userManager.get((Integer) item);
    
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setOpaque(true);
    panel.setBackground(Color.WHITE);
    GridBagConstraints constraints;
    
    ImagePanel iconLabel = new ImagePanel();
    //iconLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    iconLabel.setMinimumSize(new Dimension(32, 32));
    iconLabel.setPreferredSize(new Dimension(32, 32));
    constraints = Utils.GBConstraints(0, 0, 1, 2);
    constraints.insets = new Insets(3, 6, 3, 0);
    panel.add(iconLabel, constraints);
    
    service.userManager.getUserpic(user.id, iconLabel, false, new FileLoadingCallback() {
      public void fail() { }
      public void complete(TFileType type, Object data) {
        cache.remove(user.id);
        //Object o = model.getSize() > index ? model.getElementAt(index) : null;
        model.updateContents(user.id);
      }
    });
      
    JLabel titleLabel = new JLabel((user.first_name + " " + user.last_name).trim());
    titleLabel.setFont(new Font(Utils.fontName, Font.PLAIN, 14));
    titleLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
    //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    constraints = Utils.GBConstraints(1, 0, 1, 1);
    constraints.insets = new Insets(2, 7, 2, 0);
    constraints.weightx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    panel.add(titleLabel, constraints);
      
    JLabel statusLabel = new JLabel(Utils.toStatus(user.status, false));
    statusLabel.setForeground(selected ? Color.WHITE : Color.decode("0x808080"));
    statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
    //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    constraints = Utils.GBConstraints(1, 1, 1, 1);
    constraints.insets = new Insets(0, 7, 2, 0);
    constraints.weightx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    panel.add(statusLabel, constraints);
    
    if (buttonText != null && (actionListener == null || actionListener.isVisible(user.id, index))) {
      JButton actionBtn = new JButton(buttonText);
      actionBtn.setForeground(Color.DARK_GRAY);
      actionBtn.putClientProperty("JButton.buttonType", "roundRect");
      
      if (actionListener != null) {
        actionBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            actionListener.onContactSelected(user.id, index);
          }
        });
      }
      
      constraints = Utils.GBConstraints(2, 0, 1, 2);
      constraints.insets = new Insets(2, 2, 2, 4);
      panel.add(actionBtn, constraints);
    }
    
    if (selected) {
      if (System.getProperty("os.name").contains("Mac")) {
        panel.setBorder(UIManager.getBorder("List.sourceList" + (focused ? "Focused" : "") + "SelectionBackgroundPainter"));
      } else {
        panel.setBackground(UIManager.getColor("List.selectionBackground"));
      }
      cacheSelected.put((Integer) item, panel);
    } else {
      //panel.setBorder(UIManager.getBorder("List.sourceListBackgroundPainter"));

      cache.put((Integer) item, panel);
    }
    
    
    return panel;
  }
}
