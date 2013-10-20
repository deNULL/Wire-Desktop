package ru.denull.wire;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import ru.denull.mtproto.DataService;
import tl.Dialog;
import tl.TMessage;

public class DialogCellRenderer implements ListCellRenderer {
  private static final long serialVersionUID = 5645361179616977L;
  
  private DataService service;
  
  public DialogCellRenderer(DataService service) {
    this.service = service;
  }
  
  private int times = 0;
  public Component getListCellRendererComponent(JList list, Object item, int index, boolean selected, boolean focused) {
    JPanel panel = new JPanel(new GridBagLayout());
    Dialog dialog = service.dialogManager.get(index);
    TMessage message = service.messageManager.get(dialog.top_message);

    GridBagConstraints constraints;
    //panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    JLabel unreadLabel = new JLabel();
    //unreadLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    unreadLabel.setPreferredSize(new Dimension(16, 60));
    constraints = new GridBagConstraints();
    constraints.gridx = 0;      constraints.gridy = 0;
    constraints.gridwidth = 1;  constraints.gridheight = 2;
    panel.add(unreadLabel, constraints);
    
    ImagePanel iconLabel = new ImagePanel();
    //iconLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    iconLabel.setPreferredSize(new Dimension(50, 50));
    constraints = new GridBagConstraints();
    constraints.gridx = 1;      constraints.gridy = 0;
    constraints.gridwidth = 1;  constraints.gridheight = 2;
    panel.add(iconLabel, constraints);
    
    service.userManager.getUserpic(message.from_id, iconLabel, false);
    
    JLabel titleLabel = new JLabel();
    //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    constraints = new GridBagConstraints();
    constraints.gridx = 2;      constraints.gridy = 0;
    constraints.gridwidth = 1;  constraints.gridheight = 1;
    constraints.ipadx = 2;      constraints.ipady = 2;
    constraints.weightx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    panel.add(titleLabel, constraints);
    
    JLabel dateLabel = new JLabel(Utils.toTimeOrDay(message.date));
    //dateLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    //dateLabel.setPreferredSize(new Dimension(60, dateLabel.getPreferredSize().height));
    //System.out.println(Utils.toTimeOrDay(message.date));
    constraints = new GridBagConstraints();
    constraints.gridx = 3;      constraints.gridy = 0;
    constraints.gridwidth = 2;  constraints.gridheight = 1;
    constraints.ipadx = 2;      constraints.ipady = 2;
    panel.add(dateLabel, constraints);
    
    JLabel messageLabel = new JLabel(message.message);
    //messageLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    messageLabel.setMinimumSize(new Dimension(0, 0));
    constraints = new GridBagConstraints();
    constraints.gridx = 2;      constraints.gridy = 1;
    constraints.gridwidth = 2;  constraints.gridheight = 1;
    constraints.ipadx = 2;      constraints.ipady = 2;
    constraints.weightx = 1;
    constraints.anchor = GridBagConstraints.FIRST_LINE_START;
    panel.add(messageLabel, constraints);
    
    JLabel deleteLabel = new JLabel();
    //deleteLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    constraints = new GridBagConstraints();
    constraints.gridx = 4;      constraints.gridy = 1;
    constraints.gridwidth = 1;  constraints.gridheight = 1;
    panel.add(deleteLabel, constraints);
    
    //panel.setBackground(Color.WHITE);
    if (selected) {
      //panel.setBackground(UIManager.getColor("List.selectionBackground"));
      panel.setBorder(UIManager.getBorder("List.sourceList" + (focused ? "Focused" : "") + "SelectionBackgroundPainter"));
    } else {
      //panel.setBorder(UIManager.getBorder("List.sourceListBackgroundPainter"));
    }
    return panel;
  }
}
