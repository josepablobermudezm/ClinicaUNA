/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author JORDI RODRIGUEZ
 */
public class Idioma extends Properties {
    public Idioma(String idioma){
         switch(idioma){
        case "Español":
                getProperties("Espanol.properties");
                break;
        case "Inglés":
                getProperties("Ingles.properties");
                break;
        default:
                getProperties("Espanol.properties");
    }
    }
    private void getProperties(String idioma) {
    try {
        this.load( getClass().getResourceAsStream(idioma) );
    } catch (IOException ex) {
           System.out.println(ex);
    }
}
}