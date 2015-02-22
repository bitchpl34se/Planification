#include "reader.h"
#include "csv_file.h"

#include <vector>
#include <fstream>
#include <sstream>

using namespace std;

Data * Input::read(const char * filename)
{
    Data * data = NULL;
    try
    {
        CsvFile file;
        ifstream stream(filename);
        file << stream;
        stream.close();
        unsigned int numberOfPeriods, numberOfVacationType, integers[10];
        vector< unsigned int > load;
        vector< VacationType > vacationTypes;

        /* Step 1. Read the number of periods */
        istringstream(file.get(0, 1)) >> numberOfPeriods;

        /* Step 2. Read the charge for each period */
        for(unsigned int period = 0; period < numberOfPeriods; ++period)
        {
            istringstream(file.get(2, period)) >> integers[0];
            load.push_back(integers[0]);
        }

        /* Step 3. Read the number of vacation types */
        istringstream(file.get(4, 1)) >> numberOfVacationType;

        /* Step 4. Read data for each vacation type */
        for(unsigned int typeIndex = 0; typeIndex < numberOfVacationType; ++typeIndex)
        {
            istringstream(file.get(6 + typeIndex,  1)) >> integers[0];
            istringstream(file.get(6 + typeIndex,  2)) >> integers[1];
            istringstream(file.get(6 + typeIndex,  3)) >> integers[2]; --(integers[2]);
            istringstream(file.get(6 + typeIndex,  4)) >> integers[3]; --(integers[3]);
            istringstream(file.get(6 + typeIndex,  5)) >> integers[4];
            istringstream(file.get(6 + typeIndex,  6)) >> integers[5];
            istringstream(file.get(6 + typeIndex,  7)) >> integers[6];
            istringstream(file.get(6 + typeIndex,  8)) >> integers[7];
            istringstream(file.get(6 + typeIndex,  9)) >> integers[8];
            istringstream(file.get(6 + typeIndex, 10)) >> integers[9];
            vacationTypes.push_back(
                VacationType(
                    integers[0],
                    integers[1],
                    integers[2],
                    integers[3],
                    integers[4],
                    integers[5],
                    integers[6],
                    integers[7],
                    integers[8],
                    integers[9]
                )
            );
        }

        data = new Data(load.begin(), load.end(), vacationTypes.begin(), vacationTypes.end());
    }
    catch(...)
    {
        if(data != NULL)
        {
            delete data;
            data = NULL;
        }
    }
    return data;
}
