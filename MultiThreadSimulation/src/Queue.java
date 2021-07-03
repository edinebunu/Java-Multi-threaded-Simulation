public class Queue extends Thread {
    private int time;
    private int awaitTime = 0;
    private int id;

    public Queue(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAwaitTime() {
        return awaitTime;
    }

    public void setAwaitTime(int awaitTime) {
        this.awaitTime = awaitTime;
    }

    @Override
    public void run() {
        super.run();
    }

    public void modifyTime(int t)
    {
        this.time = t;
        resetParams();

    }

    public void addPerson(Person p)
    {
        awaitTime += p.getServiceTime();

        System.out.println("Thread :" + id + "  person : " + p.getId());

    }

    private void resetParams()
    {
        awaitTime --;
        time++;
    }
}

