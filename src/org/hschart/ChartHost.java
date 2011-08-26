package org.hschart;

/**
 * Type to denote the class that paints the chart on it. For now its
 * Swing's JComponent
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface ChartHost
{
    /** Subclasses should implement this method for handling chart change */
    public void redraw();
}
