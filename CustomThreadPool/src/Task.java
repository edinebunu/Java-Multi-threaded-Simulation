public class Task  {//client
    private int id;
    private int arrivalTime;
    private int processingTime;

    public Task(int arrivalTime,int processingTime, int id) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.id = id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public int getProcessingTime() {
        return processingTime;
    }
    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public void sleept(int time) throws InterruptedException {
        Thread.sleep(time);
    }
}