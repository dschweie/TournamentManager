package org.dos.tournament.branch.boardgames.movement;

public class TableBasedIndividual
{
  private int participants;
  private int tables;
  private int chairs;
  private int rounds;

  private int[][] meetings;

  public TableBasedIndividual(int participants, int tables, int chairs, int rounds)
  {
    this.participants = participants;
    this.tables = tables;
    this.chairs = chairs;
    this.rounds = rounds;

    this.meetings = new int[participants][participants];
    for(int i = 0; i < participants; ++i)
      for(int j = 0; j < participants; ++j)
        this.meetings[i][j] = i==j?1:0;
  }

  public void generateMovement()
  {
    int[][] movement = new int[this.rounds][this.participants];

    int index = 0;
    int maxIndex = this.rounds * participants;

    while((maxIndex > index) && ( -1 != index ))
    {
      int currentRound = index / this.participants +1;
      int currentTable = ( index % this.participants ) / this.chairs;
      int currentChair = ( index % this.participants ) % this.chairs;
      int lastSeated = movement[currentRound-1][index % this.participants];
      int currentId = (movement[currentRound-1][index % this.participants] +1) % (this.participants+1);
      boolean isSeated = false;

      while((0 < currentId) && (!isSeated))
      { // Schritt vorwärts
        if( this.checkChair(movement, currentRound, currentTable, currentChair, currentId) )
        {
          lastSeated = currentId;
          this.setChair(movement, currentRound, currentTable, currentChair, currentId);
          isSeated = true;
        }
        else
          currentId = (++currentId) % (this.participants+1);
      }

      if(isSeated)
        ++index;
      else
      {
          this.releaseChair(movement, currentRound, currentTable, currentChair, lastSeated);
        --index;
      }

      isSeated = false;
    }

    for(int i=0; i < this.rounds; ++i)
      System.out.println(String.valueOf(i+1).concat(":").concat(this.printMovement(movement, i+1)));
  }

  private void setChair(int[][] movement, int round, int table, int chair, int id)
  {
    int     firstChair    = table * this.chairs;
    int     currentChair  = table * this.chairs + chair;

    for(int i = firstChair; i < currentChair; ++i)
    {
      this.meetings[ (movement[round-1][i])-1 ][  id-1 ] = 1;
      this.meetings[ id-1 ][  (movement[round-1][i])-1 ] = 1;
    }

    movement[round-1][currentChair] = id;
  }

  private void releaseChair(int[][] movement, int round, int table, int chair, int id)
  {
    int     firstChair    = (table) * this.chairs;
    int     currentChair  = (table) * this.chairs + chair;

    if((0 < id) && (0<round))
    {
      for(int i = firstChair; i < currentChair; ++i)
      { // prüfen, ob die Teilnehmer sich nicht begegnet sind
        this.meetings[ (movement[round-1][i])-1 ][  id-1 ] = 0;
        this.meetings[ id-1 ][  (movement[round-1][i])-1 ] = 0;
      }
    }
    else
    {
      System.out.println("WARNUNG");
    }
    movement[round-1][table*this.chairs + chair] = 0;
  }

  private boolean checkChair(int[][] movement, int round, int table, int chair, int id)
  {
    boolean retval        = true;
    int     firstChair    = table * this.chairs;
    int     currentChair  = table * this.chairs + chair;

    for(int i = 0; i < this.participants; ++i)
      retval &=     movement[round-1][i] != id ;

    for(int i = firstChair; i < currentChair; ++i)
    { // prüfen, ob die Teilnehmer sich nicht begegnet sind
      retval &=     ( 0 == this.meetings[ (movement[round-1][i])-1 ][ id-1 ] )
                &&  ( 0 == this.meetings[ id-1 ][ (movement[round-1][i])-1 ] ) ;
    }

    return retval;
  }

  protected String printMovement(int[][] movement, int round)
  {
    String retval = "";
    for(int i = 0; i < this.participants; ++i)
      retval = retval.concat(0==i%this.chairs?"   ":" ").concat(String.valueOf(movement[round-1][i]));
    return retval;
  }

  public static void main(String[] args)
  {
    TableBasedIndividual movement = new TableBasedIndividual(18, 6, 3, 2);
    movement.generateMovement();
  }
}
