
package neuroidnet.ntr.plots;

// $Id$
/**
 * Plots <code>Profile</code>s.
 *
 * 
 * <p>Created: Sat Apr 13 14:47:44 2002
 * <p>Modified: $Date$
 *
 * @see Profile
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class ProfilePlot extends Plot  {
    Profile profile;

    public ProfilePlot (String title, Range range, Profile profile) {
	super(title, range);

	this.profile = profile;
	
	if (range == null) 
	    this.range = profile.getRange();
    }

    public ProfilePlot (Profile profile) {
	this(null, null, profile);
    }

    /**
     * Creates a profile string changing with variable "t". 
     * @see Grapher#profile
     * @return a <code>String</code> value
     */
    public String body() { 
	return profile(profile, range); 
    }
    
}// ProfilePlot
