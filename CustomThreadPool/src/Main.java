import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Main {

    private static int clients = 8;
    private static int no_q = 3;
    private static int totalTime = 60;
    private static int minArrival = 2;
    private static int maxArrival = 8;
    private static int minServiceTime = 2;
    private static int maxServiceTime = 4;

    private static BlockingQueue<Task> generatedTasks;
    static ArrayList<Task> users = new ArrayList<>();


    public static void main(String[] args){
        SimulationManager s = new SimulationManager();
        s.run();
    }
}
