
package edu.ull.cgunay.utils.plots;

// $Id$
/**
 * Plots <code>Profile</code>s of entities that can be converted to a <code>double</code> value for
 * each of their recorded instances.
 * That is they should meaningfully define the <code>doubleValue()</code>
 * method of <code>Profilable</code>.
 * 
 * <p>Created: Sat Apr 13 14:47:44 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Profile
 * @see Profilable#doubleValue
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
