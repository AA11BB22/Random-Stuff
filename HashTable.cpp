#include <iostream>
#include <list>
#include <string>

/**
 * Hash Table Container
 * 
 * 1. Constructor requires a hash function and 
 *    an optional integer for size of table.
 *    (duplicates are allowed)
 *    (chaining via linked list)
 *
 * 2. Member Functions:
 *    - insert, O(1)
 *    - find, O(n)
 *    - size, n
 *    - display, O(n)
 */
template<class T>
class HashTable {

private:
    // Size of hash table.
    int n, current;
    
    // Hash table via chaining.
    std::list<T>* table;
    
    // Hash function.
    typedef int (*hashFunction)(T);
    hashFunction f;

public:
    // Constructor.
    HashTable(hashFunction f, int n = 100) {
        
        // Initialization.
        this->n = n;
        this->current = 0;
        this->table = new std::list<T>[n];
        this->f = f;
    }
    
    // Destructor
    ~HashTable() {
        
        delete[] this->table;
        this->table = nullptr;
    }
    
    // Insert to hash table.
    bool insert(const T& item) {
        
        // Index of item.
        int index = f(item);
        
        if (index >= n) return false;
        
        ++current;
        table[index].push_front(item);
        return true;
    }
    
    // Search.
    bool find(const T& item) const {
        
        int index = f(item);
        for (auto it = table[index].begin(); it != table[index].end(); ++it) {
            if (item == *it) {
                return true;
            }
        }
        
        return false;
    }
    
    // Current size.
    int size() const {
        return this->current;
    }
    
    // Print items.
    void display() const {
        
        std::cout << "List of items:" << std::endl;
        for (int i = 0; i < this->n; ++i) {
            for (auto it = table[i].begin(); it != table[i].end(); ++it) {
                std::cout << *it << std::endl;
            }
        }
    }
};


/************************************/
/*                                  */
/*         EXAMPLE OF USAGE         */
/*                                  */
/************************************/

const int size = 10;

int hashFunction(std::string text) {
    
    int value = 0;
    for (int i = 0; i < (int) text.length(); ++i) {
        value += (text.at(i) * 31);
    }
    
    return value % size;
}

int main() {
    
    HashTable<std::string> hash(hashFunction, size);
    
    std::string one = "onetwotherapf1j";
    std::string one_2 = "onetwotherapf1j";
    std::string two = "asda";
    
    hash.insert(one);
    std::cout << std::boolalpha << hash.find(one_2) << std::endl;

    hash.insert(two);
    
    std::cout << hash.size() << std::endl;
    
    hash.display();
}
