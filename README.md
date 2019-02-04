# Sudoku-Solver
My own implementation of a sudoku solver

Algorithm:


Optimizations:
1. simplified update logic to insert the proposed number directly into the associated boardStructs and manually update boardStruct metadata rather 
than clearing all metadata and reconstructing the boardStruct from scratch

2. for while loop condition, rather than call validateSolution(), check if bucket[8] has 27 boardstructs, implying a fully solved board
