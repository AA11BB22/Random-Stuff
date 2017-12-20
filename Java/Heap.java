import java.util.*;

public abstract class Heap<T> implements Comparator<T>
{
    Set<T> set;
    List<T> list;
    
    public Heap()
    {
        set = new HashSet<T>();
        list = new ArrayList<T>();
    }
    
    @Override
    public int compare(final T x, final T y)
    {
        return 0;
    }
    
    public int size()
    {
        return list.size();
    }
    
    public boolean isEmpty()
    {
        return list.size() == 0;
    }
    
    public void push(final T item)
    {
        if (!set.contains(item))
        {
            set.add(item);
            list.add(item);
            moveUp(list.size() - 1);
        }
    }
    
    public T pop()
    {
        if (list.size() == 0)
        {
            return null;
        }
        else
        {
            swap(0, list.size()-1);
            T item = list.remove(list.size() - 1);
            set.remove(item);
            moveDown(0);
            return item;
        }
    }
    
    public void update(final T oldItem, final T newItem)
    {
        if (!set.contains(oldItem) || set.contains(newItem))
        {
        		return;
        }
        
        // Since it's already "sorted" ... use a binary-like search.
        // But for now ... going to just exhaustive search.
        for (int i = 0; i < list.size(); i ++)
        {
            if (list.get(i).equals(oldItem))
            {
                set.remove(oldItem);
                set.add(newItem);
                
                list.set(i, newItem);
                break;
            }
        }
        
        for (int i = parent(list.size() - 1); i >= 0; i --)
        {
            moveDown(i);
        }
    }
    
    private int parent(final int index)
    {
        return (index - 1) / 2;
    }
    
    private int left(final int index)
    {
        return 2 * index + 1;
    }
    
    private int right(final int index)
    {
        return 2 * index + 2;
    }
    
    private void swap(final int x, final int y)
    {
        T item = list.get(x);
        list.set(x, list.get(y));
        list.set(y, item);
    }
    
    private void moveUp(final int index)
    {
        int i = index;
        while (i > 0 && compare(list.get(parent(i)), list.get(i)) > 0)
        {
            swap(i, parent(i));
            i = parent(i);
        }
    }
    
    private void moveDown(final int index)
    {
        int c = index;
        int l = left(index);
        int r = right(index);
        
        if (l < list.size() && compare(list.get(c), list.get(l)) > 0)
        {
            c = l;
        }
        if (r < list.size() && compare(list.get(c), list.get(r)) > 0)
        {
            c = r;
        }
        
        if (c != index)
        {
            swap(c, index);
            moveDown(c);
        }
    }
    
    /**
     * @brief Example of a min heap.
     * @param args
     */
    public static void main(String[] args)
    {
        class MinHeap extends Heap<Integer>
        {
            @Override
            public int compare(Integer x, Integer y)
            {
                return x - y;
            }
        };
        
        MinHeap h = new MinHeap();
        
        for (int i = 0; i < 10; i++)
        {
            h.push(new Integer(i));
        }
        
        h.update(0, 100);
        h.update(1, 50);
        h.update(5, 1);
        
        while (h.size() > 0)
        {
            System.out.println(h.pop());
        }
    }
}
