#include "csv_file.h"

#include <list>
#include <sstream>

using namespace std;

CsvFile::CsvFile() :
    vector< vector< string > >(),
    delimiterValue(';')
{
    //
}

CsvFile::CsvFile(const char delimiter) :
    vector< vector< string > >(),
    delimiterValue(delimiter)
{
    //
}

char CsvFile::delimiter() const
{
    return delimiterValue;
}

void CsvFile::delimiter(const char delimiter)
{
    delimiterValue = delimiter;
}

CsvFile & CsvFile::operator << (istream & input)
{
    if(!input)
    {
        throw runtime_error("Problem with input stream [CsvFile::operator<<]");
    }
    clear();
    try
    {
        string buffer;
        list< string > lines[2];
        /* Parse using '\n' to end lines */
        while(getline(input, buffer, '\n'))
        {
            lines[0].push_back(buffer);
        }
        /* Parse using '\r' to end lines */
        for(list< string >::const_iterator line = lines[0].begin(); line != lines[0].end(); ++line)
        {
            istringstream lineInput(*line);
            while(getline(lineInput, buffer, '\r'))
            {
                lines[1].push_back(buffer);
            }
        }
        /* 'lines' now contains all lines in file 'filename', no matter what symbol [Windows, Mac, Unix] is used to end lines */
        resize(lines[1].size());
        vector< vector< string > >::iterator row = begin();
        for(list< string >::const_iterator line = lines[1].begin(); line != lines[1].end(); ++line)
        {
            istringstream lineInput(*line);
            while(getline(lineInput, buffer, delimiterValue))
            {
                row->push_back(buffer);
            }
            ++row;
        }
    }
    catch(...)
    {
        throw runtime_error("Error while parsing input stream [CsvFile::operator<<]");
    }
    return *this;
}

unsigned int CsvFile::rowCount() const
{
    return size();
}

unsigned int CsvFile::columnCount(const unsigned int row) const
{
    return (*this)[row].size();
}

const string & CsvFile::get(const unsigned int row, const unsigned int column) const
{
    return (*this)[row][column];
}

ostream & operator << (ostream & output, const CsvFile & csvFile)
{
    for(vector< vector< string > >::const_iterator row = csvFile.begin(); row != csvFile.end(); /* ++row */)
    {
        for(vector< string >::const_iterator field = row->begin(); field != row->end(); ++field)
        {
            output << *field << csvFile.delimiterValue;
        }
        if(++row != csvFile.end())
        {
            output << endl;
        }
    }
    return output;
}
