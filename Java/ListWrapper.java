import java.util.Iterator;
import java.lang.Iterable;
import java.lang.NullPointerException;

class ListWrapper<T> implements Iterable<T>
{
    protected LinkedList<T> list;
    
    public ListWrapper()
    {
        this.list = new LinkedList<T>();
    }
    
    public int size()
    {
        return this.list.size();
    }
    
    public boolean equals(final Iterable<T> other)
    {
        if (this.getClass() != other.getClass())
        {
            return false;
        }
        
        Iterator<T> itThis = this.iterator();
        Iterator<T> itOther = other.iterator();
        
        try
        {
            while (itThis.hasNext() && itOther.hasNext())
            {
                if (itThis.next() != itOther.next())
                {
                    return false;
                }
            }
            
            return ((itThis.next() == null) && (itOther.next() == null));
        }
        catch (NullPointerException e)
        {
            return itThis == itOther;
        }
    }
    
    @Override
    public Iterator<T> iterator()
    {
        return this.list.iterator();
    }
}
