#include <iostream>
#include <string>

using namespace std;

const char value_delim = ',';
const char string_delim = '\"';
const string spaces = " \r\t";

std::string trim(const string &s)
{
    size_t start = s.find_first_not_of(spaces);
    size_t end = s.find_last_not_of(spaces) + 1;

    if (start < end) return s.substr(start, end);
    else return s;
}

int main()
{
    string line = "value 1, value 2, \"string 1\", \"string 2\" , value 3, \" a \" , \"string 4\", asd  ,   ";

    size_t start = 0;
    start = line.find_first_not_of(spaces, start);

    while (start < line.length())
    {
        size_t value_pos = line.find_first_of(value_delim, start);
        size_t string_pos = line.find_first_of(string_delim, start);

        if (value_pos < string_pos) // Find next comma.
        {
            // Do something here.
            cout << trim(line.substr(start, value_pos - start)) << endl;
        }
        else if (value_pos > string_pos) // Find next quote.
        {
            size_t end_pos = line.find_first_of(string_delim, string_pos + 1) + 1;

            // Do something here.
            cout << trim(line.substr(string_pos, end_pos - string_pos)) << endl;

            value_pos = line.find_first_of(value_delim, end_pos);
        }
        else break; // Can't find any.

        start = line.find_first_not_of(spaces, value_pos + 1);
        if (start == string::npos) break;
    }
}