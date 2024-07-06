import java.util.Random;

public class Team {
    

boolean hasPiece = false;
int role;
int crossFieldTime;
int pickupTime;
int scoringTime;
int sourceDefenseTime;
String name;
Random randoCalrissian;

boolean makeDefenseDeterministic = false;


   public Team (int autoScorePieces, int crossFieldTime, int pickupTime, int scoringTime, int defensetime, int role, String name)
   {
        this.role = role;
        this.crossFieldTime = crossFieldTime;
        this.pickupTime = pickupTime;
        this.scoringTime = scoringTime;
        this.sourceDefenseTime = defensetime;
        this.name = name;

        randoCalrissian = new Random();
       
   }

   public void cycle()
   {

   }

   public void recievePiece()
   {

   }

   public int getRole()
   {
    return role;
   }

   public int getCrossFieldTime()
   {
     return crossFieldTime;
   }

   public int getSourceDefenseTime()
   {
    if (makeDefenseDeterministic)
     return sourceDefenseTime;

    else
    {

      int adjustedDefense = sourceDefenseTime;
      if (sourceDefenseTime >0)
      {
        if(randoCalrissian.nextBoolean())
        adjustedDefense = sourceDefenseTime+2;

        else
        adjustedDefense = sourceDefenseTime-2;

      }
      return adjustedDefense;
    }
    
   }

   public int getShotDefenseTime()
   {
    

    int coverageTime = -999;
   
   if (makeDefenseDeterministic)
    {
      coverageTime = sourceDefenseTime/3;
    }
   else
   {

    coverageTime = sourceDefenseTime/3;
  
    if(coverageTime==0 && sourceDefenseTime > 0) //when defense time is minimal, sometimes give 1 sec of coverage.  Reflects poor but extant defense.
     {
      if(randoCalrissian.nextBoolean())
        coverageTime = 1;
     } 
     else
     {
      if(randoCalrissian.nextBoolean())
      coverageTime = coverageTime+2;
      else
      coverageTime = coverageTime-2;
     }
     if (coverageTime<0)
     coverageTime = 0;

    System.out.println("Defned shot time final adjust" + coverageTime);
    
   }
    return coverageTime;
   }

   public int getCycleTime()
   {

     return pickupTime+crossFieldTime+scoringTime;
     
   }

   public int getTransferTime()
   {
      int timeToWing = 1;
      int shotTime = 1;

      timeToWing = crossFieldTime/3;
      if (timeToWing == 0)
        timeToWing = 1;

    shotTime = scoringTime/2;
      if (shotTime == 0)
        shotTime = 1;


    return pickupTime + (timeToWing) + (shotTime);
   }

   public int getShortCycleTime()
   {
    int timeToWing = 1;
    timeToWing = crossFieldTime/3;

      if (timeToWing == 0)
        timeToWing = 1;

    return pickupTime + (timeToWing) + (scoringTime);

   }

   public String getName()
   {
    return name;
   }
  

}
