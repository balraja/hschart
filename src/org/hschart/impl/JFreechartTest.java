package org.hschart.impl;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * A simple application which updates a JFeechart 3 * 60 * 60 times.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class JFreechartTest extends JFrame
{
    private long myX = 0;
    
    private final SimpleJFreechart myJfreeChart;
    
    private final Random myRandGenerator;
    
    /** CTOR */
    public JFreechartTest() throws HeadlessException
    {
        myRandGenerator = new Random(1);
       
        getContentPane().setLayout(new BorderLayout());
        myJfreeChart = new SimpleJFreechart();
        getContentPane().add(myJfreeChart.getChartpanel(), BorderLayout.CENTER);
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
                        myJfreeChart.updateChart(myX, y);
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
        JFreechartTest test = new JFreechartTest();
        test.setSize(new Dimension(1000, 1000));
        test.setDefaultCloseOperation(EXIT_ON_CLOSE);
        test.setVisible(true);
        
        test.run();
        while(true);
    }
    
}
