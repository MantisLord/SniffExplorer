/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc.sniffexplorer.exceptions;

/**
 *
 * @author chaouki
 */
public class ParseException extends Exception {
    private String customMessage;

    public ParseException() {
        super();
    }

    public ParseException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }
}
