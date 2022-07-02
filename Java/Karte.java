package com.adt.blackjack;

public class Karte {
    private int kartennummer;       // Jede Karte hat eine eigene Nummer von 0-51
    private String farbe;
    private int wert;
    private String bildwert;    

    public Karte(int kartennummer)
    {
        this.kartennummer = kartennummer;
        wert = kartenWert();
        farbe = kartenFarbe();
        bildwert = wertInWorten();
    }

    public int kartenWert()         // Ermittelt kartenWert aus der Kartennummer. Karten 2-10 haben angegebene Zahl als
    {                               // Wert, Bilder haben Wert 10 und Asse haben Wert 11.
        int w = kartennummer % 13;
        if (w<=8)
            return w+2;
        else
        if (w==12)
            return 11;
        else
            return 10;
    }
    public String kartenFarbe()
    {
        int x = kartennummer /13;

        switch (x)
        {
            case 0:
                farbe = "Karo";
                break;
            case 1:
                farbe = "Herz";
                break;
            case 2:
                farbe = "Pik";
                break;
            case 3:
                farbe = "Kreuz";
                break;
            default:
                System.out.println("Karte gibt es nicht!");
        }
        return farbe;
    }
    public String wertInWorten()        // Ermittelt Kartenwert in Worten: z.B "Bude" oder "Sieben" 
    {
        int w = kartennummer % 13;
        switch (w)
        {
            case 0:
                return bildwert = "zwei";
            case 1:
                return bildwert = "drei";
            case 2:
                return bildwert = "vier";
            case 3:
                return bildwert = "fünf";
            case 4:
                return bildwert = "sechs";
            case 5:
                return bildwert = "sieben";
            case 6:
                return bildwert = "acht";
            case 7:
                return bildwert = "neun";
            case 8:
                return bildwert = "zehn";
            case 9:
                return bildwert = "Bube";
            case 10:
                return bildwert = "Dame";
            case 11:
                return bildwert = "König";
            case 12:
                return bildwert = "Ass";
            default:
                System.out.println("Karte gibt es nicht!");
        }
        return bildwert;
    }
    public int getKartennummer()
    {
        return kartennummer;
    }

}
