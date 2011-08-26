package org.hschart;

import java.awt.Dimension;

/**
 * Type to denote a simple XYChart.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface Chart
{
    /** Refreshes the chart with new changes to the model */
    public void updateChart(ModelUpdate update);
    
    /** Returns the chart image on screen */
    public ScreenImage getImageOnScreen();
    
    /** Changes the chart dimension */
    public void changeDimension(Dimension dim);
}
