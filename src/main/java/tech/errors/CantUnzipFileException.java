/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.errors;

/**
 *
 * @author myduy
 */
public class CantUnzipFileException extends Exception{

    public CantUnzipFileException(String message) {
        super(message);
    }
    
}
