import java.lang.IndexOutOfBoundsException;

public class Percolation {
  
  private int N = 0;
  private boolean [][] grid = null;
  private WeightedQuickUnionUF connections = null;
  
  public Percolation(int N){
    this.N = N;
    this.grid = new boolean[this.N][this.N];
    for(int i = 0; i < this.N; i++)
    {
     for(int j = 0; j < this.N; j++)
     {
       this.grid[i][j] = false;
     } 
    }
    this.connections = new WeightedQuickUnionUF(this.N*this.N);
  }
  
  public void open(int i, int j)
  {
    if(N < i || N < j)
    {
      throw new IndexOutOfBoundsException();
    }
    this.grid[i][j] = true;
    int position = getIndexPosition(i,j);
    
    if(i != 0)
      this.connections.union(position,getIndexPosition(i-1,j));
    if(i != N - 1) 
      this.connections.union(position,getIndexPosition(i+1,j));
    
    if(j != 0)
      this.connections.union(position,getIndexPosition(i,j-1));
    if(j != N - 1) 
      this.connections.union(position,getIndexPosition(i,j+1));
  }
  
  public boolean isOpen(int i, int j)
  {
    if(N < i || N < j)
    {
      throw new IndexOutOfBoundsException();
    }
    return this.grid[i][j];
  }
  
  public boolean isFull(int i, int j){
    if(N < i || N < j)
    {
      throw new IndexOutOfBoundsException();
    }
    for(int k = 0; k < this.N; k ++)
    {
      if(this.connections.connected(i*this.N+j,i))
      {
        return true;
      }
    }
    return false;
  }
  
  public boolean percolates(){
    for(int i = 0; i < this.N; i ++)
    {
      if(this.isFull(this.N-1,i))
      {
        return true;
      }
    }
    return false;
  }
  
  private int getIndexPosition(int i, int j)
  {
    return i*this.N+j;
  }
}