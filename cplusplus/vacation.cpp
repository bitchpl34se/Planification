#include "vacation.h"

using namespace std;

unsigned int Vacation::numberOfPeriods = 0;

Vacation::Vacation(const unsigned int start, const unsigned int stop, const unsigned int breakStart, const unsigned int breakStop) :
    start(start),
    stop(stop),
    breakStart(breakStart),
    breakStop(breakStop)
{
    if(!check())
    {
        throw invalid_argument("Invalid periods [Vacation constructor]");
    }
}

bool Vacation::isWorkedAt(const unsigned int period) const
{
    return ((start <= period) && (period < breakStart)) || ((breakStop < period) && (period <= stop));
}

bool Vacation::check() const
{
    return
           (start <= breakStart)
        && (breakStart <= breakStop)
        && (breakStop <= stop);
}

ostream & operator << (ostream & output, const Vacation & vacation)
{
    unsigned int period = 0;
    for(/* period = 0 */; period < vacation.start; ++period)
        output << "    ";
    for(/* period = vacation.start */; period < vacation.breakStart; ++period)
        output << "|---";
    for(/* period = vacation.breakStart */; period <= vacation.breakStop; ++period)
        output << "||||";
    for(/* period = vacation.breakStop + 1 */; period <= vacation.stop; ++period)
        output << "|---";
    // period == vacation.stop + 1
        output << "|   ";
    for(++period /* period = vacation.stop + 2 */; period <= Vacation::numberOfPeriods; ++period)
        output << "    ";
    return output;
}
