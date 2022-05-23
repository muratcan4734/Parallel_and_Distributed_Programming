public class Question1 {
    private static int PlatformAvailableResource = 0;

    synchronized static void IncrementPlatformResource() {
        PlatformAvailableResource++;
    }

    synchronized static void DecrementPlatformResource() {
        PlatformAvailableResource--;
    }

    synchronized static int GetPlatformResource() {
        return PlatformAvailableResource;
    }

    private static class PlatformAcess implements Runnable {
        public void run() {
            DecrementPlatformResource();
            System.out.println(Thread.currentThread().getName() + ": is executing completed" + "\nAvailable PF resources : " + (100 - GetPlatformResource()));
        }
    }

    public static void main(String[] args) {
        Thread[] t = new Thread[150];
        Random rand = new Random();
        for (int i = 0; i < 150; i++) {
            int threadId = rand.nextInt(100);
            System.out.println("Created Random thread for Platform Acess with threadId : " + threadId);
            try {
                while (t[threadId].isAlive()) {
                    System.out.println("Waiting...Since threadId " + threadId + " is In running state.");
                }
            } catch (Exception ex) {
                t[threadId] = new Thread(new PlatformAcess(), "Thread-".concat(Integer.toString(threadId)));
                System.out.println("Generated ThreadID : " + threadId);
                t[threadId].start();
                if (GetPlatformResource() >= 100) {
                    t[threadId].stop();
                } else {
                    IncrementPlatformResource();
                    System.out.println(Thread.currentThread().getName() + ": Started for executing" + "\nAvailable PF resources : " + (100 - GetPlatformResource()));
                }
            }
        }
    }
}
