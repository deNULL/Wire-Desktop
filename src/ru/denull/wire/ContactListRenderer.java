package ru.denull.wire;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import ru.denull.mtproto.DataService;
import ru.denull.wire.Utils;
import tl.*;
import tl.Dialog;

public class ContactListRenderer implements ListCellRenderer {
  
  private DataService service;
  
  public ContactListRenderer(DataService service) {
    this.service = service;
  }
  
  private int times = 0;
  public Component getListCellRendererComponent(JList list, Object item, int index, boolean selected, boolean focused) {
    TUser user = service.userManager.get((Integer) item);
    
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setOpaque(true);
    panel.setBackground(Color.decode("0xf9f9f9"));
    GridBagConstraints constraints;
    
    ImagePanel iconLabel = new ImagePanel();
    //iconLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    iconLabel.setMinimumSize(new Dimension(32, 32));
    iconLabel.setPreferredSize(new Dimension(32, 32));
    constraints = Utils.GBConstraints(0, 0, 1, 2);
    constraints.insets = new Insets(3, 6, 3, 0);
    panel.add(iconLabel, constraints);
    
    service.userManager.getUserpic(user.id, iconLabel, false);
      
    JLabel titleLabel = new JLabel((user.first_name + " " + user.last_name).trim());
    titleLabel.setFont(new Font(Utils.fontName, Font.BOLD, 12));
    titleLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
    //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    constraints = Utils.GBConstraints(1, 0, 1, 1);
    constraints.insets = new Insets(4, 7, 2, 0);
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
    
    if (selected) {
      if (System.getProperty("os.name").contains("Mac")) {
        panel.setBorder(UIManager.getBorder("List.sourceList" + (focused ? "Focused" : "") + "SelectionBackgroundPainter"));
      } else {
        panel.setBackground(UIManager.getColor("List.selectionBackground"));
      }
    } else {
      //panel.setBorder(UIManager.getBorder("List.sourceListBackgroundPainter"));
    }
    return panel;
  }
}
