package ru.denull.wire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.View;

import ru.denull.mtproto.DataService;
import ru.denull.wire.model.FileManager;
import tl.*;
import tl.Dialog;

public class MessageCellRenderer implements ListCellRenderer {
  private DataService service;
  private TInputPeer peer;
  private HashMap<Integer, Component> cache = new HashMap<Integer, Component>();
  
  public MessageCellRenderer(DataService service, TInputPeer peer) {
    this.service = service;
    this.peer = peer;
  }
  
  public Component getListCellRendererComponent(JList list, Object item, int index, boolean selected, boolean focused) {
    GridBagConstraints constr;
    
    if (item == null) {
      JLabel label = new JLabel("Нет сообщений", SwingConstants.CENTER);
      label.setOpaque(true);
      label.setForeground(Color.decode("0x80879b"));
      label.setBackground(Color.decode("0xdfe8ef"));
      label.setFont(new Font("Tahoma", Font.PLAIN, 12));
      label.setBorder(new EmptyBorder(4, 4, 4, 4));    
      return label;
    } else
    if (item instanceof Integer) {
      JLabel label = (JLabel) cache.get(-(Integer) item);
      
      if (label == null) {
        label = new JLabel(Utils.toDay((Integer) item), SwingConstants.CENTER);
        label.setOpaque(true);
        label.setForeground(Color.decode("0x80879b"));
        label.setBackground(Color.decode("0xdfe8ef"));
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
        cache.put(-(Integer) item, label);
      }
      
      return label;
    } else {
      TMessage message = (TMessage) item;
      Component comp = cache.get(message.id);
      
      if (comp != null) {
        return comp;
      }
      
      if (message instanceof MessageService) {
        JLabel label = new JLabel(Utils.getServiceMessageDesc(service, message), SwingConstants.CENTER);
        label.setOpaque(true);
        label.setForeground(Color.decode("0x80879b"));
        label.setBackground(Color.decode("0xdfe8ef"));
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label.setBorder(new EmptyBorder(4, 4, 4, 4));
        cache.put(message.id, label);
        return label;
      }
      
      MessageLayout layout = new MessageLayout(list, !message.out);
      JPanel panel = new JPanel(layout);
      panel.setBackground(Color.decode("0xdfe8ef"));
      
      JLabel timeLabel = new JLabel(Utils.toTime(message.date));
      timeLabel.setForeground(Color.decode("0x80879b"));
      timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
      panel.add(timeLabel, MessageLayout.DATE);
      
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
        
        layout.setHTMLBody(bodyLabel, 8, 25);
        
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
        if (message.preview != null) { // uploading photo
          thumbnail = message.preview;
          
          thumbPanel.setPreferredSize(new Dimension(
            Math.min(maxw, (int) (19 + Math.sqrt(50000f * thumbnail.getWidth(null) / thumbnail.getHeight(null)))),
            Math.min(maxh, (int) (10 + Math.sqrt(50000f * thumbnail.getHeight(null) / thumbnail.getWidth(null))))
          ));
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
        thumbPanel.setImage(thumbnail);
        
        TFileLocation location = media.getFullsize();
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
          service.fileManager.queryImage(location, thumbPanel);
          break;
        case FileManager.STATE_QUEUED:
        case FileManager.STATE_IN_PROGRESS:
          progressBar = new JProgressBar();
          progressBar.setValue(state & FileManager.STATE_PROGRESS_MASK);
          //panel.add(progressBar, MessageLayout.ACTIONS);
          break;
        case FileManager.STATE_COMPLETE:
          JButton openBtn = new JButton("Открыть");
          service.fileManager.queryImage(location, thumbPanel);
          //panel.add(openBtn, MessageLayout.ACTIONS);
          break;
        }
        
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
        
        Video video = (Video) media.video;
        thumbPanel.setImage(media.getThumbnail());
        
        thumbPanel.setPreferredSize(getOptimalSize(video.w, video.h));
        thumbPanel.setMaximumSize(new Dimension(video.w, video.h));
        
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
    int optw = (int) Math.sqrt(100000f * w / h);
    int opth = (int) Math.sqrt(100000f * h / w);
    
    // We don't want to upscale images
    optw = Math.min(optw, w);
    opth = Math.min(opth, h);
    
    // We don't need to think about width - MessageLayout scales image as needed, but we want to limit height here
    if (opth > 480) {
      opth = 480;
      optw = w * 480 / h;
    }
    
    return new Dimension(19 + optw, 10 + opth);
  }
}
