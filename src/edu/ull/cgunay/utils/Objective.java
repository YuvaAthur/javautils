package neuroidnet.utils;
public class Objective implements Runnable {
    Object o;
    ParallelTask lock;
    Task task;

    public Objective(ParallelTask lock, Task task, Object o) {
	this.lock = lock;
	this.o = o;
	this.task = task;
    }

    public void run() {
	while (true) {
	    synchronized (lock) { 
		lock.waitcount++;
		try {
		    //System.out.println(o + " waiting..");
		    lock.wait();
		} catch (InterruptedException e) {
		    System.out.println("interrupted!!!" + e);
		    e.printStackTrace();
		}
		lock.waitcount--;
	    }
	    task.job(o);		// Out of synchronized, therefore concurrent
	    synchronized (lock) { 
		lock.runcount--;
		/*if (lock.runcount == 0) {
		  System.out.println(o + " notified one!");
			
		  lock.notify(); // Wake up controller
		  lock.reset();
		  }*/ // end of if (lock.count == 0)
		    
	    }
	} // end of while (true)	    
    }
}
