#include "data.h"

using namespace std;

const std::vector< unsigned int > & Data::load() const
{
    return loadList;
}

const std::vector< VacationType > & Data::vacationTypes() const
{
    return vacationTypeList;
}

bool Data::check() const
{
    bool result = true;
    for(auto vacationType : vacationTypeList)
    {
        result = result && (vacationType.stopMax <= loadList.size());
    }
    return result;
}

void Data::fitOutput() const
{
    Vacation::numberOfPeriods = loadList.size();
}

ostream & operator << (ostream & output, const Data & data)
{
    output << "# Periods";
    output << endl;
    for(unsigned int period = 0; period < data.load().size(); ++period)
    {
        output << period + 1;
             if(period + 1 < 10  ) output << "   ";
        else if(period + 1 < 100 ) output << "  " ;
        else if(period + 1 < 1000) output << " "  ;
    }
    output << "    #";
    output << endl << "# Load";
    output << endl;
    for(auto load : data.load())
    {
        output << load;
             if(load < 10  ) output << "   ";
        else if(load < 100 ) output << "  " ;
        else if(load < 1000) output << " "  ;
    }
    output << "    #";
    unsigned int typeIndex = 0;
    for(auto vacationType : data.vacationTypes())
    {
        output << endl << "# Vacation type " << (++typeIndex) << " " << vacationType;
        for(auto vacation : vacationType.vacations())
        {
            output << endl << vacation << "#";
        }
    }
    return output;
}
