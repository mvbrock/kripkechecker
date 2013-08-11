/*
 * Traverse.java
 *
 * Created on May 7, 2006, 11:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kripkechecker;

import java.io.*;
import java.util.Vector;

/**
 *
 * @author matt
 */
public class Traverse {
    
    private static BufferedReader stdin = new BufferedReader( 
                                new InputStreamReader( System.in ) );
    TreeNode node;
    /** Creates a new instance of Traverse */
    public Traverse( TreeNode nNode ) {
        node = nNode;
    }
    
        
    public void traverse() throws IOException
    {
        System.out.print("(");
        if( node.getParent() != null )
            System.out.print(node.getParent());
        System.out.print(")->["+node+"] : ");
        
        Vector modalArray = node.getModalArray();
        for(int i = 0; i < modalArray.size(); i++ )
            System.out.print((Modal)modalArray.get(i) + ",");
        System.out.println("");
        
        System.out.print("(");
        if( node.getChildOne() != null )
            System.out.print(node.getChildOne());
        System.out.print("), (");
        if( node.getChildTwo() != null )
            System.out.print(node.getChildTwo());
        System.out.println(")");
        
        
        System.out.print(":");
        int val = Integer.parseInt(stdin.readLine());
        switch( val )
        {
            case 8:
                if( node.getParent() != null )
                    node = node.getParent();
                traverse();
                break;
            case 4:
                if( node.getChildOne() != null )
                    node = node.getChildOne();
                traverse();
                break;   
            case 6:
                if( node.getChildTwo() != null )
                    node = node.getChildTwo();
                traverse();
                break;
            case 0:
                break;
        }
        
    }
}
