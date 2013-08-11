/*
 * TreeNode.java
 *
 * Created on May 6, 2006, 3:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kripkechecker;
import java.util.Vector;

/**
 *
 * @author matt
 */
public class TreeNode
{
    boolean evaluated;
    boolean isEvaluated;
    boolean isLeaf;
    Token token;
    Vector modalArray;
    TreeNode childOne;
    TreeNode childTwo;
    TreeNode parent;
    /** Creates a new instance of TreeNode */
    public TreeNode()
    {
        modalArray = new Vector();
    }
    public TreeNode( Token nToken )
    {
        token = nToken;
        isEvaluated = false;
        modalArray = new Vector();
    }
    public TreeNode( Token nToken, boolean nIsLeaf )
    {
        token = nToken;
        isLeaf = nIsLeaf;
        isEvaluated = false;
        modalArray = new Vector();
    }
    
    boolean evaluate()
    {
        return true;
    }
    
    void addModal( Modal modal ) { modalArray.add( modal ); }
    Vector getModalArray() { return modalArray; }
    int getModalArrayCount() { return modalArray.size(); }
    
    TreeNode getChildOne() { return childOne; }
    TreeNode getChildTwo() { return childTwo; }
    boolean getEvaluated() { return evaluated; }
    boolean getIsLeaf() { return isLeaf; }
    TreeNode getParent() { return parent; }
    Token getToken() { return token; }
    
    void optimizeModals()
    {
        for(int i = 0; i < modalArray.size() - 1; i++)
        {
            Modal modalFirst = (Modal)modalArray.get( i );
            Modal modalSecond = (Modal)modalArray.get( i + 1 );
            if( modalFirst == Modal.Not )
            {
                if( modalSecond != Modal.Not )
                {
                        // Flips the modals according to
                        // De Morgans laws.
                    if( modalSecond == Modal.Box )
                        modalSecond = Modal.Diamond;
                    else
                        modalSecond = Modal.Box;
                    modalArray.set( i, modalSecond );
                    modalArray.set( i + 1, modalFirst );
                } else
                {
                    modalArray.remove( i );
                    modalArray.remove( i );
                    i--;
                }
            }            
        }
    }
    
    void setChildNodeOne( TreeNode nChildOne )
    {
        childOne = nChildOne;
        childOne.setParent( this );
    }
    void setChildNodeTwo( TreeNode nChildTwo )
    {
        childTwo = nChildTwo;
        childTwo.setParent( this );
    }
    void setLeaf( boolean nIsLeaf ) { isLeaf = nIsLeaf; }
    void setParent( TreeNode nParent ) { parent = nParent; }
    void setToken( Token nToken ) { token = nToken; }
    
    public String toString()
    {
        String output = "";
        if( this.getToken() != null )
        {
            output += this.getToken().getId().toString();
            if( this.getToken().getId() == TokenId.LABEL )
                output += ":(" + this.getToken().getLabel() + ")";
        } else
            output += "null";
        return output;
    }
}
