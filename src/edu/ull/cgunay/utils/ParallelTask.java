package edu.ull.cgunay.utils;

//import java.lang.*;
import java.util.*;

/**
 * Parallel analog of an iteration where a given task is executed on 
 * each of the items of a collection concurrently. The <code>init()</code>
 * method creates threads for all items and the <code>step()</code> method
 * contains the synchronization algorithm required to start and end all tasks
 * concurrently.
 * <p>Subsequent calls to <code>step()</code> is mutually exclusive
 * by ensuring that the the new step does
 * not start until the previous step is completed.
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file
 * @see Iteration
 */
public class ParallelTask implements Simulation {

    /**
     * The collection on which to iterate.
     *
     */
    public Collection collection;

    /**
     * Semaphore for threads that are waiting.
     *
     */
    public volatile int waitcount;

    /**
     * Semaphore for threads that are running.
     *
     */
    public volatile int runcount;

    /**
     * Task to be executed at each step by each thread.
     */
    Task task;
	
    /**
     * Set collection and task.
     *
     * @param collection a <code>Collection</code> value
     * @param task a <code>Task</code> value
     */
    public ParallelTask(Collection collection, Task task) {
	this.collection = collection;
	this.task = task;

	waitcount = 0;
	reset();
	//init();
    }

    /**
     * Resets value of runcount to max # of threads.
     *
     */
    public void reset() {
	runcount = collection.size();
    }

    /**
     * Start a new thread containing the <code>Objective</code> runnable
     * for each element in the collection.
     * @see Objective
     */
    public void init() {
	// iterate over elements and associate threads
	UninterruptedIteration.loop(collection, new Task() {
		public void job(Object o) {
		    new Thread(new Objective(ParallelTask.this, task, o)).start();
		}
	    });
    }

    /**
     * Does the following:
     * <ol>
     *   <li> Wait for all threads to go in wait() (until waitcount = max)
     *   <li> Notify all of them, therefore make them execute the task.
     *   <li> Wait for all of the to finish (until runcount == 0). 
     */
    synchronized public void step() {
	reset();

	try {
	    // Wait until all threads (Objectives) are in wait()
	    while (waitcount < collection.size()) {
		//System.out.println("Waiting for others to wait!");
		wait(10);
	    }
	} catch (InterruptedException e) {
	    System.out.println("interrupted!!!" + e);
	    e.printStackTrace();
	}

	// Unleash them to do the job concurrently
	notifyAll();
	//System.out.println("notified all!");

	try {
	    //System.out.println("Waiting for others to finish!");
	    //wait();		// Give up lock, become first one in queue!

	    // Wait until all complete the job
	    while (runcount > 0) {
		//System.out.println("Waiting for others to finish!");
		wait(10);
	    }
	} catch (InterruptedException e) {
	    System.out.println("interrupted!!!" + e);
	    e.printStackTrace();
	}

    }
}
