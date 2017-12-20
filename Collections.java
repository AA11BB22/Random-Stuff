class Collections
{
    // I honestly took more than 45 mins ... just let me use the built-in one :)
    // Disclaimer: There may still be bugs ...
    public static void main(String[] args) throws Exception
    {
        IntegerStack s1 = new IntegerStack();
        IntegerStack s2 = new IntegerStack();
        IntegerQueue q1 = new IntegerQueue();
        IntegerQueue q2 = new IntegerQueue();
        s1.push(17);
        s1.push(42);
        s1.push(88);
        s2.push(17);
        s2.push(42);
        s2.push(88);
        q1.enqueue(17);
        q1.enqueue(42);
        q1.enqueue(88);
        q2.enqueue(42);
        q2.enqueue(88);
        System.out.println(s1.equals(q1)); // false
        System.out.println(s1.equals(s2)); // true
        System.out.println(s2.equals(s1)); // true
        System.out.println(q1.equals(q2)); // false
        System.out.println(q2.equals(q1)); // false
        s1.pop();
        q1.dequeue();
        System.out.println(s1.equals(s2)); // false
        System.out.println(s2.equals(s1)); // false
        System.out.println(q1.equals(q2)); // true
        System.out.println(q2.equals(q1)); // true
    }
}
