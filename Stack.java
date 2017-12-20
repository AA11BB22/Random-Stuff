class Stack<T> extends ListWrapper<T>
{
    public void push(final T item)
    {
        this.list.pushBack(item);
    }
    
    public T peek() throws Exception
    {
        return this.list.getAt(this.list.size() - 1);
    }
    
    public T pop() throws Exception
    {
        return this.list.popBack();
    }
}
