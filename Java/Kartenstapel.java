package com.adt.blackjack;
import java.util.*;

public class Kartenstapel {
    private Stack kartenstapel;
    public Kartenstapel()
    {
        kartenstapel = new Stack();
        kartenMischen();
    }
    public Stack gibKartenstapel()
    {
        return kartenstapel;
    }
    public void kartenMischen()     // Mischt nach dem Prinzip "Durchwühlen"
    {
        int kartennummern[] = new int[52];
    	Random r = new Random();
    	
    	for(int i = 0; i < 52; i++)     // Erstellt Integer Array mit allen 52 Kartennummern
    	{
    		kartennummern[i] = i;
    	}
    	
    	for(int i = 0; i < 52; i++)     // Tauscht jedes Array-Feld einmal mit einem zufaelligen anderen 
    	{
    		int index = r.nextInt(52);
    		
    		int swap = kartennummern[i];
    		kartennummern[i] = kartennummern[index];
    		kartennummern[index] = swap;
    	}
    
    	for(int i = 0; i < 52; i++)     // Erstellt Kartenstapel aus dem gemischten Kartennumern-Array
    	{
    		kartenstapel.push(new Karte(kartennummern[i]));
    	}
    }
}
