package ru.denull.wire.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import ru.denull.mtproto.Auth.AuthCallback;
import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Log;
import ru.denull.mtproto.Server;
import tl.Dialog;
import tl.FileLocation;
import tl.InputFile;
import tl.InputFileLocation;
import tl.InputVideoFileLocation;
import tl.PeerUser;
import tl.TFileLocation;
import tl.TInputFile;
import tl.TInputFileLocation;
import tl.TLObject;
import tl.Video;
import tl.storage.TFileType;
import tl.upload.GetFile;
import tl.upload.SaveFilePart;

public class FileManager {
  private static final String TAG = "FileManager";
  
  /*
  public static final String TABLE_NAME = "file";
  public static final String _ID = "_id";
  public static final String COLUMN_NAME_BODY = "body";
  public static final String SQL_CREATE_ENTRIES =
      "CREATE TABLE " + TABLE_NAME + " (" +
          _ID + " INTEGER PRIMARY KEY," +
          COLUMN_NAME_BODY + " BLOB" +
      " )";
  public static final String SQL_DELETE_ENTRIES =
      "DROP TABLE IF EXISTS " + TABLE_NAME;
  */
  
  public static final int MAX_CONCURRENT_JOBS = 3;
  public static final long MIN_DISK_CACHE_CLEAR_INTERVAL = 10 * 1000; // clear disk cache each 10s
  public static final int MAX_MEMORY_CACHE = 8 * 1024 * 1024; // allow up to 8mb in memory
  public static final int MAX_DISK_CACHE = 16 * 1024 * 2024; // allow up to 16mb on disk
  public static final int MIN_DISK_CACHE_STORE_TIME = 8 * 60 * 60 * 1000; // store files on disk for 8 hours at least
  public static long lastTimeCacheChecked = 0;
  public int activeJobs = 0;
  
  public DataService service;
  public File cacheDir;
  //SQLiteDatabase db;
  
  public Cache loaded;
  public HashMap<Long, FileLoadingJob> progress = new HashMap<Long, FileLoadingJob>(50);
  public LinkedList<FileLoadingJob> queue = new LinkedList<FileLoadingJob>();
  public HashMap<Long, Integer> states = new HashMap<Long, Integer>(100);
  
  //public static final Dialog empty = new Dialog(new PeerUser(0), 0, 0);
  
  public class FileLoadingJob {
    public static final int WAITING = 0;
    public static final int LOADING = 1;
    public static final int CANCELED = 2; // not really needed, as jobs are instantly removed on error (TODO: don't remove? for some time?)
    public static final int COMPLETE = 3;
    
    public static final int CHUNK_SIZE = 8 * 1024; // 8kb?
    
    public FileManager manager;
    
    public long id;
    public int dc_id;
    public TInputFileLocation location;
    
    public boolean diskOnly; // don't store this file in memory at all (for video files)
    
    public TFileType type;
    
    public int size = 0, loaded = 0;
    public ByteArrayOutputStream buffer;
    public File cached;
    public FileOutputStream stream;
    public int state = WAITING;
    public ArrayList<FileLoadingCallback> callbacks = new ArrayList<FileLoadingCallback>();
    public ArrayList<WeakReference<JLabel>> views = new ArrayList<WeakReference<JLabel>>();
    
    
    public FileLoadingJob(FileManager manager, long id, int dc_id, TInputFileLocation location) {
      this(manager, id, dc_id, location, false, 0);
    }
    public FileLoadingJob(FileManager manager, long id, int dc_id, TInputFileLocation location, int size) {
      this(manager, id, dc_id, location, false, size);
    }
    public FileLoadingJob(FileManager manager, long id, int dc_id, TInputFileLocation location, boolean diskOnly) {
      this(manager, id, dc_id, location, diskOnly, 0);
    }
    public FileLoadingJob(FileManager manager, long id, int dc_id, TInputFileLocation location, boolean diskOnly, int size) {
      this.manager = manager;
      this.id = id;
      this.dc_id = dc_id;
      this.location = location;
      this.diskOnly = diskOnly;
      this.size = size;
    }
    public void start() {
      state = LOADING;
      manager.activeJobs++;
      
      // check disk first
      final String filename = "file" + Long.toHexString(id) + ".dat";
      service.threadPool.submit(new Runnable() {
        public void run() {
          cached = new File(cacheDir, filename);
          if (cached.exists()) {
            try {
              byte[] data = null;
              if (!diskOnly) {
                FileInputStream stream = new FileInputStream(cached);
                
                data = new byte[(int) cached.length()];
                stream.read(data);
                stream.close();
              }
              cached.setLastModified(System.currentTimeMillis()); // "touch" file (can be used in checkCache below)
              
              complete(data, false);
              return;
            } catch (IOException e) {
              Log.w(TAG, "Unable to load file #" + id + " from cache");
              e.printStackTrace();
            }
          }
          
          if (diskOnly) {
            try {
              if (cached.createNewFile()) {
                stream = new FileOutputStream(cached);
              }
            } catch (IOException e) {
              Log.w(TAG, "Unable to save file #" + id + " to disk cache");
              e.printStackTrace();
            }
          } else {
            buffer = new ByteArrayOutputStream(size);
          }
          
          load();
        }
      });
    }
    public void complete() {
      complete(buffer.toByteArray(), true);
    }
    public void complete(byte[] result, boolean saveToDisk) {
      state = COMPLETE;
      manager.activeJobs--;
      
      if (!diskOnly) {
        BufferedImage bitmap = null;
        try {
          bitmap = ImageIO.read(new ByteArrayInputStream(result));
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        
        // store in memory
        manager.loaded.put(new Element(id, bitmap));
        
        // send notifications
        for (FileLoadingCallback callback : callbacks) {
          callback.complete(type, bitmap);
        }
        for (WeakReference<JLabel> view : views) {
          final JLabel v = view.get();
          v.setIcon(new ImageIcon(bitmap));
          /*if (v != null && v.getTag() != null && ((Long) v.getTag() == id)) {
            service.activity.ui(new Runnable() {
              public void run() {
                v.setImageBitmap(bitmap);
              }
            }, true);
          }*/
        }
      
        // write to disk
        if (saveToDisk) {
          try {
            cached = new File(manager.cacheDir, "file" + Long.toHexString(id) + ".dat");
            if (cached.createNewFile()) {
              stream = new FileOutputStream(cached);
              stream.write(buffer.toByteArray());
              stream.close();
            }
            manager.checkCache();
          } catch (IOException e) {
            Log.w(TAG, "Unable to save file #" + id + " to disk cache");
            e.printStackTrace();
          }
        }
      } else {
        try {
          stream.close();
        } catch (Exception e) {
          Log.w(TAG, "Unable to close cache file");
          e.printStackTrace();
        }
        
        // send notifications
        for (FileLoadingCallback callback : callbacks) {
          callback.complete(type, cached);
        }
      }

      manager.progress.remove(id);
      manager.checkJobs();
    }
    public void cancel() {
      state = CANCELED;
      activeJobs--;
      
      if (diskOnly) {
        try {
          stream.close();
        } catch (IOException e) {
          Log.w(TAG, "Unable to close cache file");
          e.printStackTrace();
        }
        cached.delete();
      }
      
      for (FileLoadingCallback callback : callbacks) {
        callback.fail();
      }
      
      manager.progress.remove(id);
      manager.checkJobs();
    }
    public void append(byte[] bytes) {
      try {
        if (diskOnly) {
          stream.write(bytes);
        } else {
          buffer.write(bytes);
        }
      } catch (IOException e) {
        e.printStackTrace();
        cancel();
        return;
      }
      loaded += bytes.length;
      
      // a bit of cheating if size is unknown...
      float percent = (size > 0) ? (1.0f * loaded / size) : (1.0f * loaded / (loaded + (CHUNK_SIZE << 1)));
      for (FileLoadingCallback callback : callbacks) {
        if (callback instanceof FileLoadingProgressiveCallback) {
          ((FileLoadingProgressiveCallback) callback).progress(loaded, size, percent);
        }
      }
    }
    public void load() {
      manager.service.connectAndPrepare(dc_id, false, false, false, new AuthCallback() {
        public void error() {
          cancel();
        }
        public void done(Server server, byte[] auth_key) {
          server.call(new GetFile(location, loaded, CHUNK_SIZE), new Server.RPCCallback<tl.upload.File>() {
            public void done(tl.upload.File result) {
              append(result.bytes);
              if ((size > 0 && loaded <= size) || result.bytes.length < CHUNK_SIZE) {
                complete(diskOnly ? null : buffer.toByteArray(), true);
              } else {
                load();
              }
            }
            public void error(int code, String message) {
              Log.e(TAG, "Unable to load file #" + id);
              cancel();
            }
          });
        }
      });
    }
    public int getState() {
      int result = STATE_DOWNLOAD;
      
      float percent = (size > 0) ? (1.0f * loaded / size) : (1.0f * loaded / (loaded + (CHUNK_SIZE << 1)));
      result |= (int) (STATE_PROGRESS_MAX * percent);
      
      if (state == WAITING) {
        result |= STATE_QUEUED;
      } else if (state == LOADING) {
        result |= STATE_IN_PROGRESS;
      } else if (state == COMPLETE) {
        result |= STATE_COMPLETE;
      }
      
      result |= STATE_FILE_CACHE;
      if (!diskOnly) {
        result |= STATE_MEMORY_CACHE;
      }
      
      return result;
    }
  }
  public interface FileLoadingCallback {
    // data is either File or Bitmap
    public void complete(TFileType type, Object data);
    public void fail();
  }
  public interface FileLoadingProgressiveCallback extends FileLoadingCallback {
    public void progress(int loaded, int size, float percent);
  }
  
  public FileManager(DataService service) {
    this.service = service;
    this.cacheDir = service.getCacheDir();
    this.loaded = service.cacheManager.getCache("files");
  }
  
  // remove some files from cacheDir if they are taking too much space
  public void checkCache() {
    if (lastTimeCacheChecked == 0) {
      lastTimeCacheChecked = System.currentTimeMillis();
    }
    
    if (System.currentTimeMillis() - lastTimeCacheChecked < MIN_DISK_CACHE_CLEAR_INTERVAL) return;
    
    long bytes = 0;
    File[] files = cacheDir.listFiles();
    for (File file : files) {
      bytes += file.length();
    }
    
    if (bytes < MAX_DISK_CACHE) return;
    
    Arrays.sort(files, new Comparator<File>() {
      public int compare(File lhs, File rhs) {
        return Long.valueOf(lhs.lastModified()).compareTo(rhs.lastModified());
      }
    });
    
    for (File file : files) {
      if (bytes < MAX_DISK_CACHE || file.lastModified() > System.currentTimeMillis() - MIN_DISK_CACHE_STORE_TIME) return;
      file.delete();
    }
  }
  
  // is he still dead?
  public void checkJobs() {
    while (activeJobs < MAX_CONCURRENT_JOBS && queue.size() > 0) {
      FileLoadingJob job = queue.pop();

      if (job.state != FileLoadingJob.CANCELED) {
        job.start();
      }
    }
  }
  
  // Object is either TFileLocation or TVideo (same as in query())
  // result bits:
  // 0-15: loading progress (0-65535)
  // 16-17: loading state (0 - not loading, 1 - queued for download, 2 - downloading now, 3 - downloaded)
  // 18: available in memory
  // 19: available in file
  // 20: is upload
  // 31: reserved (must be 0)
  public static final int STATE_PROGRESS_MASK       = 0x0000ffff;
  public static final int STATE_PROGRESS_SHIFT      = 0;
  public static final int STATE_PROGRESS_MAX        = 65535;
  
  public static final int STATE_LOADING_MASK        = 0x00030000;
  public static final int STATE_LOADING_SHIFT       = 16;
  public static final int STATE_NOT_LOADING         = 0 << STATE_LOADING_SHIFT;
  public static final int STATE_QUEUED              = 1 << STATE_LOADING_SHIFT;
  public static final int STATE_IN_PROGRESS         = 2 << STATE_LOADING_SHIFT;
  public static final int STATE_COMPLETE            = 3 << STATE_LOADING_SHIFT;
  
  public static final int STATE_STORAGE_MASK        = 0x000c0000;
  public static final int STATE_STORAGE_SHIFT       = 18;
  public static final int STATE_MEMORY_CACHE        = 1 << STATE_STORAGE_SHIFT;
  public static final int STATE_FILE_CACHE          = 2 << STATE_STORAGE_SHIFT;
  
  public static final int STATE_DIRECTION_MASK      = 0x00100000;
  public static final int STATE_DIRECTION_SHIFT     = 20;
  public static final int STATE_DOWNLOAD            = 0 << STATE_DIRECTION_SHIFT;
  public static final int STATE_UPLOAD              = 1 << STATE_DIRECTION_SHIFT;
  
  public static final int STATE_INVALID             = 0xffffffff;
  public int getState(Object location) {
    if (location instanceof FileLocation) {
      return getState(((FileLocation) location).secret);
    } else
    if (location instanceof Video) {
      return getState(((Video) location).id);
    } else { // FileLocationUnavailable, VideoEmpty, and so on
      return STATE_INVALID;
    }
  }
  public long getId(Object location) {
    if (location instanceof FileLocation) {
      return ((FileLocation) location).secret;
    } else
    if (location instanceof Video) {
      return ((Video) location).id;
    } else {
      return -1;
    }
  }
  public File getFile(Object location) {
    return getFile(getId(location));
  }
  public File getFile(long id) {
    return new File(cacheDir, "file" + Long.toHexString(id) + ".dat");
  }
  public int getState(long id) {
    FileLoadingJob job = progress.get(id);
    if (job != null) {
      return job.getState();
    }
    
    if (uploading != null && uploading.id == id) {
      return uploading.getState();
    }
    
    for (FileUploadingJob upload : uploadQueue) {
      if (upload.id == id) {
        return upload.getState();
      }
    }
    
    /*if (loaded.get(id) != null) {
      return STATE_PROGRESS_MAX | STATE_COMPLETE | STATE_MEMORY_CACHE | STATE_FILE_CACHE | STATE_DOWNLOAD;
    }*/
    
    Integer state = states.get(id);
    if (state != null) {
      return state;
    }
    
    if (getFile(id).exists()) {
      state = STATE_PROGRESS_MAX | STATE_COMPLETE | STATE_FILE_CACHE | STATE_DOWNLOAD;
      states.put(id, state);
      return state;
    }
    
    return 0;
  }
  public int getStateBits(long id, int mask) {
    return getState(id) & mask;
  }
  public void setState(long id, int state) {
    states.put(id, state);
  }
  public void setStateBits(long id, int mask, int bits) {
    int state = getState(id);
    if ((state & mask) != bits) {
      setState(id, (state & ~mask) | bits);
    }
  }

  // objects can be fetched from 3 places: instantly from memory (loaded), quickly (but async) from file cache, slowly (async) from web
  // Object is either TFileLocation or TVideo
  // view is used when loading images, allows to have no callbacks at all and 
  // returns true if file was instantly loaded
  public boolean query(Object location, FileLoadingCallback callback) {
    return query(location, callback, null, 0);
  }
  public boolean query(Object location, FileLoadingCallback callback, JLabel view, int size) {
    long id = 0;
    int dc_id = 0;
    TInputFileLocation ilocation = null;
    
    /*if (view != null && view.getTag() != null) { // cancel previous job
      long old_id = (Long) view.getTag();
      
      FileLoadingJob oldJob = progress.get(old_id);
      if (oldJob != null) {
        int index = 0;
        for (WeakReference<JLabel> oldView : oldJob.views) {
          if (oldView.get() == view) {
            oldJob.views.remove(index);
            break;
          }
          index++;
        }
        
        if (oldJob.state == FileLoadingJob.WAITING && oldJob.views.size() == 0) { // it's not even started
          queue.remove(oldJob);
          progress.remove(old_id);
        }
      }
      view.setTag(null);
    }*/
    
    if (location instanceof FileLocation) {
      FileLocation flocation = (FileLocation) location;
      
      id = flocation.secret; // using secret as a hashcode

      Element cached = loaded.get(id);
      if (cached != null) {
        if (callback != null) {
          callback.complete(null, cached); // TODO: somehow store filetype in cache too
        }
        
        if (view != null) {
          view.setIcon(new ImageIcon((BufferedImage) cached.getObjectValue()));
        }
        return true;
      }
      
      dc_id = flocation.dc_id;
      ilocation = new InputFileLocation(flocation.volume_id, flocation.local_id, flocation.secret);
    } else
    if (location instanceof Video) {
      Video video = (Video) location;
      
      id = video.access_hash;      
      size = video.size;
      dc_id = video.dc_id;
      ilocation = new InputVideoFileLocation(video.id, video.access_hash);
    } else { // FileLocationUnavailable, VideoEmpty, and so on
      callback.fail();
      return true;
    }
    
    // join existing job or start a new one
    FileLoadingJob job = progress.get(id);
    if (job == null) {
      job = new FileLoadingJob(this, id, dc_id, ilocation, location instanceof Video, size);
      progress.put(id, job);
      queue.add(job);
    }
    
    if (job.state == FileLoadingJob.CANCELED) {
      callback.fail();
      return true;
    }
    
    if (callback != null) {
      job.callbacks.add(callback);
    }
    
    if (view != null) {
      job.views.add(new WeakReference<JLabel>(view));
      //view.setTag(id);
    }
    
    checkJobs();
    return false;
  }
  
  public boolean queryImage(TFileLocation location, JLabel view) {
    return queryImage(location, view, 0);
  }
  public boolean queryImage(TFileLocation location, JLabel view, int size) {
    return query(location, null, view, size);
  }
  
  
  
  // UPLOADING FILES
  
  public LinkedList<FileUploadingJob> uploadQueue = new LinkedList<FileUploadingJob>();
  public FileUploadingJob uploading = null;
  
  class FileUploadingJob {
    public static final int WAITING = 0;
    public static final int LOADING = 1;
    public static final int CANCELED = 2; // not really needed, as jobs are instantly removed on error (TODO: don't remove? for some time?)
    public static final int COMPLETE = 3;
    
    public static final int CHUNK_SIZE = 8 * 1024; // 8kb?
    
    byte[] data;
    String name;
    FileUploadingCallback callback;
    public int state = WAITING;
    
    public int part_size;
    public int part = 0;
    public long id;
    
    MessageDigest digest;
    
    public FileUploadingJob(long id, byte[] data, String name, FileUploadingCallback callback) {
      super();
      this.id = id;
      this.data = data;
      this.name = name;
      this.callback = callback;
    }
    
    public void start() {
      state = LOADING;
      uploading = this;
      
      part_size = CHUNK_SIZE; // TODO: implement some clever way to split file in chunks?
      
      id = (new Random()).nextLong();
      
      try {
        digest = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      
      upload();
    }
    public void complete() {
      state = COMPLETE;
      uploading = null;
      
      String hash = "";
      for (byte b : digest.digest()) {
        hash += ((b & 0xff) < 16 ? "0" : "") + Integer.toHexString(b & 0xff);
      }
      callback.complete(new InputFile(id, part, name, hash));
      
      checkUploadJobs();
    }
    public void cancel() {
      state = CANCELED;
      uploading = null;

      callback.fail();
      
      checkUploadJobs();
    }
    public void append() {
      part++;
      
      float percent = (1.0f * part * part_size / data.length);
      if (callback instanceof FileUploadingProgressiveCallback) {
        ((FileUploadingProgressiveCallback) callback).progress(part * part_size, data.length, percent);
      }
    }
    public void upload() {
      int size = Math.min(data.length - part * part_size, part_size);
      byte[] bytes = new byte[size];
      System.arraycopy(data, part * part_size, bytes, 0, size);
      digest.update(bytes);
      
      service.mainServer.call(new SaveFilePart(id, part, bytes), new Server.RPCCallback<TLObject>() {
        public void done(TLObject result) {
          append();
          if (part * part_size >= data.length) {
            complete();
          } else {
            upload();
          }
        }
        public void error(int code, String message) {
          Log.e(TAG, "Unable to upload file #" + id);
          cancel();
        }
      });
    }
    public int getState() {
      int result = STATE_UPLOAD;
      
      float percent = (1.0f * part * part_size / data.length);
      result |= (int) (STATE_PROGRESS_MAX * percent);
      
      if (state == LOADING) {
        result |= STATE_IN_PROGRESS;
      } else if (state == COMPLETE) {
        result |= STATE_COMPLETE;
      }
      
      return result;
    }
  }
  
  public void checkUploadJobs() {
    while (uploading == null && uploadQueue.size() > 0) {
      FileUploadingJob job = uploadQueue.pop();

      if (job.state != FileLoadingJob.CANCELED) {
        job.start();
      }
    }
  }
  
  public interface FileUploadingCallback {
    public void complete(TInputFile file);
    public void fail();
  }
  public interface FileUploadingProgressiveCallback extends FileUploadingCallback {
    public void progress(int loaded, int size, float percent);
  }
  
  public void upload(long id, byte[] data, String name, FileUploadingCallback callback) {
    uploadQueue.add(new FileUploadingJob(id, data, name, callback));
    checkUploadJobs();
  }
   
}
