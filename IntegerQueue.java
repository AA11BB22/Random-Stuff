class IntegerQueue extends Queue<Integer>
{
    @Override
    public synchronized void enqueue(Integer value)
    {
        super.enqueue(value);
    }
    
    @Override
    public synchronized Integer peek() throws Exception
    {
        return super.peek();
    }
    
    @Override
    public synchronized Integer dequeue() throws Exception
    {
        return super.dequeue();
    }
    
    @Override
    public synchronized int size()
    {
        return super.size();
    }
    
    @Override
    public synchronized boolean equals(final Iterable<Integer> other)
    {
        return super.equals(other);
    }
}
