package fifasimulator;

import java.io.FileNotFoundException;

/**
 *  Main class of the simulator, ties all pieces together.
 *  @author enriqueareyan
 */
public class FifaSimulator {
    
    static boolean verbose = false;
    static int numberOfRuns = 500;
    static int numberOfTournaments = 100;
    static String resultsDir = "results/";
    static int progress = 0;

    private static void parseArgument(String[] args){
        if(args.length > 0){
            /*
             * Parse all command line arguments
             */
            for(int i=0;i<args.length;i++){
                if("-help".equals(args[i])){ //If a --help comnad is received, show help and don't run simulation
                System.out.println("\nWelcome to FifaSimulator! \n\n"
                        + "Usage: java -jar FifaSimulator.jar [-options] \n"
                        + "where options include:\n"
                        + "\t\t -help \t\t\t this helper. Only shows this helper and does not run the simulation\n"
                        + "\t\t -numberOfRuns \t\t <number of runs of the simulation>\n"
                        + "\t\t -numberOfTournaments \t <number of tournaments per run of the simulation>\n"
                        + "\t\t -resultsDir \t <directory where the results should be stored>\n"
                        + "\t\t -verbose \t <yes or no (no by default). Prints quite a bit of information about the simulation>\n"
                        + "If no arguments are received or the arguments are not recognized, then the simulation run with the following default parameters:\n"
                        + "\t-numberOfRuns 500, -numberOfTournaments 100, -resultsDir results/, -verbose no\n");
                    System.exit(0); //Exit the simulator
                }else if("-numberOfRuns".equals(args[i])){
                    FifaSimulator.numberOfRuns = Integer.parseInt(args[i+1]);
                }else if("-numberOfTournaments".equals(args[i])){
                    FifaSimulator.numberOfTournaments = Integer.parseInt(args[i+1]);
                }else if("-resultsDir".equals(args[i])){
                    FifaSimulator.resultsDir = args[i+1];
                }else if("-verbose".equals(args[i])){
                    if("yes".equals(args[i+1])){
                        FifaSimulator.verbose = true;
                    }
                }
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws Exception{
         
         FifaSimulator.parseArgument(args);
        
        for(int i=0;i<numberOfRuns;i++){
            int favoriteWon=0,
                favoriteAtLeastThird = 0,
                secondFavoriteWon = 0,
                secondFavoriteAtLeastThird = 0,
                thirdFavoriteWon = 0,
                thirdFavoriteAtLeastThird = 0,
                leastFavoriteWon=0,
                leastFavoriteAtLeastThird = 0,
                nbrTourFavoriteWins = 0,
                nbrTourLeastFavoriteWins = 0;
            boolean favoriteWonYet = false,
                    leastFavoriteWonYet = false;
            
            for(int j=0;j<numberOfTournaments;j++){
                /*
                 * Create Tournament
                 */
                Tournament T = new Tournament();
                if(FifaSimulator.verbose){
                    System.out.println("FIFA:");
                    /*
                     * Create Groups
                     */
                    System.out.println("-----From main code:----");
                    System.out.println(T.getGroup(0));
                    System.out.println(T.getGroup(1));
                    System.out.println(T.getGroup(2));
                    System.out.println(T.getGroup(3));
                    System.out.println(T.getGroup(4));
                    System.out.println(T.getGroup(5));
                    System.out.println(T.getGroup(6));
                    System.out.println(T.getGroup(7));
                }
                /*
                 * Run tournament
                 */
                T.runTournament();
                /*
                 * Done with tournament, compile statistics
                 */
            
                //Compile statistics for the number of times a team won the tournament
                Statistics S = new Statistics(T);
                favoriteWon += S.teamWon(0);//Team 0 is the favorite
                favoriteAtLeastThird += S.teamAtLeastThrid(0);
                secondFavoriteWon += S.teamWon(1);//Team 1 is the second favorite
                secondFavoriteAtLeastThird += S.teamAtLeastThrid(1);
                thirdFavoriteWon += S.teamWon(2);//Team 2 is the third favorite
                thirdFavoriteAtLeastThird += S.teamAtLeastThrid(2);
                leastFavoriteWon += S.teamWon(31);// Team 31 is the least favorite
                leastFavoriteAtLeastThird += S.teamAtLeastThrid(31);
            
                //Compile statistics for the number of tournaments until a team won the tournament
                if(!favoriteWonYet && (S.teamWon(0) == 0)){
                    nbrTourFavoriteWins++;
                }else{
                    favoriteWonYet = true;
                }
                if(!leastFavoriteWonYet && (S.teamWon(31) == 0)){
                    nbrTourLeastFavoriteWins++;
                }else{
                    leastFavoriteWonYet = true;
                }
            
            }
            /*
             * Write data to log files
             */
            try{
                Statistics.writeToFile(resultsDir + "firstFavorite.csv",favoriteWon+",");
                Statistics.writeToFile(resultsDir + "secondFavorite.csv",secondFavoriteWon+",");
                Statistics.writeToFile(resultsDir + "thirdFavorite.csv",thirdFavoriteWon+",");
                Statistics.writeToFile(resultsDir + "leastFavorite.csv",leastFavoriteWon+",");

                Statistics.writeToFile(resultsDir + "firstFavoriteAtLeastThird.csv",favoriteAtLeastThird+",");
                Statistics.writeToFile(resultsDir + "secondFavoriteAtLeastThird.csv",secondFavoriteAtLeastThird+",");
                Statistics.writeToFile(resultsDir + "thirdFavoriteAtLeastThird.csv",thirdFavoriteAtLeastThird+",");
                Statistics.writeToFile(resultsDir + "leastFavoriteAtLeastThird.csv",leastFavoriteAtLeastThird+",");
            
                Statistics.writeToFile(resultsDir + "firstNbrTourWins.csv",nbrTourFavoriteWins+",");
                Statistics.writeToFile(resultsDir + "leastNbrTourWins.csv",nbrTourLeastFavoriteWins+",");
            }catch(FileNotFoundException e){
                System.out.println("Warning! could not write results. Most likely the provided directory does "
                        + "not exists. The provided directory is: " + FifaSimulator.resultsDir);
            }
            /*
             * Report on the progress of the simulation.
             */
            if(Float.compare((float)i/FifaSimulator.numberOfRuns, (float)0.20)<0){
                System.out.print("[#                   ] 1% \t "+(i+1)+"/"+FifaSimulator.numberOfRuns+"\r");
            }else if(Float.compare((float)i/FifaSimulator.numberOfRuns, (float)0.40)<0) {
                System.out.print("[#####               ] 25%\t "+(i+1)+"/"+FifaSimulator.numberOfRuns+"\r");
            }else if(Float.compare((float)i/FifaSimulator.numberOfRuns, (float)0.60)<0) {
                System.out.print("[##########          ] 50%\t  "+(i+1)+"/"+FifaSimulator.numberOfRuns+"\r");
            }else if(Float.compare((float)i/FifaSimulator.numberOfRuns, (float)0.95)<0) {
                System.out.print("[###############     ] 75%\t  "+(i+1)+"/"+FifaSimulator.numberOfRuns+"\r");
            }else{
                System.out.print("[####################] 100%\t "+(i+1)+"/"+FifaSimulator.numberOfRuns+"\r");
            }
        }
        System.out.println("");
    }
}
