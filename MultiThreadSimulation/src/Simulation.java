import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulation {

    private int clients = 8;
    private int no_q = 3;
    private int totalTime = 60;
    private int minArrival = 2;
    private int maxArrival = 8;
    private int minServiceTime = 2;
    private int maxServiceTime = 4;

    ArrayList<Person> users = new ArrayList<>();



    public void start(){

        for(int i = 1; i<= clients; i++)
        {
            int arrival = ThreadLocalRandom.current().nextInt(minArrival, maxArrival + 1);
            int service = ThreadLocalRandom.current().nextInt(minServiceTime, maxServiceTime + 1);

            users.add(new Person(i,arrival,service));
        }

        for(int i = 0; i< clients; i++)
        {
            System.out.println("arrival " + users.get(i).getArrivalTime() + "  " +"  service: " + users.get(i).getServiceTime() );
        }
        System.out.println();

        Thread mainThread = new Thread(() -> {

            ArrayList<Queue> queues = new ArrayList<>();
            for(int i=1; i <= no_q; i++)
                queues.add(new Queue(i));

            for(int i=0; i < no_q; i++)
                queues.get(i).start();

            for(int i = 1; i<= totalTime; i++){

                for (Queue q : queues)
                    if(q.getAwaitTime() != 0)
                        queues.get(queues.indexOf(q)).modifyTime(i);

//                for (Queue q : queues)
//                    System.out.println(q.getAwaitTime());
//                System.out.println();

                if(isready(i))
                {
                    int minArr = getMinArrival();
                    Person currentPerson = getPerson(minArr);
                    Queue current = getShortQueue(queues);

                    current.addPerson(currentPerson);
                }
            }

        });
        mainThread.start();
    }

    private boolean isready(int time){
        for(Person p : users)
            if(p.getArrivalTime() <= time) return true;
        return false;
    }

    private int getMinArrival(){
        int min = users.get(0).getArrivalTime();

        for(Person p : users)
            if(p.getArrivalTime() <= min) min = p.getArrivalTime();

        return min;
    }

    private Person getPerson(int time){
        Person c = null;
        for(Person p : users)
            if(p.getArrivalTime() == time)
            {
                c = p;
                break;
            }
        users.remove(c);
        return c;
    }

    private Queue getShortQueue(ArrayList<Queue> queues){
        int minTime = queues.get(0).getAwaitTime();
        Queue f = queues.get(0);
        for (Queue q : queues)
        {
            if(q.getAwaitTime() < minTime){
                minTime = q.getAwaitTime();
                f = q;
            }
        }
        return f;
    }
}
