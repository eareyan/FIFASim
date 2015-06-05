package fifasimulator;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author enriqueareyan
 */
public class Statistics {
    
    private Tournament T;
    
    public Statistics(Tournament T){
        this.T = T;
    }
    /*
     * This function returns 1 if the team i won the tournament
     */
    public int teamWon(int i){
        if(this.T.getWinner() == this.T.getTeams()[i]){
            return 1;
        }else{
            return 0;
        }
    }
    /*
     * This function return 1 if the team 1 was at least third on the tournament
     */
    public int teamAtLeastThrid(int i){
        if(this.T.getWinner() == this.T.getTeams()[i] || this.T.getSecond() == this.T.getTeams()[i] || this.T.getThird() == this.T.getTeams()[i]){
            return 1;
        }else{
            return 0;
        }
    }
    /*
     * Static function that appends data to a file where the
     * file location and the data are received as parameters
     */
    public static void writeToFile(String fileName,String data) throws IOException{
        FileWriter writer = new FileWriter(fileName,true);
        writer.write(data);
        writer.close();
    }
}
