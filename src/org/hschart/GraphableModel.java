package org.hschart;

/**
 * Type to denote the graphical representation of a model.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public abstract class GraphableModel implements Graphable
{
    private final ModelIterator myModelIterator;
    
    /**
     * CTOR
     */
    public GraphableModel(ModelIterator modelIterator)
    {
        myModelIterator = modelIterator;
    }
    
    /** The iterator over the model to be used for graphing */
    protected ModelIterator getIterator()
    {
        return myModelIterator;
    }
}
