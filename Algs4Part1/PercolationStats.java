import java.lang.IllegalArgumentException;

public class PercolationStats {

  private int N = 0; // grid size NxN
  private int T = 0; // number of experiments
  
  private double stdDev = 0.0d;
  private double mean = 0.0d;
  private double confidenceLower = 0.0f;
  private double confidenceUpper =  0.0f;
  
  public PercolationStats(int N, int T){
    if(N <= 0 || T <= 0)
    {
      throw new IllegalArgumentException("");
    }
    this.N = N;
    this.T = T;
    
    this.performExperiment();
  }
  
  public double mean(){
    return this.mean;
  }
  
  public double stddev(){
    return this.stdDev;
  }
  
  public double confidenceLo(){
    return this.confidenceLower;
  }
  
  public double confidenceHi()
  {
    return this.confidenceUpper;
  }
  
  private void performExperiment()
  {
    double [] fractionOpen = new double[this.T];
    int row = 0;
    int column = 0;
    Percolation percolation = null;
    int i = 0;
    int counter;
    for(i = 0; i < this.T; i++)
    {
      counter = 0;
      percolation = new Percolation(this.N);
      while(!percolation.percolates())
      {
        row = StdRandom.uniform(this.N);
        column = StdRandom.uniform(this.N);
        if(!percolation.isOpen(row,column))
        {
          percolation.open(row,column);
          counter++;
        }
      }
      fractionOpen[i] = (1-(double)counter/(double)(this.N*this.N));
      this.mean += fractionOpen[i];
      System.out.println(i + " " + (this.mean/(i+1)));
    }
    
    this.mean/=this.T;
    for(i = 0; i < this.T; i++)
    {
      this.stdDev += (fractionOpen[i] - this.mean)*(fractionOpen[i] - this.mean);
    }
    this.stdDev/=(this.T-1);
    this.confidenceLower = this.mean - 1.96*Math.sqrt(this.stdDev)/Math.sqrt(this.T);
    this.confidenceUpper = this.mean + 1.96*Math.sqrt(this.stdDev)/Math.sqrt(this.T);
  }
  
  public static void main(String[] args)
  {
    if(args.length != 2)
      throw new RuntimeException("Incorrect number of input arguments!");
    PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
    StdOut.println("mean\t=\t" + p.mean());
    StdOut.println("stddev\t=\t" + p.stddev());
    StdOut.println("95 % confidence interval\t=\t" + p.confidenceLo() + ", " + p.confidenceHi());
  }
}