package ru.denull.wire;

import ru.denull.mtproto.DataService;
import ru.denull.wire.model.DialogManager;
import ru.denull.wire.model.FileManager.FileLoadingCallback;
import ru.denull.wire.stub.tl.storage.TFileType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DialogCellRenderer implements ListCellRenderer {
    private static final long serialVersionUID = 5645361179616977L;

    private DataService service;

    public DialogCellRenderer(DataService service) {
        this.service = service;
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
        final DialogManager model = (DialogManager) list.getModel();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        //panel.setBackground(Color.decode("0xf9f9f9"));
        panel.setBackground(Color.WHITE);
        GridBagConstraints constraints;
        final Dialog dialog = (Dialog) item;
        TMessage message = service.messageManager.get(dialog.top_message);

        //panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel unreadLabel = new JLabel();
        //unreadLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        unreadLabel.setMinimumSize(new Dimension(10, 66));
        unreadLabel.setPreferredSize(new Dimension(10, 66));
        if (dialog.unread_count > 0 && !message.out) {
            unreadLabel.setIcon(new ImageIcon(Utils.getImage("unread.png")));
        }
        constraints = Utils.GBConstraints(0, 0, 1, 2);
        constraints.insets = new Insets(0, 3, 0, 3);
        panel.add(unreadLabel, constraints);

        ImagePanel iconLabel = new ImagePanel();
        //iconLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        iconLabel.setMinimumSize(new Dimension(50, 50));
        iconLabel.setPreferredSize(new Dimension(50, 50));
        panel.add(iconLabel, Utils.GBConstraints(1, 0, 1, 2));

        JLabel dateLabel = new JLabel(Utils.toTimeOrDay(message.date));
        dateLabel.setForeground(selected ? Color.WHITE : Color.decode("0x006fc8"));
        dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        //dateLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        //dateLabel.setPreferredSize(new Dimension(60, dateLabel.getPreferredSize().height));
        //System.out.println(Utils.toTimeOrDay(message.date));
        if (message.out) {
//      if (message.failed) {
//        dateLabel.setIcon(new ImageIcon(Utils.getImage("msg_warning.png")));
//      } else if (message.sending) {
//        dateLabel.setIcon(new ImageIcon(Utils.getImage("msg_clock.png")));
//      } else

            if (message.unread) {
                dateLabel.setIcon(new ImageIcon(Utils.getImage("msg_check.png")));
            } else {
                dateLabel.setIcon(new ImageIcon(Utils.getImage("msg_dblcheck.png")));
            }
        }

        constraints = Utils.GBConstraints(3, 0, 2, 1);
        constraints.insets = new Insets(6, 0, 0, 8);
        panel.add(dateLabel, constraints);

        JLabel deleteLabel = new JLabel();
        //deleteLabel.setBorder(BorderFactory.createLineBorder(Color.RED));

        constraints = Utils.GBConstraints(4, 1, 1, 1);
        constraints.anchor = GridBagConstraints.PAGE_START;
        panel.add(deleteLabel, constraints);

        if (dialog.peer instanceof PeerChat) {
            int chat_id = ((PeerChat) dialog.peer).chat_id;
            TChat chat = service.chatManager.get(chat_id);
            TUser user = service.userManager.get(message.from_id);

            if (chat == null) {
                //Log.wtf(TAG, "Trying to show non-existent chat #" + chat_id);
                return panel;
            }

            //setupItem(convertView, tchat, user, message, activity, query);
            service.chatManager.getImage(chat.id, iconLabel, false, new FileLoadingCallback() {
                public void fail() {
                }

                public void complete(TFileType type, Object data) {
                    //cache.remove(message.id);
                    Object o = model.getSize() > index ? model.getElementAt(index) : null;
                    if (o != null && o instanceof Dialog && ((Dialog) o).peer.chat_id == dialog.peer.chat_id) {
                        model.updateContents(index);
                    } else {
                        model.updateContents();
                    }
                }
            });
      
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

            JLabel titleLabel = new JLabel(chat.title.trim());
            titleLabel.setFont(new Font(Utils.fontName, Font.PLAIN, 14));
            titleLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
            //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
            constraints = Utils.GBConstraints(2, 0, 1, 1);
            constraints.insets = new Insets(5, 7, 2, 0);
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
            } else if (message instanceof MessageService) {
                messageLabel = new EmojiLabel(Utils.getServiceMessageDesc(service, message));
                messageLabel.setForeground(Color.decode(selected ? "0xffffff" : "0x006fc8"));
            } else {
                messageLabel = new EmojiLabel(getMessagePreview(message),
                        (user instanceof UserEmpty) ? null : (user instanceof UserSelf ? "Вы" : (user.first_name + " " + user.last_name)),
                        Color.decode(selected ? "0xffffff" : (user instanceof UserSelf ? "0x808080" : "0x006fc8")));
                messageLabel.setForeground(Color.decode(selected ? "0xffffff" : (message.media instanceof MessageMediaEmpty ? "0x404040" : "0x006fc8")));
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

            service.userManager.getUserpic(user_id, iconLabel, false, new FileLoadingCallback() {
                public void fail() {
                }

                public void complete(TFileType type, Object data) {
                    //cache.remove(message.id);
                    Object o = model.getSize() > index ? model.getElementAt(index) : null;
                    if (o != null && o instanceof Dialog && ((Dialog) o).peer.user_id == dialog.peer.user_id) {
                        model.updateContents(index);
                    } else {
                        model.updateContents();
                    }
                }
            });

            JLabel titleLabel = new JLabel((user instanceof UserEmpty) ? "" : (user.first_name + " " + user.last_name).trim());
            titleLabel.setFont(new Font(Utils.fontName, Font.PLAIN, 14));
            titleLabel.setForeground(selected ? Color.WHITE : Color.BLACK);
            //titleLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
            constraints = Utils.GBConstraints(2, 0, 1, 1);
            constraints.insets = new Insets(4, 7, 2, 0);
            constraints.weightx = 1;
            constraints.anchor = GridBagConstraints.LINE_START;
            panel.add(titleLabel, constraints);

            String typing = service.typingManager.getStatus(user_id, false);
            EmojiLabel messageLabel = new EmojiLabel(typing == null ? getMessagePreview(message) : typing);
            messageLabel.setForeground(selected ? Color.WHITE : ((typing == null && !(message.media instanceof MessageMediaEmpty)) ? Color.decode("0x006fc8") : Color.DARK_GRAY));
            //messageLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
            messageLabel.setMinimumSize(new Dimension(0, 0));
            messageLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            messageLabel.center = false;
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
