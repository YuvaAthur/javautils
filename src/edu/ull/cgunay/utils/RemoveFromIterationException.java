
package neuroidnet.utils;

// $Id$
/**
 * Thrown in the job() of an iteration (see class Iteration) to indicate that the current
 * object need be removed from the underlying collection.
 *
 *
 * <p>Created: Mon Apr 22 13:42:48 2002
 * <p>Modified: $Date$
 * @see Iteration
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class RemoveFromIterationException extends IterationException  {
    public RemoveFromIterationException () {
	
    }
    
}// RemoveFromIterationException
