package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF  grid ;
    private WeightedQuickUnionUF secondaryGrid;
    private int openSites;
    private int [][] percolationGrid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N)
    {
        if(N <=0){
            throw new IllegalArgumentException();
        }
        this.grid = new WeightedQuickUnionUF(N*N+2);
        //Using a second WeightedQuickUnionUF ds to counter backwash by only using one virtual site...
        this.secondaryGrid = new WeightedQuickUnionUF(N*N+1);
        this.openSites = 0;
        this.percolationGrid = new int[N][N];

        for (int i = 0 ; i < N ; i++){
            this.grid.union(N*N,i);
            this.secondaryGrid.union(N*N,i);
            this.grid.union(N*N+1,(N*N-1)-i);
            }
        }


    private void checkUnion(int row,int col,int newRow,int newColumn){

        if(this.checkIndex(newRow, newColumn))
            return;

        int currentBlock  = this.percolationGrid[0].length*row + col;

        if(this.isOpen(newRow,newColumn))
        {
            int neighbourBlock  = this.percolationGrid[0].length*newRow + newColumn;
            this.grid.union(currentBlock,neighbourBlock);
            this.secondaryGrid.union(currentBlock,neighbourBlock);
        }

    }

    private boolean checkIndex(int row,int col){

        return    row < 0  || row > this.percolationGrid[0].length - 1
               || col < 0  || col > this.percolationGrid[0].length - 1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col)   {

        if(this.isOpen(row,col)){
            return;
        }

        if(this.checkIndex(row, col)){
            throw new IndexOutOfBoundsException();
        }

        this.percolationGrid[row][col] = 1;
        this.openSites++;

        this.checkUnion(row,col,row+1,col);
        this.checkUnion(row,col,row-1,col);
        this.checkUnion(row,col,row,col+1);
        this.checkUnion(row,col,row,col-1);

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if(this.checkIndex(row, col)){
            throw new IndexOutOfBoundsException();
        }
         return this.percolationGrid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if(this.checkIndex(row, col)){
            throw new IndexOutOfBoundsException();
        }

        if(!this.isOpen(row,col)){
            return false;
        }

        int N = this.percolationGrid[0].length;
        return this.secondaryGrid.connected(N * N, row * N + col);
//
//        for (int i = 0 ; i < N ; i++){
//            if(this.grid.connected(i,row*N+col)){
//                return true;
//            }
//        }

    }
    // number of open sites
    public int numberOfOpenSites(){
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        
        if(this.percolationGrid[0].length == 1){
            return this.isOpen(0,0);
        }

        int N = this.percolationGrid[0].length;
        return this.grid.connected(N * N, N*N+1);
//
//        for (int i = 0 ; i < N ; i++){
//            if(this.isFull(N-1,i)){
//                return true;
//            }
//        }

    }

    public static void main(String[] args){

    }

}
