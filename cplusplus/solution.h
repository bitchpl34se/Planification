#ifndef SOLUTION_H_INCLUDED
#define SOLUTION_H_INCLUDED

#include "data.h"

#include <vector>
#include <stdexcept>

class Solution
{
private:
    const Data * dataAddress;
    std::vector< std::vector< unsigned int > > numberOfVacationsList;
private:
    explicit Solution();
public:
    explicit Solution(const Data & data);
public:
    const Data & data() const;
    unsigned int numberOfVacations(const unsigned int typeIndex, const unsigned int vacationIndex) const;
    unsigned int & numberOfVacations(const unsigned int typeIndex, const unsigned int vacationIndex);
public:
    bool check() const;
    bool respectsLoad() const;
    bool respectsLimits() const;
    unsigned int computeLoad(const unsigned int period) const;
    unsigned int countVacationType(const unsigned int typeIndex) const;
    unsigned int computeCost() const;
};

#include <ostream>

std::ostream & operator << (std::ostream & output, const Solution & solution);

#endif // SOLUTION_H_INCLUDED
