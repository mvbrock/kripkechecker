/*
 * Token.java
 *
 * Created on May 6, 2006, 12:24 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kripkechecker;

/**
 *
 * @author matt
 */
public class Token {
    
    TokenId id;
    String labelValue;
    int position;
    
    TokenId getId() { return id; };
    void setId( TokenId nId ) { id = nId; }
    String getLabel() { return labelValue; }
    void setLabel( String nLabelValue ) { labelValue = nLabelValue; }
    int getPosition() { return position; }
    void setPosition( int nPosition ) { position = nPosition; }
    
    Token( TokenId nId ) {
        setId( nId );
        setLabel( "" );
        setPosition( -1 );
    }
    
    Token( TokenId nId, int nPosition ) {
        setId( nId );
        setLabel( "" );
        setPosition( nPosition );
    }
    
    Token( TokenId nId, int nPosition, String nLabelValue ) {
        setId( nId );
        setLabel( nLabelValue );
        setPosition( nPosition );
    }
}
