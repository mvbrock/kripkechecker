/*
 * Main.java
 *
 * Created on May 6, 2006, 11:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kripkechecker;
import java.io.*;
/**
 *
 * @author matt
 */
public class Main {
    private static BufferedReader stdin = new BufferedReader( 
                                new InputStreamReader( System.in ) );
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        Universe universe = new Universe();
        boolean retVal = false;
        String input = "";
        while( retVal == false)
        {
            System.out.println("Enter the worlds seperated by commas or spaces: ");
            input = stdin.readLine();
            retVal = universe.createWorlds( input );
        }
        for( int i = 0; i < universe.getWorldCount(); i++ )
        {
            retVal = false;
            while( retVal == false )
            {
                System.out.println("Enter the labels for world \'" +
                        universe.getWorld(i) + "\' seperated by commas or spaces: ");
                input = stdin.readLine();
                retVal = universe.createLabels(input, universe.getWorld(i) );
            }
        }
        retVal = false;
        while( retVal == false )
        {
            System.out.println("Enter the relations to the worlds seperated by commas," +
                    "(<world>,<world>): ");
            input = stdin.readLine();
            retVal = universe.createRelations(input);
        }
        retVal = false;
        while(retVal == false)
        {
            System.out.println("Enter the formula in the documented Kripke BNF format: ");
            input = stdin.readLine();
            retVal = universe.createFormula(input);
        }
        System.out.println();
        universe.startEvaluation();
    }    
}
