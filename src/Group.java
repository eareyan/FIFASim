package fifasimulator;

import java.util.Arrays;

/**
 *  This class represent a group which is form by 4 teams.
 *  @author enriqueareyan
 */
public class Group {
    /*
     * A group consist of an array of teams.
     * In the constructo we set the number of teams to be fixed to 4.
     */
    
    private Team[] teams;
    private int currentNbrTeams = 0;
    private char letter;
    
    public Group(char letter){
        this.teams = new Team[4];
        this.letter = letter;
        
    }
    /*
     * Getters
     */
    public Team getTeam(int i){
        return this.teams[i];
    }
    public int getCurrentNbrTeams(){
        return this.currentNbrTeams;
    }
    /*
     * Adds a team to this group.
     */
    public void addTeam(int i,Team t) throws Exception{
        if(!(i<4 && i>=0)){
            throw new Exception("Invalid team position");
        }
        teams[i] = t;
        this.currentNbrTeams++;
    }
    /*
     * Runs the simulation for the group stage play of this group.
     * The structure is the following: for each team,
     * play against two other teams and record 3 points for a win and 
     * 0 for a loss (as of a first implementation, there are no loss).
     */
    public void RunGroupPlay() throws Exception{
        
        if(this.currentNbrTeams != 4){
            throw new Exception("Can't run group play without having exactly 4 teams");
        }
        
        int team1Index=0,team2Index=0;
        for(int i=0;i<6;i++){
            /*
             * Play a team against each other:
             */
            switch(i){
                case 0: team1Index = 0; team2Index = 1;break;
                case 1: team1Index = 0; team2Index = 2;break;
                case 2: team1Index = 0; team2Index = 3;break;
                case 3: team1Index = 1; team2Index = 2;break;
                case 4: team1Index = 1; team2Index = 3;break;
                case 5: team1Index = 2; team2Index = 3;break;
                    
            }
            /*
             * Add points to the team that won the game.
             */
            Team.playTeams(this.getTeam(team1Index), this.getTeam(team2Index)).winsGame();
        }
        /*
         * Sort the group by its table
         */
        Arrays.sort(this.teams,Team.compareByGroupPhasePoints());
        if(FifaSimulator.verbose){
            this.printGroupTable();
        }
    } 
    /*
     * Prints the group table
     */
    public void printGroupTable(){
        System.out.println("---Final table for team "+this.letter+":---");
        for(int k=0;k<4;k++){
            System.out.println(this.teams[k].getName()+"\t\t\t"+this.teams[k].getGroupPhasePoints());
        }
    }
    /*
     * String representation of object
     */
    @Override
    public String toString(){
        return "Group "+this.letter+ ":"+ Arrays.toString(this.teams);
    }
}
