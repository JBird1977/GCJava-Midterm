/* this class creates and object called Minefield
 * and represent an individual square on the gameboard
 * This object has two boolean fields: revealed and mine.
 * 
 * revealed is true when the object/space is no longer hidden from the player
 * revealed is false when the object/space is hidden
 * 
 * mine is true if the object/space contains a mine
 * mine is false if the object/space does not contain a mine
 */


public class Minefield {
    private boolean revealed;
    private boolean mine;
    private int adjacency;
    
    

    public Minefield() {        //set defaults to hide the tile and to not have a mine
        this.revealed = false;
        this.mine = false;
        this.adjacency = 0;
    }
    
    public Minefield(boolean revealed, boolean mine) 
    {
        this.revealed = revealed;
        this.mine = mine;
        this.adjacency = adjacency;
    }
    
    public void setRevealed(boolean reveal) 
    {
        this.revealed =  reveal; //sets whether tile is revealed or not      
    } //end setRevealed
    
    public  boolean getRevealed() 
    {
        return this.revealed; //returns the state of revealed
    } //end getRevealed

    public void setMine(boolean mine) 
    {
        this.mine = mine;
    } //end setMine
    
    public boolean getMine() 
    {
        return this.mine; //returns the state of whether this object has a mine
    } //end getMine

    public int getAdjacency() 
    {
        return adjacency;
    }

    public void setAdjacency(int adjacency) 
    {
        this.adjacency = adjacency;
    }
  
    
    @Override
    public String toString() 
    {
        return "Revealed: " + revealed + ", Is there a mine: " + mine + ", Adjancent to " + adjacency + " mines.]";
    } //end toString Override

    
    
} // end Minefield


