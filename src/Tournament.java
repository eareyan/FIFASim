package fifasimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *  This class implements the main logic of a tournament.
 *  In this class we setup the teams and divide them into 8 groups.
 *  @author enriqueareyan
 */
public class Tournament {
    /*
     * A tournament is composed of teams and groups.
     * I also want to keep track of the winner, second, third and fourth place.
     */
    private Group[] Groups;
    private Team[] Teams;
    private Team[] Last8;
    private Team[] Last4;
    private Team[] FinalTeams;
    private Team[] ThirdAndFourth;
    private Team winner;
    private Team second;
    private Team third;
    private Team fourth;
    /*
     * main constructor
     */
    public Tournament() throws Exception{
        /*
         * A tournament consists of 32 teams and 8 groups.
         */
        this.Teams = new Team[32];
        this.Groups = new Group[8];
        this.Last8 = new Team[8];
        this.Last4 = new Team[4];
        this.FinalTeams = new Team[2];
        this.ThirdAndFourth = new Team[2];
        this.setTeams();
        this.setGroups();
    }
    /*
     * General getters
     */
    public Group[] getGroups(){
        return this.Groups;
    }
    public Group getGroup(int i){
        return this.Groups[i];
    }
    public Team[] getTeams(){
        return this.Teams;
    }
    
    public Team getWinner(){
        return this.winner;
    }
    public Team getSecond(){
        return this.second;
    }
    public Team getThird(){
        return this.third;
    }
    public Team getFourth(){
        return this.fourth;
    }    
    /*
     * Set teams.
     */
    private void setTeams(){
        /*
         * Add the 32 teams. The rankings are copied from the FIFA world cup ranking
         * located at http://www.fifa.com/worldranking/rankingtable/ on Oct, 29, 2014.
         */        
        this.Teams[0] = new Team("Germany",1669);
        this.Teams[1] = new Team("Argentina",1565);
        this.Teams[2] = new Team("Colombia",1420);
        this.Teams[3] = new Team("Belgium",1388);
        this.Teams[4] = new Team("Netherlands",1375);
        this.Teams[5] = new Team("Brazil",1307);
        this.Teams[6] = new Team("France",1191);
        this.Teams[7] = new Team("Uruguay",1184);
        this.Teams[8] = new Team("Portugal",1175);
        this.Teams[9] = new Team("Spain",1119);
        this.Teams[10] = new Team("Italy",1064);
        this.Teams[11] = new Team("Switzerland",1063);
        this.Teams[12] = new Team("Chile",1060);
        this.Teams[13] = new Team("Croatia",1002);
        this.Teams[14] = new Team("Algeria",989);
        this.Teams[15] = new Team("Costa Rica",974);
        this.Teams[16] = new Team("Mexico",954);
        this.Teams[17] = new Team("Greece",946);
        this.Teams[18] = new Team("Ukraine",920);
        this.Teams[19] = new Team("England",919);
        this.Teams[20] = new Team("Romania",876);
        this.Teams[21] = new Team("Czech Republic",870);
        this.Teams[22] = new Team("USA",862);
        this.Teams[23] = new Team("Slovakia",861);
        this.Teams[24] = new Team("Côte d'Ivoire",842);
        this.Teams[25] = new Team("Bosnia and Herzegovina",837);
        this.Teams[26] = new Team("Ecuador",826);
        this.Teams[27] = new Team("Iceland",816);
        this.Teams[28] = new Team("Austria",810);
        this.Teams[29] = new Team("Russia",792);
        this.Teams[30] = new Team("Tunisia",780);
        this.Teams[31] = new Team("Denmark",763);
        /*
         * Sort teams by ranking
         */
        Arrays.sort(this.Teams,Team.compareByRanking());
        
        
    }
    /*
     * Static function that takes an array of teams and creates groups.
     * Groups follow this structure: the first 8 groups (according to ranking)
     * are 'seeded' and place in 8 different groups.
     * The rest of the teams are then placed in the groups randomly.
     */
    private void setGroups() throws Exception{        
        /*
         * Divide into 8 groups.
         * First 8 teams are seeded and go into a separate group each.
         */        
        Group GroupA = new Group('A');
        GroupA.addTeam(0, this.Teams[0]);
        this.Groups[0] = GroupA;

        Group GroupB = new Group('B');
        GroupB.addTeam(0, this.Teams[1]);
        this.Groups[1] = GroupB;

        Group GroupC = new Group('C');
        GroupC.addTeam(0, this.Teams[2]);
        this.Groups[2] = GroupC;

        Group GroupD = new Group('D');
        GroupD.addTeam(0, this.Teams[3]);
        this.Groups[3] = GroupD;

        Group GroupE = new Group('E');
        GroupE.addTeam(0, this.Teams[4]);
        this.Groups[4] = GroupE;

        Group GroupF = new Group('F');
        GroupF.addTeam(0, this.Teams[5]);
        this.Groups[5] = GroupF;

        Group GroupG = new Group('G');
        GroupG.addTeam(0, this.Teams[6]);
        this.Groups[6] = GroupG;

        Group GroupH = new Group('H');
        GroupH.addTeam(0, this.Teams[7]);
        this.Groups[7] = GroupH;

        /*
         * Make a copy of the teams into an arraylist for manipulation:
         */
        ArrayList listOfTeams = new ArrayList<Team>(Arrays.asList(this.Teams));
        /*
         * Remove first 8 teams already included in each group as 'seeds'
         */
        for(int i=0;i<8;i++){
            listOfTeams.remove(0);
        }
        /*
         * For each group, pick a random team to put into
         */
        int r=0;
        Random rand = new Random();
        for(int i=0;i<8;i++){                         //For each group
            for(int j=0;j<3;j++){                     //Complete with 3 teams
                r = rand.nextInt(listOfTeams.size()); //Pick a random index for a team within the available teams
                this.Groups[i].addTeam(this.Groups[i].getCurrentNbrTeams(), (Team)listOfTeams.get(r));
                listOfTeams.remove(r);
            }
        }
    }
    /*
     * Runs the tournament. A tournament consists of a group play 
     * and a knockout stage.
     */
    public void runTournament() throws Exception{
        this.runGroupPlay();
        this.runKnockoutStage();
    }
    /*
     * This is an auxiliary function that calls a function of each group.
     */
    private void runGroupPlay() throws Exception{
        for(int i=0;i<8;i++){
            this.Groups[i].RunGroupPlay();
        }
    }   
    /*
     * This function runs Knockout Stage which consists of:
     *      Round of 16
     *      Quarter-finals
     *      Semi-finals
     *      Third place game
     *      Finals game
     */
    private void runKnockoutStage(){
        /*
         * For the Round of 16 the teams play like this:
         * 
         * A1-B2; C1-D2; E1-F2; G1-H2
         * B1-A2; D1-C2; F1-E2; H1-G2
         * 
         * In terms of our indeces:
         * 00-11; 20-31; 40-51; 60-71 
         * 10-01; 30-21; 50-41; 70-61
         */
        if(FifaSimulator.verbose){
            System.out.println("-----Round of 16------");
        }
        this.Last8[0] = Team.playTeams(this.Groups[0].getTeam(0), this.Groups[1].getTeam(1));// Play A1-B2 <=> 00-11
        this.Last8[1] = Team.playTeams(this.Groups[1].getTeam(0), this.Groups[0].getTeam(1));// Play B1-A2 <=> 10-01
        this.Last8[2] = Team.playTeams(this.Groups[2].getTeam(0), this.Groups[3].getTeam(1));// Play C1-D2 <=> 20-31
        this.Last8[3] = Team.playTeams(this.Groups[3].getTeam(0), this.Groups[2].getTeam(1));// Play D1-C2 <=> 30-21
        this.Last8[4] = Team.playTeams(this.Groups[4].getTeam(0), this.Groups[5].getTeam(1));// Play E1-F2 <=> 40-51
        this.Last8[5] = Team.playTeams(this.Groups[5].getTeam(0), this.Groups[4].getTeam(1));// Play F1-E2 <=> 50-41
        this.Last8[6] = Team.playTeams(this.Groups[6].getTeam(0), this.Groups[7].getTeam(1));// Play G1-H2 <=> 60-71
        this.Last8[7] = Team.playTeams(this.Groups[7].getTeam(0), this.Groups[6].getTeam(1));// Play H1-G2 <=> 70-61
        if(FifaSimulator.verbose){
            this.printLast8();
        }
        /*
         * For the Quarter-finals the teams play like this:
         * 
         * Winner of A1-B2 against winner of C1-D2
         * Winner of E1-F2 against winner of G1-H2
         * Winner of B1-A2 against winner of D1-C2
         * Winner of F1-E2 against winner of H1-G2
         * 
         * In terms of our indices for the variable Last8:
         * 0 against 2
         * 4 against 6
         * 1 against 3
         * 5 against 7
         */
        if(FifaSimulator.verbose){
            System.out.println("-----Quarter-finals------");
        }
        this.Last4[0] = Team.playTeams(this.Last8[0], this.Last8[2]); //
        this.Last4[1] = Team.playTeams(this.Last8[4], this.Last8[6]); //
        this.Last4[2] = Team.playTeams(this.Last8[1], this.Last8[3]); //
        this.Last4[3] = Team.playTeams(this.Last8[5], this.Last8[7]); //
        if(FifaSimulator.verbose){
            this.printLast4();
        }
        /*
         * Semifinals
         * 
         */
        if(Team.determineWinner(this.Last4[0], this.Last4[1])){
            //First team won!
            this.FinalTeams[0] = this.Last4[0];
            this.ThirdAndFourth[0] = this.Last4[1];
        }else{
            this.FinalTeams[0] = this.Last4[1];
            this.ThirdAndFourth[0] = this.Last4[0];
        }
        if(Team.determineWinner(this.Last4[2], this.Last4[3])){
            //First team won!
            this.FinalTeams[1] = this.Last4[2];
            this.ThirdAndFourth[1] = this.Last4[3];
        }else{
            this.FinalTeams[1] = this.Last4[3];
            this.ThirdAndFourth[1] = this.Last4[2];
        }
        if(FifaSimulator.verbose){
            System.out.println("Final two teams:"+this.FinalTeams[0]+ " and "+ this.FinalTeams[1]);
            System.out.println("Third and fourt:"+this.ThirdAndFourth[0]+ " and "+ this.ThirdAndFourth[1]);
        }
        /*
         * Play Final fase to determine winner, second, third and fourth
         */
        if(Team.determineWinner(ThirdAndFourth[0], ThirdAndFourth[1])){
            third   = ThirdAndFourth[0];
            fourth  = ThirdAndFourth[1];
        }else{
            third   = ThirdAndFourth[1];
            fourth  = ThirdAndFourth[0];
        }
        if(Team.determineWinner(FinalTeams[0], FinalTeams[1])){
            winner = FinalTeams[0];
            second = FinalTeams[1];
        }else{
            winner = FinalTeams[1];
            second = FinalTeams[0];
        }
        if(FifaSimulator.verbose){
            System.out.println("Winner!: "  +   winner.getName());
            System.out.println("Second: "   +   second.getName());
            System.out.println("third: "    +   third.getName());
            System.out.println("fourth: "   +   fourth.getName());
        } 
    }
    /*
     * Auxiliary printers
     */
    public void printLast8(){
        System.out.println("Final 8:");
        for(int i=0;i<8;i++){
            System.out.println(this.Last8[i].getName());
        }
        
    }
    public void printLast4(){
        System.out.println("Final 4:");
        for(int i=0;i<4;i++){
            System.out.println(this.Last4[i].getName());
        }
        
    }     
}
