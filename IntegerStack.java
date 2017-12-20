class IntegerStack extends Stack<Integer>
{
    @Override
    public synchronized void push(Integer value)
    {
        super.push(value);
    }
    
    @Override
    public synchronized Integer peek() throws Exception
    {
        return super.peek();
    }
    
    @Override
    public synchronized Integer pop() throws Exception
    {
        return super.pop();
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
