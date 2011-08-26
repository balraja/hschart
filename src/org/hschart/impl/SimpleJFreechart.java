package org.hschart.impl;


/**
 * Wraps JFreechart with an access to update model directly.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class SimpleJFreechart
{
    private static final String SERIES_NAME = "test";
    
    private final XYSeriesCollection myDataModel;
    
    private final ChartPanel myChartPanel;
    
    /**
     * CTOR
     */
    public SimpleJFreechart()
    {
        myDataModel = new XYSeriesCollection();
        myDataModel.addSeries(new XYSeries(SERIES_NAME));
        
        JFreeChart chart = 
            ChartFactory.createXYLineChart(
                "Chart Test", 
                "Num Updates", 
                "value",
                myDataModel, 
                PlotOrientation.VERTICAL, 
                false, 
                false,
                false);
        myChartPanel = new ChartPanel(chart, true);
    }
    
    /** Getter for the chart panel */
    public ChartPanel getChartpanel()
    {
        return myChartPanel;
    }
    
    /**
     * Updates the chart with new value.
     */
    public void updateChart(double x, double y)
    {
        myDataModel.getSeries(0).add(x, y);
    }
}
