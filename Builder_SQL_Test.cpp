#include <iostream>
#include <string>
#include <stack>
#include <assert.h>

using namespace std;

class Database
{
public:
    class SQL;
    
    void ResetQuery() { m_szQuery = ""; }
    string GetQuery() const { return m_szQuery; }
    
private:
    string m_szQuery;
    
    Database(string Query) : m_szQuery(Query) { }
};

static const string OR = " OR ";
static const string AND = " AND ";

class Database::SQL
{
public:
    SQL()
    {
        m_szQuery = "";
        m_bSelect = true;
        m_bFrom = true;
        m_bWhere = true;
    }
    
    Database EndSQL();
    
    template<class T>
    SQL SELECT(T tail)
    {
        m_szQuery += (string) tail;
        m_bSelect = false;
        return *this;
    }
    
    template<class T, class... Ts>
    SQL SELECT(T head, Ts... tail)
    {
        assert(m_bSelect);
        
        if (m_szQuery.length() == 0) m_szQuery += "SELECT ";
        m_szQuery += ((string) head + ", ");
        return SELECT(tail...);
    }
    
    SQL FROM(const string &szDatabase)
    {
        assert(m_bFrom);
        
        m_szQuery += (" FROM " + szDatabase);
        m_bFrom = false;
        return *this;
    }
    
    SQL WHERE()
    {
        assert(m_bWhere);
        
        m_szQuery += " WHERE ";
        m_bWhere = false;
        return *this;
    }
    
    SQL StartOR();
    SQL EndOR();
    
    SQL StartAND();
    SQL EndAND();
    
    SQL EQUAL(const string &szFieldName, const string &szValue);

private:
    string m_szQuery;
    stack<string> m_Operations;
    
    bool m_bSelect;
    bool m_bFrom;
    bool m_bWhere;
};

Database::SQL Database::SQL::StartOR()
{
    if (m_Operations.size()) m_szQuery += m_Operations.top();
    
    m_szQuery += '(';
    m_Operations.push(OR);
    return *this;
}

Database::SQL Database::SQL::EndOR()
{
    assert(m_Operations.size() && m_Operations.top() == OR && m_szQuery.length() && m_szQuery[m_szQuery.length() - 1] != '(');
    
    m_szQuery += ')';
    m_Operations.pop();
    return *this;
}

Database::SQL Database::SQL::StartAND()
{
    if (m_Operations.size()) m_szQuery += m_Operations.top();
    
    m_szQuery += '(';
    m_Operations.push(AND);
    return *this;
}

Database::SQL Database::SQL::EndAND()
{
    assert(m_Operations.size() && m_Operations.top() == AND && m_szQuery.length() && m_szQuery[m_szQuery.length() - 1] != '(');
    
    m_szQuery += ')';
    m_Operations.pop();
    return *this;
}

Database::SQL Database::SQL::EQUAL(const string &szFieldName, const string &szValue)
{
    if (m_Operations.size() && m_szQuery.length() && m_szQuery[m_szQuery.length() - 1] != '(') m_szQuery += m_Operations.top();
    m_szQuery += (szFieldName + " = \'" + szValue + '\'');
    return *this;
}

Database Database::SQL::EndSQL()
{
    assert(m_Operations.size() == 0);
    
    return Database(m_szQuery);
}

int main()
{
    Database Db = Database::
                    SQL().SELECT("A", "B", "C", "D").FROM("ABC").WHERE().
                        StartOR().
                            EQUAL("column 1", "value").
                            EQUAL("column 2", "value").
                            StartAND().
                                EQUAL("column 3", "value").
                                EQUAL("column 4", "value").
                            EndAND().
                            StartOR().
                                EQUAL("column 5", "value").
                                StartAND().
                                    EQUAL("column 6", "value").
                                    EQUAL("column 7", "value").
                                EndAND().
                            EndOR().
                        EndOR().
                    EndSQL();
    
    string Query = Db.GetQuery();
    cout << Query << endl;
    
    return 1;
}
