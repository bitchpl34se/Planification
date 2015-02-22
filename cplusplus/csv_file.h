#ifndef CSV_FILE_H_INCLUDED
#define CSV_FILE_H_INCLUDED

#include <string>
#include <vector>
#include <stdexcept>
#include <istream>
#include <ostream>

class CsvFile :
    private std::vector< std::vector< std::string > >
{
private:
    char delimiterValue;
public:
    explicit CsvFile(); // default delimiter value == ';'
    explicit CsvFile(const char delimiter);
public:
    char delimiter() const;
    void delimiter(const char delimiter);
public:
    CsvFile & operator << (std::istream & input);
public:
    unsigned int rowCount() const;
    unsigned int columnCount(const unsigned int row = 0) const;
    const std::string & get(const unsigned int row, const unsigned int column) const;
public:
    friend
    std::ostream & operator << (std::ostream & output, const CsvFile & csvFile);
};

#endif // CSV_FILE_H_INCLUDED
