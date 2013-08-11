/*
 * Modal.java
 *
 * Created on May 6, 2006, 3:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kripkechecker;

/**
 *
 * @author matt
 */
public enum Modal {
    Box, Diamond, Not, Blank;
    
    static public Modal convertToModal( TokenId tId )
    {
        if( tId == TokenId.BOX)
            return Box;
        else
            if( tId == TokenId.DIAMOND )
                return Diamond;
            else
                if( tId == TokenId.NOT )
                    return Not;
                else
                    return Blank;
    }
}
