import java.lang.Iterable;
import java.util.Iterator;

/**
 * @brief Single linked list.
 * @param <T>
 */
class LinkedList<T> implements Iterable<T>
{
    final static String INVALID_INDEX = "Invalid index.";
    final static String EMPTY = "List is empty.";
    
    @SuppressWarnings("hiding")
    private class Node<T>
    {
        public T item;
        public Node<T> next;
        
        public Node(final T item, final Node<T> next)
        {
            this.item = item;
            this.next = next;
        }
    };
    
    @Override
    public Iterator<T> iterator()
    {
        return new ListIterator<T>(nodeStart);
    }
    
    @SuppressWarnings("hiding")
    private class ListIterator<T> implements Iterator<T>
    {
        private Node<T> current;
        
        public ListIterator(Node<T> node)
        {
            this.current = node;
        }
        
        @Override
        public boolean hasNext()
        {
            return this.current != null;
        }
        
        @Override
        public T next()
        {
            if (this.current != null)
            {
                T item = this.current.item;
                
                this.current = this.current.next;
                
                return item;
            }
            else
            {
                return null;
            }
        }
    }
    
    private int sz;
    private Node<T> nodeStart;
    private Node<T> nodeEnd;
    
    public LinkedList()
    {
        this.sz = 0;
        this.nodeStart = null;
        this.nodeEnd = null;
    }
    
    public void pushFront(final T item)
    {
        // Create new node.
        Node<T> nodeNew = new Node<T>(item, this.nodeStart);
        
        // Update start node.
        this.nodeStart = nodeNew;
        
        // Update end node.
        if (sz == 0)
        {
            this.nodeEnd = this.nodeStart;
        }
        
        sz ++;
    }
    
    public void pushBack(final T item)
    {
        if (sz == 0)
        {
            this.pushFront(item);
        }
        else
        {
            // Create new node.
            Node<T> nodeNew = new Node<T>(item, null);
            
            // Connect new node.
            this.nodeEnd.next = nodeNew;
            
            // Update end node.
            this.nodeEnd = this.nodeEnd.next;
            
            sz ++;
        }
    }
    
    public void insertAt(final int index, final T item) throws Exception
    {
        if (index < 0 || index > sz)
        {
            throw new Exception(INVALID_INDEX);
        }
        else
        {
            if (index == 0) // Case front.
            {
                this.pushFront(item);
            }
            else if (index == sz) // Case back.
            {
                this.pushBack(item);
            }
            else // Case middle.
            {
                // Iterate temporary to index.
                Node<T> nodeTempEarlier = this.nodeStart;
                Node<T> nodeTempAfter = nodeTempEarlier.next;
                
                for (int i = 0; i < index - 1; i ++)
                {
                    nodeTempEarlier = nodeTempAfter;
                    nodeTempAfter = nodeTempAfter.next;
                }
                
                // Create new node.
                Node<T> nodeNew = new Node<T>(item, nodeTempAfter);
                
                // Update connection.
                nodeTempEarlier.next = nodeNew;
                
                sz ++;
            }
        }
    }
    
    public T getAt(final int index) throws Exception
    {
        if (index < 0 || index > sz)
        {
            throw new Exception(INVALID_INDEX);
        }
        else
        {
            Node<T> nodeTemp = this.nodeStart;
            
            for (int i = 0; i < index; i ++)
            {
                nodeTemp = nodeTemp.next;
            }
            
            return nodeTemp.item;
        }
    }
    
    public int size()
    {
        return this.sz;
    }
    
    public T popFront() throws Exception
    {
        if (sz == 0)
        {
            throw new Exception(EMPTY);
        }
        else
        {
            sz --;
            
            T item = this.nodeStart.item;
            
            // Update start node.
            this.nodeStart = this.nodeStart.next;
            
            // Update end node.
            if (sz == 0)
            {
                this.nodeEnd = null;
            }
            
            return item;
        }
    }
    
    public T popBack() throws Exception
    {
        if (sz <= 1)
        {
            return this.popFront();
        }
        else
        {
            sz --;
            
            T item = this.nodeEnd.item;
            
            // Iterate temporary to before end.
            Node<T> nodeTemp = this.nodeStart;
            
            for (int i = 0; i < sz - 1; i ++)
            {
                nodeTemp = nodeTemp.next;
            }
            
            // Update end node.
            this.nodeEnd = nodeTemp;
            
            // Update connection.
            this.nodeEnd.next = null;
            
            return item;
        }
    }
    
    public T popAt(final int index) throws Exception
    {
        if (index < 0 || index >= sz)
        {
            throw new Exception(INVALID_INDEX);
        }
        else
        {
            if (index == 0) // Case front.
            {
                return this.popFront();
            }
            else if (index == sz) // Case back.
            {
                return this.popBack();
            }
            else // Case middle.
            {
                sz --;
                
                // Iterate temporary to index.
                Node<T> nodeTempEarlier = this.nodeStart;
                Node<T> nodeTempAfter = nodeTempEarlier.next;
                
                for (int i = 0; i < index - 1; i ++)
                {
                    nodeTempEarlier = nodeTempAfter;
                    nodeTempAfter = nodeTempAfter.next;
                }
                
                T item = nodeTempAfter.item;
                
                // Update connection.
                nodeTempEarlier.next = nodeTempAfter.next;
                
                return item;
            }
        }
    }
}
