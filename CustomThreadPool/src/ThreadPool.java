import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    private BlockingQueue<Task> taskQueue;
    private List<PoolThreadRunnable> servers = new ArrayList<>();
    private boolean isStopped = false;
    public static AtomicInteger currentTime=new AtomicInteger(0);
    Timer t = new Timer();

    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new ArrayBlockingQueue<>(maxNoOfTasks);

        for (int i = 0; i < noOfThreads; i++) {
            PoolThreadRunnable server = new PoolThreadRunnable(taskQueue);

            servers.add(new PoolThreadRunnable(taskQueue));
        }

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                currentTime.getAndIncrement();
                if(currentTime.intValue()==50)System.exit(0);
            };
        };
        t.scheduleAtFixedRate(tt,0,1500);

        for (PoolThreadRunnable runnable : servers) {
            new Thread(runnable).start();
            System.out.println();
        }

    }
    public synchronized void waitUntilAllTasksFinished() {
        while(this.taskQueue.size() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void execute(Task task){
        if (this.isStopped)
            throw new IllegalStateException(" ThreadPool is stopped");

        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        this.isStopped=true;
        for(PoolThreadRunnable server: servers) {
            if(server.isAlive().equals("RUNNABLE"))
            server.doStop();
            System.out.println("/");

        }
    }
}
