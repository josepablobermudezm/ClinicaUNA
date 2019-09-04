/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicauna.util;

/**
 *
 * @author Carlos Olivares
 */
public class generadorContrasennas {
    
    public String NUMEROS = "0123456789";

    public String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    public String ESPECIALES = "ñÑ";
    private static generadorContrasennas INSTANCE = null;
    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (generadorContrasennas.class) {
                if (INSTANCE == null) {
                    INSTANCE = new generadorContrasennas();
                }
            }
        }
    }

    public static generadorContrasennas getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    public String getPinNumber() {
        return getPassword(NUMEROS, 4);
    }

    public String getPassword() {
        return getPassword(8);
    }

    public String getPassword(int length) {
        return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
    }

    public String getPassword(String key, int length) {
        String pswd = "";

        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }

        return pswd;
    }
}
