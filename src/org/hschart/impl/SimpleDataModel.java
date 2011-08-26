package org.hschart.impl;

import java.util.ArrayList;
import java.util.List;

import org.hschart.DataModel;
import org.hschart.DataPoint;
import org.hschart.ModelIterator;
import org.hschart.ModelUpdate;
import org.hschart.Range;

/**
 * Type to encapsulate a chart model with a single data series.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class SimpleDataModel implements DataModel
{
    private final List<DataItem> myValues;
    
    private Range myDomainRange;
    
    private Range myValueRange;
    
    private int myLastUpdateStartIndex;
    
    /** Implements <code>ModelUpdate</code> for updating the model */
    public static class SimpleModelUpdate implements ModelUpdate
    {
        private final double myX;
        
        private final double myY;

        /**
         * CTOR
         */
        public SimpleModelUpdate(double x, double y)
        {
            super();
            myX = x;
            myY = y;
        }

        /**
         * Returns the value of x
         */
        public double getX()
        {
            return myX;
        }

        /**
         * Returns the value of y
         */
        public double getY()
        {
            return myY;
        }
    }
    
    /**
     * Type to encapsulate a data point on the chart.
     */
    private static class DataItem implements DataPoint
    {
        private final double myDomainValue;
        
        private final double myRangeValue;

        /**
         * CTOR
         */
        public DataItem(double domainValue, double rangeValue)
        {
            myDomainValue = domainValue;
            myRangeValue = rangeValue;
        }

        /**
         * Returns the value of domainValue
         */
        public double getDomainValue()
        {
            return myDomainValue;
        }

        /**
         * Returns the value of rangeValue
         */
        public double getRangeValue()
        {
            return myRangeValue;
        }
    }

    /** Implements ModelIterator to iterate over SimpleModel */
    private class SimpleModelIterator implements ModelIterator
    {
        private int myPrevIndex;
        
        private int myCurIndex;
        
        /**
         * CTOR
         */
        public SimpleModelIterator(int startIndex)
        {
            myCurIndex = startIndex - 1;
            myPrevIndex = myCurIndex - 1;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public DataPoint getPreviousDataPoint()
        {
            return myPrevIndex >= 0 ? myValues.get(myPrevIndex) : null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext()
        {
            myPrevIndex = myCurIndex;
            myCurIndex++;
            return myCurIndex <= (myValues.size() - 1);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public DataPoint next()
        {
            return myValues.get(myCurIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();            
        }
        
    }
    
    /**
     * CTOR
     */
    public SimpleDataModel()
    {
        myValues = new ArrayList<DataItem>();
        myDomainRange = null;
        myValueRange = null;
        myLastUpdateStartIndex = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Range getDomainRange()
    {
        return myDomainRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Range getValueRange()
    {
        return myValueRange;
    }

    /**
     * Sets the value of domainRange.
     */
    protected void setDomainRange(Range domainRange)
    {
        myDomainRange = domainRange;
    }

    /**
     * Sets the value of valueRange.
     */
    protected void setValueRange(Range valueRange)
    {
        myValueRange = valueRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleUpdate(ModelUpdate update)
    {
        SimpleModelUpdate smu = (SimpleModelUpdate) update;
        myLastUpdateStartIndex = myValues.isEmpty() ? 0 : myValues.size();
        myValues.add(new DataItem(smu.getX(), smu.getY()));
        
        if (myDomainRange == null && myValueRange == null) {
            myDomainRange = new Range(smu.getX(), smu.getX());
            myValueRange = new Range(smu.getY(), smu.getY());
        }
        else {
            if (smu.getX() < myDomainRange.getStart()) {
                myDomainRange = new Range(smu.getX(), myDomainRange.getEnd());
            }
            else if (smu.getX() > myDomainRange.getEnd()) {
                myDomainRange = new Range(myDomainRange.getStart(), smu.getX());
            }
            
            if (smu.getY() < myValueRange.getStart()) {
                myValueRange = new Range(smu.getY(), myValueRange.getEnd());
            }
            else if (smu.getY() > myValueRange.getEnd()) {
                myValueRange = new Range(myValueRange.getStart(), smu.getY());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelIterator iterator()
    {
        return new SimpleModelIterator(0);
    }
    
    /** Returns an iterator over the items of last update */
    public ModelIterator lastUpdateIterator()
    {
        return new SimpleModelIterator(myLastUpdateStartIndex);
    }

}
