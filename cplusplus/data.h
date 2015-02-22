#ifndef DATA_H_INCLUDED
#define DATA_H_INCLUDED

#include "vacation_type.h"

#include <vector>
#include <stdexcept>

class Data
{
private:
    std::vector< unsigned int > loadList;
    std::vector< VacationType > vacationTypeList;
private:
    explicit Data();
public:
    template < class InputIteratorLoad, class InputIteratorVacationType >
    explicit Data(InputIteratorLoad loadFirst, InputIteratorLoad loadLast, InputIteratorVacationType vacationTypesFirst, InputIteratorVacationType vacationTypesLast);
public:
    const std::vector< unsigned int > & load() const;
    const std::vector< VacationType > & vacationTypes() const;
public:
    bool check() const;
    void fitOutput() const;
};

#include <ostream>

std::ostream & operator << (std::ostream & output, const Data & data);

/* Template code must be located in a header file (otherwise, no instantiation is possible) */

template < class InputIteratorLoad, class InputIteratorVacationType >
Data::Data(InputIteratorLoad loadFirst, InputIteratorLoad loadLast, InputIteratorVacationType vacationTypesFirst, InputIteratorVacationType vacationTypesLast) :
    loadList(loadFirst, loadLast),
    vacationTypeList(vacationTypesFirst, vacationTypesLast)
{
    if(!check())
    {
        throw std::invalid_argument("Invalid parameters [Data constructor]");
    }
}

#endif // DATA_H_INCLUDED
