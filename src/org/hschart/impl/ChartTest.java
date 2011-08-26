package org.hschart.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.hschart.impl.SimpleDataModel.SimpleModelUpdate;

/**
 * A simple class to test the chart.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class ChartTest extends JFrame
{
    private final ChartPanel myChartPanel;
    
    private long myX = 0;
    
    private final Random myRandGenerator;
    
    /** CTOR */
    public ChartTest() throws HeadlessException
    {
        myChartPanel = new ChartPanel(new Dimension(1000, 500));
        myRandGenerator = new Random(1);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(myChartPanel, BorderLayout.CENTER);
    }
    
    /**
     * Runs the chart.
     */
    private void run()
    {
        long numValues = 3 * 60 * 60; 
        for (long i = 0; i < numValues; i++) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run()
                    {
                        myX++;
                        int y = (Math.abs(myRandGenerator.nextInt()) % 8000) + 1;
                        SimpleModelUpdate update = new SimpleModelUpdate(myX, y);
                        myChartPanel.getChart().updateChart(update);
                    }
                });
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        ChartTest test = new ChartTest();
        test.setSize(new Dimension(1000, 1000));
        test.setDefaultCloseOperation(EXIT_ON_CLOSE);
        test.setVisible(true);
        
        test.run();
        while(true);
    }
    
}
