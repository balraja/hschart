package org.hschart.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.hschart.Axis;
import org.hschart.Canvas;
import org.hschart.Chart;
import org.hschart.ChartHost;
import org.hschart.DataModel;
import org.hschart.Range;
import org.hschart.ScreenImage;
import org.hschart.ScreenImageFactory;

/**
 * Wraps {@link Chart} and provides space where a chart can be drawn
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class ChartPanel extends JPanel implements ChartHost
{
    /** Instance of {@link Chart} hosted by this panel */
    private final Chart myChart;
    
    /** Implements {@link ScreenImage} using a {@link BufferedImage} */
    private static class SwingScreenImage implements ScreenImage
    {
        private final BufferedImage myImage;
        
        /**
         * CTOR
         */
        public SwingScreenImage(BufferedImage image)
        {
            myImage = image;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Canvas getCanvas()
        {
            
            return new SwingCanvas(myImage.createGraphics(), 
                                   new Dimension(myImage.getWidth(),
                                                 myImage.getHeight()));
        }

        /**
         * Returns the value of image
         */
        public BufferedImage getImage()
        {
            return myImage;
        }
        
    }
    
    /**
     * Implements {@link ScreenImageFactory} to return <code>SwingScrrenImage
     * </code> 
     * 
     * @author subbiahb
     * @version $Id:$
     */
    public class SwingScreenImageFactory implements ScreenImageFactory
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public ScreenImage makeScreenImage(Dimension d)
        {
            BufferedImage bufferedImage =
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                                   .getDefaultScreenDevice()
                                   .getDefaultConfiguration()
                                   .createCompatibleImage(d.width, d.height);
            return new SwingScreenImage(bufferedImage);
        }
    }
    
    /**
     * CTOR
     */
    public ChartPanel(Dimension preferredSize)
    {
        setPreferredSize(preferredSize);

        NumberTickInfo tickInfo = 
            new NumberTickInfo(1, new DecimalFormat("#.0"));
        Axis domainAxis = 
            new AxisImpl(false, "Num Updates", tickInfo, new Range(1, 10));
        Axis rangeAxis =
            new AxisImpl(true, "value", tickInfo, new Range(1, 10));
        DataModel dm = new SimpleDataModel();
        myChart =
            new LineChart(domainAxis, 
                          rangeAxis, 
                          10, 
                          10,
                          dm, 
                          preferredSize,
                          new SwingScreenImageFactory(),
                          this);
        
        addComponentListener(
            new ComponentAdapter() {
                /**
                 * {@inheritDoc}
                 */
                @Override
                public void componentResized(ComponentEvent e)
                {
                    super.componentResized(e);
                    myChart.changeDimension(getSize());
                }
            });
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        ((Graphics2D) g).drawImage(
            ((SwingScreenImage) myChart.getImageOnScreen()).getImage(), 
            0,
            0,
            this);
    }
    
    /** Getter for {@link Chart} */
    public Chart getChart()
    {
        return myChart;   
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redraw()
    {
        repaint();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(Dimension d)
    {
        myChart.changeDimension(d);
        super.setSize(d);
    }
}
