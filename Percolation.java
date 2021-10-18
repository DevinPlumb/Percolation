/* *****************************************************************************
 *  Name:    Devin Plumb
 *  NetID:   dplumb
 *  Precept: P06
 *
 *  Description:  Data type to model an n-by-n percolation problem.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {

    private boolean[][] isOpen; // is the site open
    private final QuickFindUF union; // used to check percolation
    private final QuickFindUF union2; // used to check fullness
    private int count; // number of open sites
    private final int size; // size of grid

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n is too small");
        isOpen = new boolean[n][n];
        union = new QuickFindUF(n * n + 2);
        union2 = new QuickFindUF(n * n + 1);
        count = 0;
        size = n;
    }

    // converts a coordinate in the boolean double array to a union index
    private int xyTo1d(int row, int col) {
        return (size * row) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
            throw new IllegalArgumentException("site not in the grid");

        if (!isOpen[row][col]) count++;
        isOpen[row][col] = true;
        int c = xyTo1d(row, col);

        if (row == 0) {
            union.union(size * size, c);
            union2.union(size * size, c);
        }
        if (row == size - 1) union.union(size * size + 1, c);

        if (row > 0 && isOpen[row - 1][col]) {
            union.union(c - size, c);
            union2.union(c - size, c);
        }
        if (row < size - 1 && isOpen[row + 1][col]) {
            union.union(c + size, c);
            union2.union(c + size, c);
        }
        if (col > 0 && isOpen[row][col - 1]) {
            union.union(c - 1, c);
            union2.union(c - 1, c);
        }
        if (col < size - 1 && isOpen[row][col + 1]) {
            union.union(c + 1, c);
            union2.union(c + 1, c);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
            throw new IllegalArgumentException("site not in the grid");
        return isOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
            throw new IllegalArgumentException("site not in the grid");
        return union2.connected(size * size, xyTo1d(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.connected(size * size, size * size + 1);
    }

    // unit testing
    public static void main(String[] args) {
        Percolation percolation = new Percolation(10);
        System.out.println(percolation.isOpen(0, 0));
        percolation.open(0, 0);
        percolation.open(2, 0);
        System.out.println(percolation.isOpen(0, 0));
        System.out.println(percolation.isOpen(2, 0));
        System.out.println(percolation.isFull(0, 0));
        System.out.println(percolation.isFull(2, 0));
        System.out.println(percolation.numberOfOpenSites());
        System.out.println(percolation.percolates());
    }

}
