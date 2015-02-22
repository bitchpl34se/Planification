#include "solver.h"

#include <glpk.h>

#include <vector>

#define SOLVER_UNUSED_BOUND 0

using namespace std;

Solver::Solver(const Data & data, const Solver::Model & model) :
    model(model),
    dataAddress(&data),
    unfeasible(true),
    numberOfVacationsList()
{
    const unsigned int numberOfPeriods = dataAddress->load().size(),
                       numberOfVacationType = dataAddress->vacationTypes().size();

    for(unsigned int typeIndex = 0; typeIndex < numberOfVacationType; ++typeIndex)
    {
        numberOfVacationsList.push_back(vector< double >(dataAddress->vacationTypes()[typeIndex].vacations().size()));
    }

    int row, col;
    int * colsBase = new int [numberOfVacationType + 1];
    int len = 0;
    int * ind = NULL, * ind_iterator;
    double * val = NULL, * val_iterator;

    glp_init_env();
    glp_prob * problem = glp_create_prob();

    /* Step 1. Add columns = variables */
    for(unsigned int typeIndex = 0; typeIndex < numberOfVacationType; ++typeIndex)
    {
        colsBase[typeIndex] = glp_add_cols(problem, (int) dataAddress->vacationTypes()[typeIndex].vacations().size());
    }
    colsBase[numberOfVacationType] = colsBase[0] + glp_get_num_cols(problem);
    for(col = colsBase[0]; col < colsBase[numberOfVacationType]; ++col)
    {
        glp_set_col_bnds(problem, col, GLP_LO, 0, SOLVER_UNUSED_BOUND); //< All variables are *REAL* positive numbers.
        if(model == Solver::Model::exact)
        {
            glp_set_col_kind(problem, col, GLP_IV); //< All variables are *INTEGER* positive numbers.
        }
    }

    /* Step 2. Add rows = constraints */
    /* Constraint type 1. For each period, satisfy the charge */
    row = glp_add_rows(problem, numberOfPeriods);
    /* Same number of not-null coefficients for all constraints */
    len = glp_get_num_cols(problem);
    ind = new int [len + 1];
    val = new double [len + 1];
    /* Indexes of columns : independent on 'period' */
    ind_iterator = &(ind[1]);
    for(col = colsBase[0]; col < colsBase[numberOfVacationType]; ++col)
    {
        *(ind_iterator++) = col;
    }
    /* Values of columns : dependent on 'period' */
    for(unsigned int period = 0; period < numberOfPeriods; ++period)
    {
        val_iterator = &(val[1]);
        for(auto vacationType : dataAddress->vacationTypes())
        for(auto vacation : vacationType.vacations())
        {
            *(val_iterator++) = (vacation.isWorkedAt(period) ? 1 : 0);
        }
        glp_set_mat_row(problem, row, len, ind, val);
        glp_set_row_bnds(problem, row, GLP_LO, dataAddress->load()[period], SOLVER_UNUSED_BOUND);
        ++row;
    }
    delete [] ind;
    delete [] val;
    /* Constraint type 2. For each vacation type, do not exceed the limit on the number of vacations */
    row = glp_add_rows(problem, numberOfVacationType);
    for(unsigned int typeIndex = 0; typeIndex < numberOfVacationType; ++typeIndex)
    {
        /* Different number of not-null coefficients, depending on 'typeIndex' */
        len = colsBase[typeIndex + 1] - colsBase[typeIndex];
        ind = new int [len + 1];
        val = new double [len + 1];
        /* Indexes and values of columns : dependent on 'typeIndex' */
        ind_iterator = &(ind[1]);
        val_iterator = &(val[1]);
        for(col = colsBase[typeIndex]; col < colsBase[typeIndex + 1]; ++col)
        {
            *(ind_iterator++) = col;
            *(val_iterator++) = 1;
        }
        glp_set_mat_row(problem, row, len, ind, val);
        glp_set_row_bnds(problem, row, GLP_UP, SOLVER_UNUSED_BOUND, dataAddress->vacationTypes()[typeIndex].limit);
        ++row;
        delete [] ind;
        delete [] val;
    }

    /* Step 3. Define objective */
    glp_set_obj_dir(problem, GLP_MIN);
    for(unsigned int typeIndex = 0; typeIndex < numberOfVacationType; ++typeIndex)
    for(int col = colsBase[typeIndex]; col < colsBase[typeIndex + 1]; ++col)
    {
        glp_set_obj_coef(problem, col, dataAddress->vacationTypes()[typeIndex].cost);
    }

    /* Step 4. Launch resolution */
    if(model == Solver::Model::relaxed)
    {
        glp_smcp options;
        glp_init_smcp(&options);
        options.msg_lev = GLP_MSG_ERR;
        options.presolve = GLP_ON;
        unfeasible = !((glp_simplex(problem, &options) == 0) && (glp_get_status(problem) == GLP_OPT));
    }
    else // model == Solver::Model::exact
    {
        glp_iocp options;
        glp_init_iocp(&options);
        options.msg_lev = GLP_MSG_ERR;
        options.presolve = GLP_ON;
        unfeasible = !((glp_intopt(problem, &options) == 0) && (glp_mip_status(problem) == GLP_OPT));
    }

    /* Step 5. Retrieve values of variables */
    if(!unfeasible)
    {
        col = colsBase[0];
        for(unsigned int typeIndex = 0; typeIndex < numberOfVacationType; ++typeIndex)
        for(unsigned int vacationIndex = 0; vacationIndex < dataAddress->vacationTypes()[typeIndex].vacations().size(); ++vacationIndex)
        {
            if(model == Solver::Model::relaxed)
            {
                numberOfVacationsList[typeIndex][vacationIndex] = glp_get_col_prim(problem, col++);
            }
            else // model == Solver::Model::exact
            {
                numberOfVacationsList[typeIndex][vacationIndex] = glp_mip_col_val(problem, col++);
            }
        }
    }

    glp_delete_prob(problem);
    glp_free_env();

    delete [] colsBase;
}

const Data & Solver::data() const
{
    return *dataAddress;
}

double Solver::numberOfVacations(const unsigned int typeIndex, const unsigned int vacationIndex) const
{
    return numberOfVacationsList[typeIndex][vacationIndex];
}

Solution Solver::generateSolution() const
{
    Solution result(*dataAddress);
    if(model == Solver::Model::exact)
    {
        for(unsigned int typeIndex = 0; typeIndex < dataAddress->vacationTypes().size(); ++typeIndex)
        for(unsigned int vacationIndex = 0; vacationIndex < dataAddress->vacationTypes()[typeIndex].vacations().size(); ++vacationIndex)
        {
            result.numberOfVacations(typeIndex, vacationIndex) = (unsigned int) numberOfVacations(typeIndex, vacationIndex);
        }
    }
    else // model == Solver::Model::relaxed
    {
        /* Generate approximated solution ... */
    }
    return result;
}

bool Solver::isNotFeasible() const
{
    return unfeasible;
}
