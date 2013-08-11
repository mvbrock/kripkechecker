/*
 * Universe.java
 *
 * Created on May 7, 2006, 12:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kripkechecker;

import java.util.Vector;
import java.util.Hashtable;
/**
 *
 * @author matt
 */
public class Universe {
    
    Vector worldArray;
    Vector labelArray;
    Hashtable worldHash;
    Tree formulaTree;
    String formulaString;
    
    /** Creates a new instance of Universe */
    public Universe() {
        worldArray = new Vector();
        labelArray = new Vector();
        worldHash = new Hashtable();
    }
    
    public boolean createFormula( String formula )
    {
        formulaString = formula;
        Tokenizer tokenizer = new Tokenizer( formula );
        formulaTree = new Tree( tokenizer );
        return formulaTree.parse();
    }
    
    public boolean createLabels( String labels, World world )
    {
        Tokenizer tokenizer = new Tokenizer( labels );
        for(int i = 0; i < tokenizer.length(); i++)
        {
            Token token = tokenizer.getToken(i);
            if( token.getId() == TokenId.LABEL )
            {
                world.addLabel(token.getLabel());
            } else
            {
                if( token.getId() != TokenId.COMMA )
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean createWorlds( String worlds )
    {
        Tokenizer tokenizer = new Tokenizer( worlds );
        for(int i = 0; i < tokenizer.length(); i++)
        {
            Token token = tokenizer.getToken(i);
            if( token.getId() == TokenId.LABEL )
            {
                World world = new World(token.getLabel());
                worldArray.add( world );
                worldHash.put( token.getLabel(), world );
                if( worldArray.size() != worldHash.size() )
                {
                    System.out.println("Duplicate worlds are not allowed.");
                    return false;
                }
            } else
            {
                if( token.getId() != TokenId.COMMA )
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean createRelations( String relations )
    {
        Tokenizer tokenizer = new Tokenizer( relations );
        int i = 0;
        while( i < tokenizer.length() )
        {
            Token token = tokenizer.getToken(i++);
            if(token == null || token.getId() != TokenId.LPAREN )
                return false;
            token = tokenizer.getToken(i++); 
            if( token == null || token.getId() != TokenId.LABEL )
                return false;
            
            World worldOne = (World)worldHash.get(token.getLabel());
            if(worldOne == null)
            {
                System.out.println("World \'" + token.getLabel() + "\' at position " +
                        token.getPosition() + " does not exist.");
                return false;
            }
            token = tokenizer.getToken(i++); 
            if( token == null || token.getId() != TokenId.COMMA )
                return false;
            token = tokenizer.getToken(i++); 
            if( token == null || token.getId() != TokenId.LABEL )
                return false;
            
            World worldTwo = (World)worldHash.get(token.getLabel());
            if(worldTwo == null)
            {
                System.out.println("World \'" + token.getLabel() + "\' at position " +
                        token.getPosition() + " does not exist.");
                return false;
            }
            
            worldOne.addRelation(worldTwo);            
            
            token = tokenizer.getToken(i++); 
            if( token == null || token.getId() != TokenId.RPAREN )
                return false;
            token = tokenizer.getToken(i++); 
            if( token != null )
            {
               if( token.getId() != TokenId.COMMA ||
                        ( token.getId() == TokenId.COMMA && i == tokenizer.length()))
                    return false;
            }
        }
        
        return true;
    }
    
    Tree getFormulaTree() { return formulaTree; }
    World getWorld( int idx ) { return (World)worldArray.get( idx ); }
    int getWorldCount() { return worldArray.size(); }
    
    void startEvaluation()
    {
        Vector satisfiedEvaluation = new Vector();
        for( int i = 0; i < worldArray.size(); i++ )
        {
            boolean retVal = evaluate( (World)worldArray.get(i), formulaTree.getRoot(), 
                                formulaTree.getRoot().getModalArray() );
            satisfiedEvaluation.add( retVal );
        }
        System.out.println("Worlds satisfying the formula, " + formulaString + ":");
        for( int i = 0; i < satisfiedEvaluation.size(); i++ )
            if( (Boolean)satisfiedEvaluation.get(i) == true )
                System.out.print((World)worldArray.get(i) + ", ");
        System.out.println("\n");
        System.out.println("Worlds NOT satisfying the formula, " + formulaString + ":");
        for( int i = 0; i < satisfiedEvaluation.size(); i++ )
            if( (Boolean)satisfiedEvaluation.get(i) == false )
                System.out.print((World)worldArray.get(i) + ", ");     
    }
    
    boolean evaluate( World world, TreeNode node, Vector modalArray )
    {
        if( modalArray.size() == 0 )
            return evaluate( world, node );
        
        boolean retVal;
        int truthTotals = 0;
        Vector nextModalArray = (Vector)modalArray.clone();
        Modal firstModal = (Modal)nextModalArray.remove(0);
        if( firstModal == Modal.Not )
        {
            retVal = evaluate( world, node );
            if( retVal == true )
                return false;
            else
                return true;
        }
        Vector returnValueArray = new Vector();
        
        for( int i = 0; i < world.getRelationCount(); i++ )
        {
            retVal = evaluate( world.getRelation(i), node, nextModalArray);
            if( retVal == true )
                truthTotals++;
        }
        if( firstModal == Modal.Box )
        {
            if( truthTotals == world.getRelationCount() && truthTotals > 0 )
                return true;
            else
                return false;
        }
        if( firstModal == Modal.Diamond )
        {
            if( truthTotals > 0 )
                return true;
            else
                return false;
        }
        return true;
    }
    
    boolean evaluate( World world, TreeNode node )
    {
        Token token = node.getToken();
        if( token.getId() == TokenId.LABEL )
        {
            return world.matchLabel( token.getLabel() );
        }
        if( token.getId() == TokenId.AND || token.getId() == TokenId.OR || 
                token.getId() == TokenId.IMPLIES )
        {
            boolean retValOne = evaluate( world, node.getChildOne(),
                                    node.getChildOne().getModalArray());
            boolean retValTwo = evaluate( world, node.getChildTwo(),
                                    node.getChildTwo().getModalArray());
            if( token.getId() == TokenId.AND )
                return retValOne & retValTwo;
            if( token.getId() == TokenId.OR )
                return retValOne | retValTwo;
            if( token.getId() == TokenId.IMPLIES )
                return !retValOne | retValTwo;
        }
        return false;
    }    
}
