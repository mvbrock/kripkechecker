/*
 * World.java
 *
 * Created on May 6, 2006, 11:55 AM
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
public class World {
    
    Hashtable relationHash;
    Vector relationArray;
    Vector labelArray;
    String name;
    /** Creates a new instance of World */
    public World( String nName ) {
        relationHash = new Hashtable();
        relationArray = new Vector();
        labelArray = new Vector();
        setName( nName );
    }
    
    public void addLabel( String label )
    {
        labelArray.add( label );
    }
    public void addRelation( World world )
    {
            // If we haven't added the world before, then add it to the array.
        if( relationHash.put(world.toString(), world) == null )
            relationArray.add(world);
    }
    
    public String getLabel( int idx ) { return (String)labelArray.get(idx); }
    public int getLabelCount() { return labelArray.size(); }
    public World getRelation( int idx ) { return (World)relationArray.get(idx); }
    public int getRelationCount() { return relationArray.size(); }
    
    public boolean matchLabel( String label )
    {
        for( int i = 0; i < labelArray.size(); i++ )
            if( label.equals((String)labelArray.get(i)))
                return true;
        return false;
    }
    
    public void setLabelArray(Vector labelArray) {
        this.labelArray = labelArray;
    }
    
    public void setName( String nName ) { name = nName; };
    
    public String toString()
    {
        return name;
    }
}
