package edu.ull.cgunay.utils;
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
	try {
	    while (true) {
		synchronized (lock) { 
		    lock.waitcount++;

		    //System.out.println(o + " waiting..");
		    lock.wait();
		    lock.waitcount--;
		}

		try {
		    task.job(o); // Out of synchronized, therefore concurrent		 
		} catch (TaskException e) {
		    throw new Error("" + e);
		} // end of try-catch

		synchronized (lock) { 
		    lock.runcount--;
		    /*if (lock.runcount == 0) {
		      System.out.println(o + " notified one!");
			
		      lock.notify(); // Wake up controller
		      lock.reset();
		      }*/ // end of if (lock.count == 0)
		    
			}
	    } // end of while (true)	    
	} catch (InterruptedException e) {
	    System.out.println("Received interrupt, exiting thread in + " + this);
	}
    }
}
