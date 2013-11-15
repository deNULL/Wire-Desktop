package ru.denull.wire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.*;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.View;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server;
import ru.denull.wire.model.FileManager;
import ru.denull.wire.model.MessageListModel;
import ru.denull.wire.model.FileManager.FileLoadingCallback;
import tl.*;
import tl.Dialog;
import tl.contacts.ImportedContacts;
import tl.storage.TFileType;

public class MessageCellRenderer implements ListCellRenderer {
  private DataService service;
  private TInputPeer peer;
  public HashMap<Integer, Component> cache = new HashMap<Integer, Component>();
  
  public MessageCellRenderer(DataService service, TInputPeer peer) {
    this.service = service;
    this.peer = peer;
  }
  
  public Component getListCellRendererComponent(final JList list, Object item, final int index, boolean selected, boolean focused) {
    GridBagConstraints constr;
    final MessageListModel model = (MessageListModel) list.getModel();
    final long modelState = model.getState();
    
    if (item instanceof String) {
      JLabel label = new JLabel((String) item, SwingConstants.CENTER);
      label.setOpaque(true);
      label.setForeground(Color.decode("0x80879b"));
      label.setBackground(Color.decode("0xdfe8ef"));
      label.setFont(new Font(Utils.fontName, Font.PLAIN, 18));
      label.setBorder(new EmptyBorder(50, 4, 50, 4));    
      return label;
    } else
    if (item instanceof Integer) {
      JLabel label = (JLabel) cache.get(-(Integer) item);
      
      if (label == null) {
        label = new JLabel(Utils.toDay((Integer) item), SwingConstants.CENTER);
        label.setOpaque(true);
        label.setForeground(Color.decode("0x80879b"));
        label.setBackground(Color.decode("0xdfe8ef"));
        label.setFont(new Font(Utils.fontName, Font.PLAIN, 12));
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
        cache.put(-(Integer) item, label);
      }
      
      return label;
    } else {
      final TMessage message = (TMessage) item;
      Component comp = cache.get(message.id);
      
      if (comp != null) {
        //System.out.println("returned " + message.id + ": " + comp);
        return comp;
      }
      
      if (message instanceof MessageService) {
        JLabel label = new JLabel(Utils.getServiceMessageDesc(service, message), SwingConstants.CENTER);
        label.setOpaque(true);
        label.setForeground(Color.decode("0x80879b"));
        label.setBackground(Color.decode("0xdfe8ef"));
        label.setFont(new Font(Utils.fontName, Font.PLAIN, 12));
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
        cache.put(message.id, label);
        return label;
      }
      
      MessageLayout layout = new MessageLayout(list, !message.out);
      JPanel panel = new JPanel(layout);
      panel.setBackground(Color.decode("0xdfe8ef"));
      
      JLabel timeLabel = new JLabel(Utils.toTime(message.date));
      timeLabel.setForeground(Color.decode("0x80879b"));
      timeLabel.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
      panel.add(timeLabel, MessageLayout.DATE);
      
      if (message.out) {
        if (message.failed) {
          timeLabel.setIcon(new ImageIcon(Utils.getImage("msg_warning.png")));
        } else if (message.sending) {
          timeLabel.setIcon(new ImageIcon(Utils.getImage("msg_clock.png")));
        } else if (message.unread) {
          timeLabel.setIcon(new ImageIcon(Utils.getImage("msg_check.png")));
        } else {
          timeLabel.setIcon(new ImageIcon(Utils.getImage("msg_dblcheck.png")));
        }
      }
      
      if (!message.out) {
        if (peer instanceof InputPeerChat) {
          ImagePanel photoPanel = new ImagePanel();
          photoPanel.setPreferredSize(new Dimension(32, 32));
          
          panel.add(photoPanel, MessageLayout.PHOTO);
          
          service.userManager.getUserpic(message.from_id, photoPanel, false);
        }
      }
      if (message.media instanceof MessageMediaEmpty) {
        /*JLabel bodyLabel = new JLabel(Utils.parseEmoji(
            message.message,
            (!message.out && peer instanceof InputPeerChat) ? service.userManager.get(message.from_id) : null,
            (message instanceof MessageForwarded) ? service.userManager.get(((MessageForwarded) message).fwd_from_id) : null,
            true));
        bodyLabel.setVerticalAlignment(SwingConstants.TOP);
        //bodyLabel.setMaximumSize(new Dimension());
        Utils.fixEmoji(bodyLabel);*/
        /*JTextPane bodyLabel = new JTextPane();
        bodyLabel.setContentType("text/html");
        bodyLabel.setText(Utils.parseEmoji(
            message.message,
            (!message.out && peer instanceof InputPeerChat) ? service.userManager.get(message.from_id) : null,
            (message instanceof MessageForwarded) ? service.userManager.get(((MessageForwarded) message).fwd_from_id) : null,
            true));
        bodyLabel.setOpaque(false);
        Utils.fixEmoji(bodyLabel);*/
        
        TUser from = (!message.out && peer instanceof InputPeerChat) ? service.userManager.get(message.from_id) : null;
        String fromName = null;
        Color fromColor = null;
        if (from != null) {
          fromColor = Color.decode("0x" + Utils.userColors[from.id & 7]);
          fromName = from.first_name + " " + from.last_name;
        }
        TUser forw = (message instanceof MessageForwarded) ? service.userManager.get(((MessageForwarded) message).fwd_from_id) : null;
        String forwName = null;
        Color forwColor = null;
        if (forw != null) {
          forwColor = Color.decode("0x006fc8");
          forwName = "Пересланное сообщение\n" + forw.first_name + " " + forw.last_name;
        }
        EmojiLabel bodyLabel = new EmojiLabel(message.message, fromName, fromColor, forwName, forwColor);
        
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(message.out ?
            new NinePatchBorder(Utils.getImage("msg_out.png"), 4, 4, 31, 13, 4, 8, 4, 14) : 
            new NinePatchBorder(Utils.getImage("msg_in.png"), 4, 13, 31, 4, 4, 17, 4, 5));
        bodyPanel.add(bodyLabel, BorderLayout.CENTER);
        //bodyLabel.setMinimumSize(new Dimension(32, 28));
        
        layout.setHTMLBody(bodyLabel, 8, 22);
        
        panel.add(bodyPanel, MessageLayout.BODY);
      } else
      if (message.media instanceof MessageMediaPhoto) {
        MessageMediaPhoto media = ((MessageMediaPhoto) message.media);
        
        ImagePanel thumbPanel = new ImagePanel();
        thumbPanel.setBorder(message.out ?
            new NinePatchBorder(Utils.getImage("msg_out.png"), 4, 4, 31, 13, 4, 4, 4, 13) : 
            new NinePatchBorder(Utils.getImage("msg_in.png"), 4, 13, 31, 4, 4, 13, 4, 4));
        panel.add(thumbPanel, MessageLayout.BODY);
        Image thumbnail = null;

        int maxw = 0;
        int maxh = 0;
        for (TPhotoSize size : ((Photo) media.photo).sizes) {
          maxw = Math.max(maxw, size.w);
          maxh = Math.max(maxh, size.h);
        }
        final int _maxw = maxw;
        final int _maxh = maxh;
        if (message.preview != null) { // uploading photo
          thumbnail = message.preview;
          
          thumbPanel.setPreferredSize(getOptimalSize(thumbnail.getWidth(null), thumbnail.getHeight(null)));
        } else {        
          thumbnail = media.getThumbnail();
          for (TPhotoSize size : ((Photo) media.photo).sizes) {
            if (size instanceof PhotoSize) {
              String type = ((PhotoSize) size).type;
              if (type.equals("x") || type.equals("y") || type.equals("w")) {
                thumbPanel.setPreferredSize(getOptimalSize(size.w, size.h));
                break;
              }
            }
          }
        }
        thumbPanel.setMaximumSize(new Dimension(maxw, maxh));
        
        final TFileLocation location = media.getFullsize();
        final TFileLocation hires = media.getMaxsize();
        int state = service.fileManager.getState(location);
        JProgressBar progressBar;
        switch (state & FileManager.STATE_LOADING_MASK) {
        case FileManager.STATE_NOT_LOADING:
          /*JButton downloadBtn = new JButton("Загрузить");
          downloadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
          });
          panel.add(downloadBtn, MessageLayout.ACTIONS);*/
          progressBar = new JProgressBar();
          progressBar.setValue(0);
          //panel.add(progressBar, MessageLayout.ACTIONS);
          thumbPanel.setImage(thumbnail);
          service.fileManager.query(location, new FileLoadingCallback() {
            public void fail() {
              // nothing
            }
            public void complete(TFileType type, Object data) {
              cache.remove(message.id);
              if (model.getState() == modelState) {
                model.updateContents(index);
                //list.repaint(list.getCellBounds(index, index));
              } else {
                model.updateContents();
              }
              
            }
          });
          break;
        case FileManager.STATE_QUEUED:
        case FileManager.STATE_IN_PROGRESS:
          progressBar = new JProgressBar();
          progressBar.setValue(state & FileManager.STATE_PROGRESS_MASK);
          //panel.add(progressBar, MessageLayout.ACTIONS);
          thumbPanel.setImage(thumbnail);
          break;
        case FileManager.STATE_COMPLETE:
          service.fileManager.queryImage(location, thumbPanel);          
          break;
        }
        
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.decode("0xdfe8ef"));
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        
        JButton openBtn = new JButton("открыть");
        //openBtn.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
        openBtn.setForeground(Color.DARK_GRAY);
        openBtn.putClientProperty("JButton.buttonType", "roundRect");
        openBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Просмотр");
            ImagePanel panel = new ImagePanel();
            //panel.setPreferredSize(new Dimension(_maxw, _maxh));
            service.fileManager.queryImage(hires, panel);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setSize(_maxw, _maxh);
            frame.setVisible(true);
          }
        });
        actionPanel.add(openBtn);
        
        actionPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        
        JButton saveBtn = new JButton("сохранить...");
        //openBtn.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.putClientProperty("JButton.buttonType", "roundRect");
        saveBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Main.saveDialog.setFile("photo.jpg");
            Main.saveDialog.setVisible(true);
            String filename = Main.saveDialog.getFile();
            if (filename != null) {
              service.fileManager.queryFile(hires, Main.saveDialog.getDirectory() + System.getProperty("file.separator") + Main.saveDialog.getFile());
            }
          }
        });
        actionPanel.add(saveBtn);
        
        panel.add(actionPanel, MessageLayout.ACTIONS);
        
        /*Button btnLoad = ViewHolder.get(convertView, R.id.btn_load);
        btnLoad.setTag(message);
        View progressBlock = ViewHolder.get(convertView, R.id.progress_block);
        TFileLocation location = media.getFullsize();
        int state = service.fileManager.getState(location);
        switch (state & FileManager.STATE_LOADING_MASK) {
        case FileManager.STATE_NOT_LOADING:
          btnLoad.setVisibility(View.VISIBLE);
          btnLoad.setText(activity.getResources().getString(R.string.download, ""));
          progressBlock.setVisibility(View.INVISIBLE);
          break;
        case FileManager.STATE_QUEUED:
        case FileManager.STATE_IN_PROGRESS:
          btnLoad.setVisibility(View.INVISIBLE);
          progressBlock.setVisibility(View.VISIBLE);
          
          ProgressBar progress = ViewHolder.get(progressBlock, R.id.progress);
          progress.setProgress(state & FileManager.STATE_PROGRESS_MASK);
          break;
        case FileManager.STATE_COMPLETE:
          service.fileManager.queryImage(location, thumb);
          btnLoad.setVisibility(View.VISIBLE);
          btnLoad.setText(activity.getResources().getString(R.string.view_photo, ""));
          progressBlock.setVisibility(View.INVISIBLE);
          break;
        }*/
      } else
      if (message.media instanceof MessageMediaVideo) {
        MessageMediaVideo media = ((MessageMediaVideo) message.media);
        
        ImagePanel thumbPanel = new ImagePanel();
        thumbPanel.setBorder(message.out ?
            new NinePatchBorder(Utils.getImage("msg_out.png"), 4, 4, 31, 13, 4, 4, 4, 13) : 
            new NinePatchBorder(Utils.getImage("msg_in.png"), 4, 13, 31, 4, 4, 13, 4, 4));
        panel.add(thumbPanel, MessageLayout.BODY);
        
        final Video video = (Video) media.video;
        thumbPanel.setImage(media.getThumbnail());
        thumbPanel.setBackground(Color.BLACK);
        
        thumbPanel.setPreferredSize(getOptimalSize(video.w, video.h));
        thumbPanel.setMaximumSize(new Dimension(video.w, video.h));
        
        JButton saveBtn = new JButton("сохранить...");
        //openBtn.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
        saveBtn.setForeground(Color.DARK_GRAY);
        saveBtn.putClientProperty("JButton.buttonType", "roundRect");
        saveBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Main.saveDialog.setFile("video.mov");
            Main.saveDialog.setVisible(true);
            String filename = Main.saveDialog.getFile();
            if (filename != null) {
              service.fileManager.queryFile(video, Main.saveDialog.getDirectory() + System.getProperty("file.separator") + Main.saveDialog.getFile());
            }
          }
        });
        panel.add(saveBtn, MessageLayout.ACTIONS);
        
        /*Button btnLoad = ViewHolder.get(convertView, R.id.btn_load);
        btnLoad.setTag(message);
        View progressBlock = ViewHolder.get(convertView, R.id.progress_block);
        int state = service.fileManager.getState(video);
        switch (state & FileManager.STATE_LOADING_MASK) {
        case FileManager.STATE_NOT_LOADING:
          btnLoad.setVisibility(View.VISIBLE);
          btnLoad.setText(activity.getResources().getString(R.string.download, Utils.toSize(video.size)));
          progressBlock.setVisibility(View.INVISIBLE);
          break;
        case FileManager.STATE_QUEUED:
        case FileManager.STATE_IN_PROGRESS:
          btnLoad.setVisibility(View.INVISIBLE);
          progressBlock.setVisibility(View.VISIBLE);
          
          ProgressBar progress = ViewHolder.get(progressBlock, R.id.progress);
          progress.setProgress(state & FileManager.STATE_PROGRESS_MASK);
          break;
        case FileManager.STATE_COMPLETE:
          btnLoad.setVisibility(View.VISIBLE);
          btnLoad.setText(activity.getResources().getString(R.string.view_video, ""));
          progressBlock.setVisibility(View.INVISIBLE);
          break;
        }
        
        ImageView icVideo = ViewHolder.get(convertView, R.id.ic_video);
        TextView duration = ViewHolder.get(convertView, R.id.duration);
        icVideo.setVisibility(View.VISIBLE);
        duration.setVisibility(View.VISIBLE);
        duration.setText(Utils.toDuration(video.duration));*/
      } else
      if (message.media instanceof MessageMediaGeo) {
        ImagePanel thumbPanel = new ImagePanel();
        thumbPanel.setBorder(message.out ?
            new NinePatchBorder(Utils.getImage("msg_out.png"), 4, 4, 31, 13, 4, 4, 4, 13) : 
            new NinePatchBorder(Utils.getImage("msg_in.png"), 4, 13, 31, 4, 4, 13, 4, 4));
        panel.add(thumbPanel, MessageLayout.BODY);

        thumbPanel.setPreferredSize(new Dimension(377, 248));
        try {
          thumbPanel.setImage(ImageIO.read(new URL(
              "http://maps.googleapis.com/maps/api/staticmap?" +
              "center=" + message.media.geo.lat + "," + message.media.geo.lng + "&" +
              "markers=color:red%7C" + message.media.geo.lat + "," + message.media.geo.lng + "&" +
              "zoom=14&" + 
              "size=360x240&" + 
              "maptype=roadmap&" + 
              "sensor=false&" +
              "visual_refresh=true")));
        } catch (MalformedURLException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.decode("0xdfe8ef"));
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        
        JButton yandexBtn = new JButton("Яндекс");
        //openBtn.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
        yandexBtn.setForeground(Color.DARK_GRAY);
        yandexBtn.putClientProperty("JButton.buttonType", "roundRect");
        yandexBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              Utils.openWebpage(new URI("http://maps.yandex.ru/?ll=" + message.media.geo.lng + "%2C" + message.media.geo.lat + "&z=16&l=map"));
            } catch (URISyntaxException e1) {
              e1.printStackTrace();
            }
          }
        });
        actionPanel.add(yandexBtn);
        
        actionPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        
        JButton googleBtn = new JButton("Google");
        //openBtn.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
        googleBtn.setForeground(Color.DARK_GRAY);
        googleBtn.putClientProperty("JButton.buttonType", "roundRect");
        googleBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              Utils.openWebpage(new URI("http://google.com/maps?q=" + message.media.geo.lat + "," + message.media.geo.lng));
            } catch (URISyntaxException e1) {
              e1.printStackTrace();
            }
          }
        });
        actionPanel.add(googleBtn);
        
        panel.add(actionPanel, MessageLayout.ACTIONS);
        
        /*ImageButton thumb = ViewHolder.get(convertView, R.id.thumb);
        thumb.setImageBitmap(null);
        
        Button btnLoad = ViewHolder.get(convertView, R.id.btn_load);
        btnLoad.setTag(message);
        View progressBlock = ViewHolder.get(convertView, R.id.progress_block);
        
        btnLoad.setVisibility(View.VISIBLE);
        btnLoad.setText(activity.getResources().getString(R.string.view_location, ""));
        progressBlock.setVisibility(View.INVISIBLE);*/
      } else
      if (message.media instanceof MessageMediaContact) {
        TUser from = (!message.out && peer instanceof InputPeerChat) ? service.userManager.get(message.from_id) : null;
        String fromName = null;
        Color fromColor = null;
        if (from != null) {
          fromColor = Color.decode("0x" + Utils.userColors[from.id & 7]);
          fromName = from.first_name + " " + from.last_name;
        }
        String forwName = "Контакт\n" + message.media.first_name + " " + message.media.last_name;
        Color forwColor = Color.decode("0x006fc8");
        EmojiLabel bodyLabel = new EmojiLabel(message.media.phone_number, fromName, fromColor, forwName, forwColor);
        
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(message.out ?
            new NinePatchBorder(Utils.getImage("msg_out.png"), 4, 4, 31, 13, 4, 8, 4, 14) : 
            new NinePatchBorder(Utils.getImage("msg_in.png"), 4, 13, 31, 4, 4, 17, 4, 5));
        bodyPanel.add(bodyLabel, BorderLayout.CENTER);
        
        layout.setHTMLBody(bodyLabel, 8, 25);
        
        panel.add(bodyPanel, MessageLayout.BODY);
        
        final MessageMediaContact contact = (MessageMediaContact) message.media;
        
        if (!service.contactManager.loaded.containsKey(-contact.user_id)) {
          final JButton importBtn = new JButton("добавить");
          //openBtn.setFont(new Font(Utils.fontName, Font.PLAIN, 11));
          importBtn.setForeground(Color.DARK_GRAY);
          importBtn.putClientProperty("JButton.buttonType", "roundRect");
          importBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              service.mainServer.call(new tl.contacts.ImportContacts(
                  new TInputContact[] { new InputPhoneContact(0, contact.phone_number, contact.first_name, contact.last_name) }, false),
                  new Server.RPCCallback<ImportedContacts>() {
                    public void done(ImportedContacts result) {
                      if (result.imported.length > 0) {
                        Main.contactListModel.add(result.imported[0].user_id, true);
                        
                      }
                      if (model.getState() == modelState) {
                        model.updateContents(index);
                        //list.repaint(list.getCellBounds(index, index));
                      } else {
                        model.updateContents();
                      }
                    }
  
                    public void error(int code, String message) {
                    }
                  
                  });
            }
          });
          panel.add(importBtn, MessageLayout.ACTIONS);
        }
      }
      
      if (message.out) {
        /*ImageView status = ViewHolder.get(convertView, R.id.status);
        if (message.media instanceof MessageMediaEmpty) {
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
          if (message.failed) {
            status.setImageResource(R.drawable.msg_warning);
          } else if (message.sending) {
            status.setImageResource(R.drawable.msg_check_photo);
          } else if (message.unread) {
            status.setImageResource(R.drawable.msg_check_photo);
          } else {
            status.setImageResource(R.drawable.msg_check_photo_comp);
          }
        }*/
      }
      
      cache.put(message.id, panel);
      
      return panel;
    }
  }
  
  private Dimension getOptimalSize(int w, int h) {
    // Try to set fixed area first (100 kilopixels), so all pictures look roughly same size
    int optw = (int) Math.sqrt(200000f * w / h);
    int opth = (int) Math.sqrt(200000f * h / w);
    
    // We don't want to upscale images
    optw = Math.min(optw, w);
    opth = Math.min(opth, h);
    
    // We don't need to think about width - MessageLayout scales image as needed, but we want to limit height here
    if (opth > 500) {
      opth = 500;
      optw = w * 500 / h;
    }
    
    return new Dimension(17 + optw, 8 + opth);
  }
}
