#include <iostream>
#include <string>

/**
 * Hash Table Container
 *
 * Constructor requires a hash function,
 *    optionally uses integer conversion of the item 
 *    and an optional integer for size of table.
 */
template<class T>
class HashTableLinear {

private:
    // Size of hash table.
    int n, current;

    // Hash table via chaining.
    bool* occupied;
    T* table;

    // Hash function.
    typedef int (*hashFunction)(T);
    hashFunction f;

public:
    // Constructor
    HashTableLinear(hashFunction f = [](T item)->int{return (int)(item);}, int n = 1001) {
        
        // Initialization.
        if (n <= 10) n = 11;
        else this->n = n;
        this->current = 0;
        this->occupied = new bool[n]; // all false by default
        this->table = new T[n];
        this->f = f;
    }
    
    // Destructor
    ~HashTableLinear() {
        
        delete[] this->occupied;
        delete[] this->table;
        
        this->occupied = nullptr;
        this->table = nullptr;
    }

    // Insert to hash table.
    bool insert(const T& item) {

        // Index of item.
        int index = f(item) % n;
        ++current;
        
        while (true) {
            
            if (!occupied[index]) {
                
                occupied[index] = true;
                table[index] = item;
                break;
            }
            index = (index + 1) % n;
        }
        
        // Resize for linear probing to function optimally.
        if ((int)(n * 0.85) < current) {
            
            int temp_size = n;
            n = n * 2 + 1;
            
            bool* temp_occupied = occupied;
            occupied = new bool[n];
            
            T* temp_table = table;
            table = new T[n];
            
            for (int i = 0; i < temp_size; ++i) {
                
                if (temp_occupied[i]) {  
                    
                    int idx = f(temp_table[i]) % n;
                    while (occupied[idx]) idx = (idx + 1) % n;
                    
                    occupied[idx] = true;
                    table[idx] = temp_table[i];
                }
            }
            
            delete[] temp_occupied;
            delete[] temp_table;
            
            temp_occupied = nullptr;
            temp_table = nullptr;
        }
        
        return true;
    }

    // Search
    bool find(const T& item) const {

        int index = f(item) % n;
        while (occupied[index] && index < n) {
            
            if (table[index] == item) {
                return true;
            }
            
            ++index;
        }
        
        return false;
    }

    // Current Size
    int size() const {
        return this->current;
    }

     void display() const {

        std::cout << "List of items:" << std::endl;
        for (int i = 0; i < this->n; ++i) {
            std::cout << i << " -> ";
            if (occupied[i]) {
                std::cout << table[i];
            }
            std::cout << std::endl;
        }
    }
};


/************************************/
/*                                  */
/*         EXAMPLE OF USAGE         */
/*                                  */
/************************************/

int hashFunction(std::string url) {
    
    int value = 0;
    for (int i = 0; i < (int) url.length(); ++i) {
        value += ((int) url.at(i) * 31);
    }
    
    return value * (int) url.length();
}

int main() {
    
    HashTableLinear<std::string> hash(hashFunction, 11);
    
    hash.insert("0");
    hash.insert("1");
    hash.insert("2");
    hash.insert("3");
    hash.insert("4");
    hash.insert("5");
    hash.insert("6");
    hash.insert("7");
    hash.insert("8");
    hash.display();
    
    std::cout << std::endl << "Resizing..." << std::endl << std::endl;
    
    hash.insert("9");
    hash.insert("10");
    hash.insert("11");
    hash.insert("12");
    hash.insert("13");
    hash.insert("14");
    hash.insert("15");
    hash.insert("16");
    hash.insert("17");
    hash.insert("18");
    hash.display();
    
    std::cout << std::endl << "Resizing..." << std::endl << std::endl;
    
    hash.insert("19");
    hash.insert("20");
    hash.insert("21");
    hash.insert("22");
    hash.insert("23");
    hash.display();
    
    std::cout << std::endl << "Search..." << std::endl << std::endl;
    
    std::cout << std::boolalpha << "11 in table, " << hash.find("11") << std::endl;
    std::cout << std::boolalpha << "40 in table, " << hash.find("40") << std::endl;
    
    std::cout << hash.size() << std::endl;

}