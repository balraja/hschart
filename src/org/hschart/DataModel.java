package org.hschart;

/**
 * Type that defines the basic interface of a chart.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface DataModel 
{
    /**
     * Returns the range spawned by domain axis values.
     */
    public Range getDomainRange();
    
    /**
     * Returns the range spawned by value axis.
     */
    public Range getValueRange();
    
    /** Updates the chart model */
    public void handleUpdate(ModelUpdate update);
    
    /** Returns an iterator for the model */
    public ModelIterator iterator();
}
