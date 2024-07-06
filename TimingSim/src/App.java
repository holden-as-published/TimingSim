/* 
 *      READ THIS FIRST
 *      This is a very lazy timing analysis calculator
 *      It's not super accurate, and simulates only at 1 sec resolution
 *      This can lead to some weird behavior where certain things do or dont line up
 * 

 *      
 * 
 *  CURRENT ASSUMPTIONS
 * 
 * 
 * 
 * ADJUSTMENTS
 * MakeDefenseDeterministic flag in team = randomizes source defense (plus minus 2 sec per cycle) and shot defense 0 or 1 sec when less than 3 sec of source defense
 * Check Team for calculation of values based on team init parameters
 * 
 * 
 * 
 */





public class App {

    TeamList teamList;
   


    public static void main(String[] args) throws Exception {

        //Define Blue Alliance. Teams always defend the opposing team in the same slot
    
       

        Alliance blueAlliance = new Alliance(TeamList.exampleTeam, TeamList.greatDefender, TeamList.dummyTeam);

        //Define Red Alliance. Teams always defend the opposing team in the same slot. 

        
        Alliance redAlliance = new Alliance(TeamList.fakeBarker, TeamList.fakeVikes, TeamList.fakeCitrus);

        System.out.println("Alliances Built. Beginning Sim");
        

        System.out.println("Red Alliance has scored: " + redAlliance.simulate(120, blueAlliance));
        System.out.println("Blue Alliance has scored: " + blueAlliance.simulate(120, redAlliance));

        System.out.println("Simulation Complete.");

        



    }
    
}
