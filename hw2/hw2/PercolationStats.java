package hw2;
import edu.princeton.cs.algs4.RandomSeq;
import edu.princeton.cs.introcs.*;

import java.util.ArrayList;

public class PercolationStats {

    private double mean = 0;
    private double mu = 0;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf){

        ArrayList<Double> list = new ArrayList<>();

        if(N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        double openSitesSum = 0;
        for(int i = 1 ; i <= T;i++ ){


            Percolation sample = pf.make(N);
            int openSites = 0;

            int randomX = StdRandom.uniform(N);
            int randomY = StdRandom.uniform(N);

            while(!sample.percolates()){

                while(sample.isOpen(randomX,randomY)){
                    randomX = StdRandom.uniform(N);
                    randomY = StdRandom.uniform(N);
                }
                sample.open(randomX,randomY);
                openSites++;
            }
            double openSiteFrac = (openSites*1.0)/(N*N);
            openSitesSum = openSitesSum + openSiteFrac;
            list.add(openSiteFrac);

        }

         this.mean = openSitesSum/T;
         this.T = T;

        for (int i = 0; i < T; i++) {

            this.mu = this.mu + Math.pow((list.get(i) - this.mean),2);

        }

        this.mu = this.mu/(T-1);


    }
    // sample mean of percolation threshold
    public double mean(){
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return Math.sqrt(this.mu);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return this.mean - (1.96*Math.sqrt(this.mu))/Math.sqrt(T);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return this.mean + (1.96*Math.sqrt(this.mu))/Math.sqrt(T);
    }

//     public static void main(String[] args) {
//         PercolationStats ps = new PercolationStats(200,100,new PercolationFactory());
//         System.out.println(ps.mean());
//         System.out.println(ps.stddev());
//         System.out.println(ps.confidenceLow());
//         System.out.println(ps.confidenceHigh());
//     }


}
