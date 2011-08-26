package org.hschart;

/**
 * Type that defines any number range.
 * 
 * @author subbiahb
 * @version $Id:$
 * 
 */
public class Range implements Comparable<Range>
{
    /** Defines the start of range */
    private final double myStart;

    /** Defines the end of range */
    private final double myEnd;

    /**
     * CTOR
     */
    public Range(double start, double end)
    {
        assert (end >= start);
        myStart = start;
        myEnd = end;
    }

    /**
     * Returns the value of start
     */
    public double getStart()
    {
        return myStart;
    }

    /**
     * Returns the value of end
     */
    public double getEnd()
    {
        return myEnd;
    }
    
    /** Returns the difference between end and start */
    public double getDifference()
    {
        return myEnd - myStart;
    }
    
    /** Returns true, if the given range is contained by this range */
    public boolean contains(Range newRange)
    {
        return (myStart <= newRange.getStart() && newRange.getEnd() <= myEnd);
    }
    
    /** Merges the given range with this range */
    public Range merge(Range range)
    {
        if (myStart <= range.getStart()) {
            if (range.getEnd() <= myEnd) {
                return this;
            }
            else {
                return new Range(myStart, range.getEnd());
            }
        }
        else {
            if (myEnd <= range.getEnd()) {
                return range;
            }
            else {
                return new Range(range.getStart(), myEnd);
            }
        }
        
    }
    
    public boolean leftIntersect(Range other)
    {
        return myStart <= other.getStart() && myEnd <= other.getEnd();
    }
    
    public boolean rightIntersect(Range other)
    {
        return myStart <= other.getStart() && myEnd <= other.getEnd();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(myEnd);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(myStart);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Range other = (Range) obj;
        if (Double.doubleToLongBits(myEnd) != Double
                .doubleToLongBits(other.myEnd))
            return false;
        if (Double.doubleToLongBits(myStart) != Double
                .doubleToLongBits(other.myStart))
            return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "Range [myEnd=" + myEnd + ", myStart=" + myStart + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Range o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
