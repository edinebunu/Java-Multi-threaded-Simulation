import java.sql.SQLOutput;
import java.util.concurrent.BlockingQueue;

public class PoolThreadRunnable implements Runnable{//PoolThreadRunnable
    private BlockingQueue<Task> tasks;
    private boolean isStopped =false;
    private Thread thread = null;

    Thread.State isAlive(){
        return Thread.currentThread().getState();
    }

    public PoolThreadRunnable(BlockingQueue<Task> tasks) {

        this.tasks = tasks;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();
        while(!isStopped()) {
            try {
                Task currentTask=tasks.take();
                int currTime=ThreadPool.currentTime.intValue();

                while(currTime < currentTask.getArrivalTime())
                {
                    currTime=ThreadPool.currentTime.intValue();

                }
                System.out.println(this.thread.getName()+" Time: "+currTime + "  client arrival:" + currentTask.getArrivalTime()+" client id: "+currentTask.getId());

                Thread.sleep(1000*currentTask.getProcessingTime());
            }catch (Exception ignored) {
            }
        }
    }

    private synchronized boolean isStopped() {
        // TODO Auto-generated method stub
        return isStopped;
    }

    public synchronized void doStop() {
        // TODO Auto-generated method stub
        isStopped=true;
        this.thread.interrupt();

    }

}