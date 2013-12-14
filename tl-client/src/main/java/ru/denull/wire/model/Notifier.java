package ru.denull.wire.model;

import java.util.concurrent.Callable;

public class Notifier {
    // CONSTANTS FOR EVENTS (project-dependent)
    // 0..9 - MainActivity/global UI related events
    public static final long MAIN_ACTIVITY_CREATED = (1L << 0);
    public static final long MAIN_ACTIVITY_STARTED = (1L << 1);
    public static final long MAIN_ACTIVITY_RESUMED = (1L << 2);
    public static final long DATA_SERVICE_CONNECTED = (1L << 3);

    // 10..19 - DataService/global data related events
    public static final long DATA_SERVICE_CREATED = (1L << 10);
    public static final long CACHING_DB_STARTED = (1L << 11);
    public static final long MAIN_SERVER_CONNECTED = (1L << 12);
    public static final long MAIN_SERVER_AUTH_KEY = (1L << 13);
    public static final long AUTHORIZED = (1L << 14);

    // 20..39 - Fragments/local UI events
    public static final long LOGIN_PHONE_FRAGMENT_STARTED = (1L << 20);

    // 40..59 - Loading/local data events

    // CONSTANTS FOR EVENTS END

    // 60..63 - flags
    // CALL_EACH_TIME: if set, callback is not removed after the first visit to the state
    public static final long CALL_EACH_TIME = (1L << 60);
    // MULTIPLE: callback is called even if we are already in matching state
    public static final long MULTIPLE = (1L << 61);
    // NOT_NOW: callback is not called straight away if we are already in matching state
    public static final long NOT_NOW = (1L << 62);

    public static long state = 0;

    public interface NotifierCallback {
    }

    public interface NotifierAfterCallback extends NotifierCallback {
        public void afterEvent(long state, long entered, long left, long events, long tag);
    }

    // TODO: add "before()"?
    // TODO: guarantee that callback will always be called in the same order they were set
    // returns true if instantly called (never returns true if flag NOT_NOW is set)
    public static boolean after(long events, Runnable callback) {

        return false;
    }

    // callback receives current state as param
    public static boolean after(long events, Callable<Integer> callback) {

        return false;
    }

    public static boolean callback(long events, NotifierCallback callback, long tag) {

        return false;
    }

    public static void enter(long state) {

    }

    public static void leave(long state) {

    }
}
