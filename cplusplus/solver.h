#ifndef SOLVER_H_INCLUDED
#define SOLVER_H_INCLUDED

#include "data.h"
#include "solution.h"

class Solver
{
public:
    enum class Model { exact, relaxed };
public:
    const Model model;
private:
    const Data * dataAddress;
    bool unfeasible;
    std::vector< std::vector< double > > numberOfVacationsList;
private:
    explicit Solver();
public:
    explicit Solver(const Data & data, const Model & model);
public:
    const Data & data() const;
    double numberOfVacations(const unsigned int typeIndex, const unsigned int vacationIndex) const;
    Solution generateSolution() const;
public:
    bool isNotFeasible() const;
};

#endif // SOLVER_H_INCLUDED
