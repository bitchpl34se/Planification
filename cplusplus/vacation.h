#ifndef VACATION_H_INCLUDED
#define VACATION_H_INCLUDED

#include <string>
#include <stdexcept>

class Vacation
{
public:
    const unsigned int start;
    const unsigned int stop;
    const unsigned int breakStart;
    const unsigned int breakStop;
public:
    static unsigned int numberOfPeriods;
private:
    explicit Vacation();
public:
    explicit Vacation(const unsigned int start, const unsigned int stop, const unsigned int breakStart, const unsigned int breakStop);
public:
    bool isWorkedAt(const unsigned int period) const;
    bool check() const;
};

#include <ostream>

std::ostream & operator << (std::ostream & output, const Vacation & vacation);

#endif // VACATION_H_INCLUDED
