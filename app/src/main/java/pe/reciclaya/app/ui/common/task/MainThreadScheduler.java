package pe.reciclaya.app.ui.common.task;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public final class MainThreadScheduler implements TaskScheduler {
    private final Handler handler=new Handler(Looper.getMainLooper());
    @Override public long nowMillis() { return SystemClock.elapsedRealtime(); }
    @Override public void post(Runnable task,long delayMillis) { handler.postDelayed(task,delayMillis); }
    @Override public void remove(Runnable task) { handler.removeCallbacks(task); }
}
