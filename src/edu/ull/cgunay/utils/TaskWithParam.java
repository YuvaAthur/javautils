package Utils;
public interface TaskWithParam {
    /**
     * Method to be called by external task givers.
     * @see Utils#loop
     * @param o the iterating value
     * @param p an <code>Object</code> value determined by the call to Utils.loop 
     */
    void job(Object o, Object[] p);
}
