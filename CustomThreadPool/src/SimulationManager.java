import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

public class SimulationManager {

    private int clients = 60;
    private int no_q = 8;
    private int totalTime = 60;
    private int minArrival = 2;
    private int maxArrival = 8;
    private int minServiceTime = 2;
    private int maxServiceTime = 4;

    ArrayList<Task> users = new ArrayList<>();
    void run(){
        for(int i = 1; i<= clients; i++)
        {
            int arrival = ThreadLocalRandom.current().nextInt(minArrival, maxArrival + 1);
            int service = ThreadLocalRandom.current().nextInt(minServiceTime, maxServiceTime + 1);

            users.add(new Task(arrival,service,i));
        }
        for(int i = 0; i< clients; i++)
        {
            System.out.println("arrival " + users.get(i).getArrivalTime() + "  " +"  service: " + users.get(i).getProcessingTime());
        }

        for(int i = 0; i< clients-1; i++)
            for(int j=i+1;j<clients;j++)
            {
                if(users.get(i).getArrivalTime() > users.get(j).getArrivalTime())
                {
                    Task a = users.get(i);
                    users.set(i,users.get(j));
                    users.set(j,a);
                }
            }


        ThreadPool threadPool = new ThreadPool(no_q, clients);

        for(int i=0; i<60; i++) {

            threadPool.execute(users.get(i));
        }

        threadPool.waitUntilAllTasksFinished();
        threadPool.stop();
        for(int i = 0; i< clients; i++)
            System.out.println(users.get(i).getId()+ "   " + users.get(i).getArrivalTime());

    }

    public static void main(String[] args) {

    }
}