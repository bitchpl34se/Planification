#include "vacation_type.h"

using namespace std;

VacationType::VacationType(const unsigned int durationMin, const unsigned int durationMax, const unsigned int startMin, const unsigned int stopMax, const unsigned int breakDurationMin, const unsigned int breakDurationMax, const unsigned int startToBreakMin, const unsigned int breakToStopMin, const unsigned int limit, const unsigned int cost) :
    durationMin(durationMin),
    durationMax(durationMax),
    startMin(startMin),
    stopMax(stopMax),
    breakDurationMin(breakDurationMin),
    breakDurationMax(breakDurationMax),
    startToBreakMin(startToBreakMin),
    breakToStopMin(breakToStopMin),
    limit(limit),
    cost(cost),
    vacationList()
{
    if(!check())
    {
        throw invalid_argument("Invalid parameters [VacationType constructor]");
    }
    enumerate();
}

void VacationType::enumerate()
{
    for(unsigned int duration = durationMin; duration <= durationMax; ++duration)
    for(unsigned int start = startMin; start + duration - 1 <= stopMax; ++start)
    for(unsigned int breakDuration = breakDurationMin; breakDuration <= breakDurationMax; ++breakDuration)
    for(unsigned int breakStart = start + startToBreakMin; breakStart + breakDuration + breakToStopMin - 1 <= start + duration - 1; ++breakStart)
    {
        vacationList.push_back(Vacation(start, start + duration - 1, breakStart, breakStart + breakDuration - 1));
    }
}

bool VacationType::check() const
{
    return
           (durationMin <= durationMax)
        && (startMin <= stopMax)
        && (startMin + durationMax - 1 <= stopMax)
        && (breakDurationMin <= breakDurationMax)
        && (startToBreakMin + breakDurationMin + breakToStopMin <= durationMin)
        && (startToBreakMin + breakDurationMax + breakToStopMin <= durationMax);
}

const vector< Vacation > & VacationType::vacations() const
{
    return vacationList;
}

ostream & operator << (ostream & output, const VacationType & vacationType)
{
    output << "(";
    output << "durationMin = " << vacationType.durationMin << ", ";
    output << "durationMax = " << vacationType.durationMax << ", ";
    output << "startMin = " << (vacationType.startMin + 1) << ", ";
    output << "stopMax = " << (vacationType.stopMax + 1) << ", ";
    output << "breakDurationMin = " << vacationType.breakDurationMin << ", ";
    output << "breakDurationMax = " << vacationType.breakDurationMax << ", ";
    output << "startToBreakMin = " << vacationType.startToBreakMin << ", ";
    output << "breakToStopMin = " << vacationType.breakToStopMin << ", ";
    output << "limit = " << vacationType.limit << ", ";
    output << "cost = " << vacationType.cost << ")";
    return output;
}
