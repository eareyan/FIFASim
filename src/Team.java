package fifasimulator;

import java.util.Comparator;

/**
 *  Class to implement the logic of a team and comparison between teams.
 *  @author enriqueareyan
 */
public class Team{
    /*
     * Name of the team. Usually the name of the country.
     */
    private String name;
    /*
     * Ranking of the team, a numerical value that reflects how good the team is.
     */
    private int ranking;
    /*
     * How many points accumulated at the group stage
     */
    private int groupPhasePoints;
    /*
     * Constructor must receive name and ranking.
     */
    public Team(String name, int ranking){
        this.name = name;
        this.ranking = ranking;
        this.groupPhasePoints = 0;
    }
    /*
     * Getters
     */
    public String getName(){
        return this.name;
    }
    public int getRanking(){
        return this.ranking;
    }
    public int getGroupPhasePoints(){
        return this.groupPhasePoints;
    }
    /*
     * This function represents winning a game, it just adds
     * 3 points to the team in the groupPhasePoints property
     */
    public void winsGame(){
        this.groupPhasePoints += 3;
    }
    /*
     * String representation of the object
     */
    @Override
    public String toString(){
        return "Team: " + this.getName() + ", ranking: "+this.getRanking();
    }
    /*
     * Function that plays a team against another.
     * Returns true if the first team wins and 0 otherwise
     */
    static Team playTeams(Team t1,Team t2){
            if(FifaSimulator.verbose){
                System.out.print(t1.toString() + " against " + t2.toString());
            }
            int totalRanking = t1.getRanking() + t2.getRanking();
            float winningProb = t1.getRanking()/(float)totalRanking;
            if(FifaSimulator.verbose){
                System.out.println(", Probability of team "+t1.getName()+" winning is:" + winningProb);
            }
            if(Math.random()<= winningProb){
                if(FifaSimulator.verbose){
                    System.out.println("Team 1 wins!");
                }
                return t1;
            }else{
                if(FifaSimulator.verbose){
                    System.out.println("Team 2 wins!");
                }
                return t2;
            }
    }
    /*
     * This function returns truw if the first team won
     * and false otherwise.
     */
    static boolean determineWinner(Team t1,Team t2){
        if(Team.playTeams(t1, t2) == t1){
            return true;
        }else{
            return false;
        }
    
    }
    /*
     * Compares two teams based on their ranking
     */
    static Comparator<Team> compareByRanking(){
        return new Comparator<Team>(){
        @Override
            public int compare(Team t1, Team t2) {
                if(t1.getRanking()>=t2.getRanking()){
                    return -1;
                }else{
                    return 1;
                }
            }
        };
    }
    /*
     * Compares two teams based of the points obtained in the group phase
     */
    static Comparator<Team> compareByGroupPhasePoints(){
        return new Comparator<Team>(){
            @Override
            public int compare(Team t1,Team t2){
                if(t1.getGroupPhasePoints()>=t2.getGroupPhasePoints()){
                    return -1;
                }else{
                    return 1;
                }
            }  
        };
    }
}
