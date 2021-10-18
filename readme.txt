/* *****************************************************************************
 *  Name:     Devin Plumb
 *  NetID:    dplumb
 *  Precept:  P06
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Operating system:   Mac OS X
 *  Compiler:           javac
 *  Text editor / IDE:  IntelliJ
 *
 *  Have you taken (part of) this course before: No.
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II: No.
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 1: Percolation



/* *****************************************************************************
 *  Describe how you implemented Percolation.java. How did you check
 *  whether the system percolates?
 **************************************************************************** */

    I used two WeightedQuickUnionUFs, one to check for fullness and the other
    for percolation. For the first WQU I created a site outside of the grid
    which automatically merged with any open sites in the top row, and in order
    to check if a site was full I checked if it was connected to this site. For
    the second WQU I created two sites outside the grid, one which automatically
    merged with any open sites in the top row and one which automatically merged
    with any open sites in the bottom row, and in order to check if the grid
    percolates I simply checked if the two off-grid sites were connected.

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time of
 *  PercolationStats.java for various values of n and T when implementing
 *  Percolation.java with QuickFindUF.java (not QuickUnionUF.java). Use a
 *  "doubling" hypothesis, where you successively increase either n or T by
 *  a constant multiplicative factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one. Do not include
 *  data points that take less than 0.25 seconds.
 **************************************************************************** */

(keep T constant)
 T = 100
multiplicative factor (for n) = 1.5

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
64              0.657            -----           ------
96              3.098            4.715           3.8248
144            15.496            5.002           3.9703
216            79.142            5.107           4.0217
324           402.958            5.092           4.0141

Averaged log ratio for significant values (time >= 1): 4.0020
Let's assume this is 4


(keep n constant)
 n = 100
 multiplicative factor (for T) = 2

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
2               0.116            -----           ------
4               0.205            1.767           0.8213
8               0.334            1.629           0.7040
16              0.659            1.973           0.9804
32              1.250            1.897           0.9237
64              2.515            2.012           1.0086
128             5.009            1.992           0.9942
256             9.870            1.970           0.97820

Averaged log ratio for significant values (time >= 0.250): 0.97702
Let's assume this is 1


/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) of
 *  PercolationStats.java as function of both n and T, such as
 *
 *       ~ 5.3*10^-8 * n^5.1 * T^1.5
 *
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */

QuickFindUF running time (in seconds) as a function of n and T:

     ~ 4*10^-10 * n^4 * T^1 seconds
       _______________________________________

/* *****************************************************************************
 *  Repeat the previous two questions, but using WeightedQuickUnionUF
 *  (instead of QuickFindUF).
 **************************************************************************** */

(keep T constant)
 T = 100

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
2               0.033            -----           ------
4               0.036            1.091           0.1257
8               0.037            1.028           0.0398
16              0.049            1.324           0.4049
32              0.076            1.551           0.6332
64              0.116            1.526           0.6098
128             0.218            1.879           0.9100
256             0.568            2.606           1.3818
512             2.892            5.092           2.3482
1024           20.309            7.022           2.8119
2048          115.112            5.668           2.5028
4096          530.551            4.609           2.2045

Averaged log ratio for significant values (time >= 0.250): 2.46685
Let's assume this is 2.5

(keep n constant)
 n = 100

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
2               0.046            -----           ------
4               0.053            1.152           0.2041
8               0.062            1.170           0.2265
16              0.081            1.306           0.3852
32              0.101            1.247           0.3185
64              0.152            1.505           0.5898
128             0.239            1.572           0.6526
256             0.342            1.431           0.5170
512             0.503            1.471           0.5568
1024            0.771            1.532           0.6154
2048            1.465            1.900           0.9260
4096            2.718            1.855           0.8916
8192            5.028            1.850           0.8874
16384          10.119            2.013           1.0090
32768          19.540            1.931           0.9494
65536          40.890            2.093           1.0653

Averaged log ratio for significant values (time >= 0.750): 0.95478
Let's assume this is 1

WeightedQuickUnionUF running time (in seconds) as a function of n and T:

     ~ 6*10^-9 * n^2.5 * T^1
       _______________________________________


/* *********************************************************************
 *  How much memory (in bytes) does a Percolation object (which uses
 *  WeightedQuickUnionUF.java) use to store an N-by-N grid? Use the
 *  64-bit memory cost model from Section 1.4 of the textbook and use
 *  tilde notation to simplify your answer. Briefly justify your answers.
 *
 *  Include the memory for all referenced objects (deep memory).
 **********************************************************************/

Object overhead: 16 bytes

isOpen: 24 (array of arrays overhead) + 24n (all arays overhead) +
        8n (arrays reference) + n^2 (boolean values) = n^2 + 32n + 24 + padding

// This is used for calculating how much memory is used by union and union2
WeightedQuickUnionUF(n): 16 (object overhead) + 4n + 24 (int array) + 4n + 24
                            (int array) + 4 (int) + 4 (padding) = 8n + 72

union: 8(n^2 + 2) + 72 = 8n^2 + 88
union2: 8(n^2 + 1) + 72 = 8n^2 + 80

count: 4

size: 4

    Total: 17n^2 + 32n + 216, plus padding
    Roughly ~17n^2

The original program not accounting for backwashing would have used:
9n^2 + 32n + 136, plus padding, or ~9n^2

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

    Fixing backwashing forced me to use a second WQU, which would increase the
    runtime of certain methods, but by a constant factor.

/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

    None, other than online course materials.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

    Figuring out backwashing. Still did not discover a clever solution. Simply
    created a second WeightedQuickUnion.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
