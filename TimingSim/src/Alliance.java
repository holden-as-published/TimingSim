public class Alliance {
    
    int scoredAmp = 0;
    int scoredSpeakerUnamped = 0;
    int scoredSpeakerAmped = 0;
    int hoardStack = 0;
    int ampTimer = 10;
    boolean isAmped = false;
    boolean ampAvail = false;
    int ampNotesAvail = 0;
    int scoredAmplifiedThisPeriod = 0;
    int scoredSpeakerHolding = 0;
    boolean waitingForPiece = false;
    
    boolean printAllScoringEvents = true;
    Team t1;
    Team t2;
    Team t3;
    Team[] userAlliance;

    int i;

    public Alliance (Team team1, Team team2, Team team3) 
    {     userAlliance = new Team[3];
          userAlliance[0] = team1;
          userAlliance[1] = team2;
          userAlliance[2] = team3;
    }

    public int simulate (int MatchLength, Alliance opposition)
    {
        int allianceScore = 9999;
      

        
      

      for (int time = 0; time < 121; time++)
        {
            //Process alliance Actions
                if (ampNotesAvail >=2)
                {
                    ampAvail = true;
                }
                if (ampAvail) //always amp as soon as avail
                {
                    if(printAllScoringEvents)
                    System.out.println(" amp avail");

                    isAmped = true;
                    ampAvail = false;
                    ampNotesAvail = 0;
                    scoredAmplifiedThisPeriod = 0;
                    ampTimer = 10;
                    scoredSpeakerHolding = scoredSpeakerAmped;
                }

                if (isAmped)

                {
                    if(printAllScoringEvents)
                    System.out.println(" isamped at " + time + " with" + ampTimer +" remaining in amp ");

                    scoredAmplifiedThisPeriod = scoredSpeakerAmped - scoredSpeakerHolding; 
                    ampTimer --;
                }

                if (ampTimer == 0 || scoredAmplifiedThisPeriod == 4)
                {
                    isAmped = false;
                }               

     for (int i = 0; i<3; i++)
        {
           if (userAlliance[i].getRole()==0)
           
           {
                if ((time % (userAlliance[i].getCycleTime()+ opposition.getshotDefenseTime(i)) == 0) && (time !=0))
                {
                    if(isAmped)
                    {
                        if(printAllScoringEvents)
                            System.out.println("teammate " + userAlliance[i].getName() +" scored amped! at time " + time);
                        

                        scoredSpeakerAmped++; 
                    
                        
                    }
                    else
                    {
                        scoredSpeakerUnamped++;

                        if(printAllScoringEvents)
                            System.out.println("teammate " + userAlliance[i].getName() + " scored unamped! at time " + time);
                        

                     }
                }
            }
        //role 1: pure amp cycling

             if(userAlliance[i].getRole()==1)
             //System.out.println("hey i processed amp but badly");
             {
                 if (((time % (userAlliance[i].getCycleTime() + opposition.getshotDefenseTime(i))) == 0) && (time !=0))
                 {   
                    scoredAmp++;
                    ampNotesAvail++;

                    if(printAllScoringEvents){
                    System.out.println("teammate " + userAlliance[i].getName() +" scored in the amp at time " + time + "with notes avail" + ampNotesAvail);
                    }
                }
            }

        // role 2: hybrid cycler

            if(userAlliance[i].getRole()==2)             {
                 if (((time % (userAlliance[i].getCycleTime() + opposition.getshotDefenseTime(i))) == 0) && (time !=0))
                 {   
                    if (isAmped)
                    {
                        scoredSpeakerAmped++; 

                        if (printAllScoringEvents)
                        System.out.println("teammate " + userAlliance[i].getName() +" scored amped! at time " + time);


                    }
                    else if (ampAvail)
                    {  
                         isAmped = true;
                        ampAvail = false;
                        ampNotesAvail = 0;
                        scoredAmplifiedThisPeriod = 0;
                        ampTimer = 10;
                        scoredSpeakerHolding = scoredSpeakerAmped + 1;
                        scoredSpeakerAmped++; 

                        if (printAllScoringEvents)
                        System.out.println("teammate " + userAlliance[i].getName() +" scored amped! at time " + time + "as a first shot in amplification");
                    }
                    else if (time > 110 && ampNotesAvail <=1 ) //conditions in which you just dump in speaker, adjust this later 03JUL HS
                    {
                        scoredSpeakerUnamped++;

                        if(printAllScoringEvents){
                            System.out.println("teammate " + userAlliance[i].getName() + " scored unamped! at time " + time + "due to time remaining");
                        }
                    }
                    else
                    {
                        scoredAmp++;
                        ampNotesAvail++;
                        if (ampNotesAvail >= 2 ) {
                            isAmped = true;
                            ampAvail = false;
                            ampNotesAvail = 0;
                            scoredAmplifiedThisPeriod = 0;
                            ampTimer = 10;
                            scoredSpeakerHolding = scoredSpeakerAmped;

                            
                        }

                    
                        if(printAllScoringEvents){
                        System.out.println("teammate" + userAlliance[i].getName()+" scored in the amp at time " + time + "with notes avail" + ampNotesAvail + "due to default action to score amp");
                    }
                        
                    }
                    

                

                }
            }
                
         // role 3: passer

                if (userAlliance[i].getRole() == 3)
                {
                    if (((time % (userAlliance[i].getTransferTime() + opposition.getSourceDefenseTime(i))) == 0) && (time !=0))
                    {
                        hoardStack++; 

                        if(printAllScoringEvents)
                        System.out.println("teammate " + userAlliance[i].getName() + " sent a piece to the hoard stack at time " + time +"  which is size " + hoardStack + "under defebse from" + opposition.getName(i));
                    }

                }

            // role 4: hybrid scorer, passing strat

                if(userAlliance[i].getRole()==4)
                {
                    if (((time % (userAlliance[i].getShortCycleTime() + opposition.getshotDefenseTime(i))) == 0) && (time !=0) || waitingForPiece) 
                    {  

                    if (hoardStack>0) 
                    {
                     waitingForPiece = false;
                       if (isAmped)
                       {
                           hoardStack--;
                           scoredSpeakerAmped++; 
   
                           if (printAllScoringEvents)
                           System.out.println("teammate " + userAlliance[i].getName() +" scored amped! at time " + time + "under defebse from" + opposition.getName(i));
   
   
                       }
                       else if (ampAvail)
                       {  
                            isAmped = true;
                           ampAvail = false;
                           ampNotesAvail = 0;
                           scoredAmplifiedThisPeriod = 0;
                           ampTimer = 10;
                           scoredSpeakerHolding = scoredSpeakerAmped + 1;
                           hoardStack--;
                           scoredSpeakerAmped++; 
   
                           if (printAllScoringEvents)
                           System.out.println("teammate " + userAlliance[i].getName() +" scored amped! at time " + time + "as a first shot");
                       }
                       else if (time > 110 && ampNotesAvail <=1 ) //conditions in which you just dump in speaker, adjust this later 03JUL HS
                       {
                            hoardStack--;
                           scoredSpeakerUnamped++;
   
                           if(printAllScoringEvents){
                               System.out.println("teammate " + userAlliance[i].getName() + " scored unamped! at time " + time + "due to time remaining");
                           }
                       }
                       else
                       {
                           hoardStack--;
                           scoredAmp++;
                           ampNotesAvail++;
                           if (ampNotesAvail >= 2 ) 
                           {
                               isAmped = true;
                               ampAvail = false;
                               ampNotesAvail = 0;
                               scoredAmplifiedThisPeriod = 0;
                               ampTimer = 10;
                               scoredSpeakerHolding = scoredSpeakerAmped;   
                           }
   
                       
                           if(printAllScoringEvents)
                           {
                           System.out.println("teammate" + userAlliance[i].getName()+" scored in the amp at time " + time + "with notes avail" + ampNotesAvail + "due to default action to score amp");
                           }
                       }
                           
                    }
                    else
                    {
                        waitingForPiece = true;
                        if (printAllScoringEvents) 
                        {
                            System.out.println (userAlliance[i].getName()+ "waiting for piece at time " + time);
                        }
                        if(hoardStack<0)
                        {
                            System.out.println("The hoard stack went negative! This shouldnt happen. Reset to 0");
                            hoardStack = 0;
                        }
                    }
                       
   
                   
   
                   }
               }

                //role 5: defense
                //nothing happens here because defensetime is accounted for in the opposing offense
        
                
        }

           
        }

        if (printAllScoringEvents)
        {
            System.out.println("scored total in the amp:  " +scoredAmp);
            System.out.println( " scored toatl in the speaker unamplified: "+ scoredSpeakerUnamped );
            System.out.println("scored in speaker while ampliefed : " + scoredSpeakerAmped);
        }
        
        allianceScore = scoredAmp + (2*scoredSpeakerUnamped)+ (5*scoredSpeakerAmped);

        return allianceScore;
    }

    public int getshotDefenseTime(int index)
    {
        return userAlliance[index].getShotDefenseTime();
    }

    public int getSourceDefenseTime(int index)
    {
        return userAlliance[index].getSourceDefenseTime();
    }

    public String getName(int index)
    {
        return userAlliance[index].getName();
    }
}




