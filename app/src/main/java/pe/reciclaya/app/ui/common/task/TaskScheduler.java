package pe.reciclaya.app.ui.common.task;

public interface TaskScheduler { long nowMillis(); void post(Runnable task,long delayMillis); void remove(Runnable task); }
