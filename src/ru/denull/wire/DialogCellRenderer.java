package ru.denull.wire;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import ru.denull.mtproto.DataService;
import ru.denull.wire.Utils;
import tl.*;
import tl.Dialog;

public class DialogCellRenderer implements ListCellRenderer {
  private static final long serialVersionUID = 5645361179616977L;
  
  private DataService service;
  
  public DialogCellRenderer(DataService service) {
    this.service = service;
  }
  
  private int times = 0;
  public Component getListCellRendererComponent(JList list, Object item, int index, boolean selected, boolean focused) {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setOpaque(true);
    panel.setBackground(Color.decode("0xf9f9f9"));
    GridBagConstraints constraints;
    Dialog dialog = service.dialogManager.get(index);
    TMessage message = service.messageManager.get(dialog.top_message);
    
    //panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
    JLabel unreadLabel = new JLabel();
    //unreadLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    unreadLabel.setMinimumSize(new Dimension(16, 64));
    unreadLabel.setPreferredSize(new Dimension(16, 64));
    constraints = new GridBagConstraints();
    panel.add(unreadLabel, Utils.GBConstraints(0, 0, 1, 2));
    
    ImagePanel iconLabel = new ImagePanel();
    //iconLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    iconLabel.setMinimumSize(new Dimension(50, 50));
    iconLabel.setPreferredSize(new Dimension(50, 50));
    panel.add(iconLabel, Utils.GBConstraints(1, 0, 1, 2));
    
    JLabel dateLabel = new JLabel(Utils.toTimeOrDay(message.date));
    dateLabel.setForeground(selected ? Color.WHITE : Color.decode("0x006fc8"));
    dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
    //dateLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    //dateLabel.setPreferredSize(new Dimension(60, dateLabel.getPreferredSize().height));
    //System.out.println(Utils.toTimeOrDay(message.date));
    constraints = Utils.GBConstraints(3, 0, 2, 1);
    constraints.insets = new Insets(4, 0, 0, 4);
    panel.add(dateLabel, constraints);
    
    JLabel deleteLabel = new JLabel();
    //deleteLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
    panel.add(deleteLabel, Utils.GBConstraints(4, 1, 1, 1));
      
    if (dialog.peer instanceof PeerChat) {
      int chat_id = ((PeerChat) dialog.peer).chat_id;
      TChat chat = service.chatManager.get(chat_id);
      TUser user = service.userManager.get(message.from_id);
      
      if (chat == null) {
        //Log.wtf(TAG, "Trying to show non-existent chat #" + chat_id);
        return panel;
      }
      
      //setupItem(convertView, tchat, user, message, activity, query);
      service.chatManager.getImage(chat.id, iconLabel, false);
      
      /*if (query == null) {
        title.setText(chat.title);
      } else {
        SpannableString htitle = new SpannableString(chat.title);
        int start = 0;
        query = query.trim().toLowerCase();
        String tmp = chat.title.trim().toLowerCase();
        do {
          start = tmp.indexOf(query, start);
          if (start > -1) {
            htitle.setSpan(Utils.getHighlight(), start, start + query.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            start++;
          }
        } while (start > -1);
        title.setText(htitle);
      }*/
      
      JLabel titleLabel = new JLabel(chat.title);
      titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
      titleLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
      //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
      constraints = Utils.GBConstraints(2, 0, 1, 1);
      constraints.insets = new Insets(7, 7, 0, 0);
      constraints.weightx = 1;
      constraints.anchor = GridBagConstraints.LINE_START;
      panel.add(titleLabel, constraints);
      
      String typing = service.typingManager.getStatus(-chat.id, false);
      
      String text;
      Color color = null;
      EmojiLabel messageLabel;
      if (typing != null) {
        messageLabel = new EmojiLabel(typing);
        messageLabel.setForeground(Color.decode(selected ? "0xffffff" : "0x006fc8"));
      } else
      if (message instanceof MessageService) {
        messageLabel = new EmojiLabel(Utils.getServiceMessageDesc(service, message));
        messageLabel.setForeground(Color.decode(selected ? "0xffffff" : "0x006fc8"));
      } else {
        messageLabel = new EmojiLabel(getMessagePreview(message), 
            (user instanceof UserEmpty) ? null : (user instanceof UserSelf ? "Вы" : (user.first_name + " " + user.last_name)),
            Color.decode(selected ? "0xffffff" : (user instanceof UserSelf ? "0x808080" : "0x006fc8")));
        messageLabel.setForeground(Color.decode(selected ? "0xffffff" : (message.media instanceof MessageMediaEmpty ? "0x000000" : "0x006fc8")));
      }
      //messageLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
      messageLabel.setMinimumSize(new Dimension(0, 0));
      messageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
      messageLabel.center = false;
      constraints = Utils.GBConstraints(2, 1, 2, 1);
      constraints.insets = new Insets(0, 7, 0, 0);
      constraints.weightx = 1;
      constraints.anchor = GridBagConstraints.FIRST_LINE_START;
      panel.add(messageLabel, constraints);
    } else {
      int user_id = dialog.peer.user_id;
      TUser user = service.userManager.get(user_id);

      service.userManager.getUserpic(user_id, iconLabel, false);
      
      JLabel titleLabel = new JLabel((user instanceof UserEmpty) ? "" : (user.first_name + " " + user.last_name));
      titleLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
      titleLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
      //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
      constraints = Utils.GBConstraints(2, 0, 1, 1);
      constraints.insets = new Insets(7, 7, 0, 0);
      constraints.weightx = 1;
      constraints.anchor = GridBagConstraints.LINE_START;
      panel.add(titleLabel, constraints);

      String typing = service.typingManager.getStatus(user_id, false);
      JLabel messageLabel = new JLabel(typing == null ? getMessagePreview(message) : typing);
      messageLabel.setForeground(selected ? Color.WHITE : ((typing == null && !(message.media instanceof MessageMediaEmpty)) ? Color.decode("0x006fc8") : Color.BLACK));
      //messageLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
      messageLabel.setMinimumSize(new Dimension(0, 0));
      constraints = Utils.GBConstraints(2, 1, 2, 1);
      constraints.insets = new Insets(0, 7, 0, 0);
      constraints.weightx = 1;
      constraints.anchor = GridBagConstraints.FIRST_LINE_START;
      panel.add(messageLabel, constraints);
      
      /*subtitle1.setTypeface(Utils.getRobotoLight(activity));
      subtitle1.setSingleLine(false);
      subtitle1.setMaxLines(2);
      subtitle2.setText("");      */
    }
    
    /*if (message.out) {
      if (message.failed) {
        status.setImageResource(R.drawable.msg_warning);
      } else if (message.sending) {
        status.setImageResource(R.drawable.msg_clock);
      } else if (message.unread) {
        status.setImageResource(R.drawable.msg_check);
      } else {
        status.setImageResource(R.drawable.msg_check_comp);
      }
    } else {
      status.setImageResource(android.R.color.transparent);
    }*/
    
    //panel.setBackground(Color.WHITE);
    if (selected) {
      //panel.setBackground(UIManager.getColor("List.selectionBackground"));
      panel.setBorder(UIManager.getBorder("List.sourceList" + (focused ? "Focused" : "") + "SelectionBackgroundPainter"));
    } else {
      //panel.setBorder(UIManager.getBorder("List.sourceListBackgroundPainter"));
    }
    return panel;
  }
  
  public static String getMessagePreview(TMessage message) {
    /*if (message.media instanceof MessageMediaEmpty) {
      return Utils.parseEmoji(message.message);
    } else if (message.media instanceof MessageMediaPhoto) {
      return "Photo";
    } else if (message.media instanceof MessageMediaVideo) {
      return "Video";
    } else if (message.media instanceof MessageMediaGeo) {
      return "Place";
    } else if (message.media instanceof MessageMediaContact) {
      return "Contact";
    } else {
      return "";
    }*/
    if (message.media instanceof MessageMediaEmpty) {
      return message.message;
    } else if (message.media instanceof MessageMediaPhoto) {
      return "Фото";
    } else if (message.media instanceof MessageMediaVideo) {
      return "Видео";
    } else if (message.media instanceof MessageMediaGeo) {
      return "Место";
    } else if (message.media instanceof MessageMediaContact) {
      return "Контакт";
    } else {
      return "";
    }
  }
}
