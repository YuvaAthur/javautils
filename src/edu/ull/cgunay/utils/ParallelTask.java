package neuroidnet.utils;

//import java.lang.*;
import java.util.*;

public abstract class ParallelTask {
    public AbstractList a;
    public volatile int waitcount, runcount;
	
    public ParallelTask(AbstractList a) {
	this.a = a;
	waitcount = 0;
	reset();
	//init();
    }

    /**
     * Resets value of runcount to max # of threads
     *
     */
    public void reset() { runcount = a.size(); }

    public abstract void init();	// Needs to be defined!
}
