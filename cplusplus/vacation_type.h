#ifndef VACATION_TYPE_H_INCLUDED
#define VACATION_TYPE_H_INCLUDED

#include "vacation.h"

#include <vector>
#include <stdexcept>

class VacationType
{
public:
    const unsigned int durationMin;
    const unsigned int durationMax;
    const unsigned int startMin;
    const unsigned int stopMax;
    const unsigned int breakDurationMin;
    const unsigned int breakDurationMax;
    const unsigned int startToBreakMin;
    const unsigned int breakToStopMin;
    const unsigned int limit;
    const unsigned int cost;
private:
    std::vector< Vacation > vacationList;
private:
    explicit VacationType();
public:
    explicit VacationType(const unsigned int durationMin, const unsigned int durationMax, const unsigned int startMin, const unsigned int stopMax, const unsigned int breakDurationMin, const unsigned int breakDurationMax, const unsigned int startToBreakMin, const unsigned int breakToStopMin, const unsigned int limit, const unsigned int cost);
public:
    bool check() const;
    const std::vector< Vacation > & vacations() const;
private:
    void enumerate();
};

#include <ostream>

std::ostream & operator << (std::ostream & output, const VacationType & vacationType);

#endif // VACATION_TYPE_H_INCLUDED
