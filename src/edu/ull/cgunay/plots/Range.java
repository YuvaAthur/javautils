
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

    /**
     * Adds given range to this one, resulting in enlarging this range.
     *
     * @param range a <code>Range</code> value
     */
    public void add(Range range) {
	start = Math.min(start, range.getStart());
	end = Math.max(end, range.getEnd());
    }

    /**
     * Returns the union of two ranges, that is the maximal range including both ranges.
     *
     * @param r1 a <code>Range</code> value
     * @param r2 a <code>Range</code> value
     * @return a <code>Range</code> value
     */
    public static Range max(Range r1, Range r2) {
	return new Range(Math.min(r1.getStart(), r2.getStart()),
			 Math.max(r1.getEnd(), r2.getEnd()));
    }
    
}// Range
