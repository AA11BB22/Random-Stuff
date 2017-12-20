class Queue<T> extends ListWrapper<T>
{
    public void enqueue(final T item)
    {
        this.list.pushBack(item);
    }
    
    public T peek() throws Exception
    {
        return this.list.getAt(0);
    }
    
    public T dequeue() throws Exception
    {
        return this.list.popFront();
    }
}
