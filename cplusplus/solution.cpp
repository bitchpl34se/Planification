#include "solution.h"

using namespace std;

Solution::Solution(const Data & data) :
    dataAddress(&data),
    numberOfVacationsList()
{
    for(unsigned int typeIndex = 0; typeIndex < data.vacationTypes().size(); ++typeIndex)
    {
        numberOfVacationsList.push_back(vector< unsigned int >(data.vacationTypes()[typeIndex].vacations().size()));
    }
}

const Data & Solution::data() const
{
    return *dataAddress;
}

unsigned int Solution::numberOfVacations(const unsigned int typeIndex, const unsigned int vacationIndex) const
{
    return numberOfVacationsList[typeIndex][vacationIndex];
}

unsigned int & Solution::numberOfVacations(const unsigned int typeIndex, const unsigned int vacationIndex)
{
    return numberOfVacationsList[typeIndex][vacationIndex];
}

bool Solution::check() const
{
    return respectsLoad() && respectsLimits();
}

bool Solution::respectsLoad() const
{
    bool result = true;
    for(unsigned int period = 0; result && (period < dataAddress->load().size()); ++period)
    {
        result = (computeLoad(period) >= dataAddress->load()[period]);
    }
    return result;
}

bool Solution::respectsLimits() const
{
    bool result = true;
    for(unsigned int typeIndex = 0; result && (typeIndex < dataAddress->vacationTypes().size()); ++typeIndex)
    {
        result = (countVacationType(typeIndex) <= dataAddress->vacationTypes()[typeIndex].limit);
    }
    return result;
}

unsigned int Solution::computeLoad(const unsigned int period) const
{
    unsigned int load = 0;
    for(unsigned int typeIndex = 0; typeIndex < numberOfVacationsList.size(); ++typeIndex)
    for(unsigned int vacationIndex = 0; vacationIndex < numberOfVacationsList[typeIndex].size(); ++vacationIndex)
    {
        if(dataAddress->vacationTypes()[typeIndex].vacations()[vacationIndex].isWorkedAt(period))
            load += numberOfVacationsList[typeIndex][vacationIndex];
    }
    return load;
}

unsigned int Solution::countVacationType(const unsigned int typeIndex) const
{
    unsigned int counter = 0;
    for(auto numberOfVacations : numberOfVacationsList[typeIndex])
    {
        counter += numberOfVacations;
    }
    return counter;
}

unsigned int Solution::computeCost() const
{
    unsigned int cost = 0;
    for(unsigned int typeIndex = 0; typeIndex < numberOfVacationsList.size(); ++typeIndex)
    for(unsigned int vacationIndex = 0; vacationIndex < numberOfVacationsList[typeIndex].size(); ++vacationIndex)
    {
        cost += numberOfVacationsList[typeIndex][vacationIndex] * dataAddress->vacationTypes()[typeIndex].cost;
    }
    return cost;
}

ostream & operator << (ostream & output, const Solution & solution)
{
    for(unsigned int typeIndex = 0; typeIndex < solution.data().vacationTypes().size(); ++typeIndex)
    {
        output << "# Vacation type " << (typeIndex + 1);
        if(solution.countVacationType(typeIndex) == 0)
        {
            output << " (no vacation of this type)";
        }
        else
        {
            for(unsigned int vacationIndex = 0; vacationIndex < solution.data().vacationTypes()[typeIndex].vacations().size(); ++vacationIndex)
            {
                if(solution.numberOfVacations(typeIndex, vacationIndex) > 0)
                {
                    output << endl;
                    output << solution.data().vacationTypes()[typeIndex].vacations()[vacationIndex];
                    output << "# quantity = " << solution.numberOfVacations(typeIndex, vacationIndex);
                }
            }
        }
        output << endl;
    }
    output << "# Cost";
    output << endl;
    output << solution.computeCost();
    return output;
}
