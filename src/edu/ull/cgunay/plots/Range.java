
package neuroidnet.ntr.plots;

// $Id$
/**
 * Holds the pair of <code>double</code>s indicating a range of a variable.
 *
 *
 * <p>Created: Mon Apr  8 17:15:38 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Grapher
 */

public class Range  {

    /**
     * Start of the range.
     */
    double start;
    
    /**
     * Get the value of start.
     * @return value of start.
     */
    public double getStart() {
	return start;
    }
    
    /**
     * Set the value of start.
     * @param v  Value to assign to start.
     */
    public void setStart(double  v) {
	this.start = v;
    }


    /**
     * End of the range.
     */
    double end;
    
    /**
     * Get the value of end.
     * @return value of end.
     */
    public double getEnd() {
	return end;
    }
    
    /**
     * Set the value of end.
     * @param v  Value to assign to end.
     */
    public void setEnd(double  v) {
	this.end = v;
    }

    public Range (double start, double end) {
	this.start = start;
	this.end = end;
    }
    
}// Range
