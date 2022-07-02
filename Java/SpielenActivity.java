package com.adt.blackjack;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;



public class SpielenActivity extends ActionBarActivity {
    private int spielNr=0;
    private int spielerPunkte=0;
    private int croupierPunkte=0;
    private int spielerGewinne=0;
    private int croupierGewinne=0;
    private int zaehler=3;          // Ist die Id, des als nächstes zu verwendeten Kartenimages   
    private TextView spielNrView;
    private TextView spielerPunkteView;
    private TextView croupierPunkteView;
    private Button karteZiehenButton, aufhoerenButton, spielStartenButton, ergebnissButton;

    private int resId = 0;
    private int imageViewId=0;
    private ImageView img;

    private Kartenstapel kartenstapel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielen);

        karteZiehenButton = (Button)findViewById(R.id.bKartenZiehen);
        aufhoerenButton = (Button)findViewById(R.id.bAufhoeren);
        spielStartenButton = (Button)findViewById(R.id.bSpielStarten);
        ergebnissButton = (Button)findViewById(R.id.bErgebnisseAnzeigen);

        spielNrView= (TextView)findViewById(R.id.tvSpielZaehler);
        spielerPunkteView = (TextView)findViewById(R.id.tvPunkteZaehlerSpieler);
        croupierPunkteView = (TextView)findViewById(R.id.tvPunkteZaehlerCroupier);

        for(int i = 1; i <= 16; i++)  // Übergibt allen ImageViews eine unsichtbare Karte, da noch keine Karten gezogen wurden
        {
            resId = getResources().getIdentifier("kb", "drawable", getPackageName());
            imageViewId = getResources().getIdentifier("imageView" + i, "id", getPackageName());
            img=(ImageView)findViewById(imageViewId);
            img.setImageResource(resId);
        }
    }

    public void spielStarten(View v) {
        //Ausgangsspielfeld-Daten übernehmen:
        croupierPunkte = 0;
        spielerPunkte = 0;
        spielNr++;
        zaehler=3;

        spielNrView.setText(Integer.toString(spielNr));
        spielerPunkteView.setText(Integer.toString(spielerPunkte));
        croupierPunkteView.setText(Integer.toString(croupierPunkte));

        for(int i = 1; i <= 16; i++)  // Übergibt allen ImageViews eine unsichtbare Karte, da noch keine Karten gezogen wurden
        {
            resId = getResources().getIdentifier("kb", "drawable", getPackageName());
            imageViewId = getResources().getIdentifier("imageView" + i, "id", getPackageName());
            img=(ImageView)findViewById(imageViewId);
            img.setImageResource(resId);
        }

        // Bei jedem neuen Spielstart muss der Kartenstapel neu erstellt oder
        // gemischt werden.
        kartenstapel = new Kartenstapel();
        // Die ersten drei Karten werden abwechselnd an Spieler und Gegner verteilt

        spielerPunkte=karteGeben(kartenstapel,1,spielerPunkte);
        spielerPunkteView.setText(Integer.toString(spielerPunkte));

        croupierPunkte=karteGeben(kartenstapel,9,croupierPunkte);
        croupierPunkteView.setText(Integer.toString(croupierPunkte));

        spielerPunkte=karteGeben(kartenstapel,2,spielerPunkte);
        spielerPunkteView.setText(Integer.toString(spielerPunkte));

        // Die vierte verdeckte Karte in das ImageView10 legen:
        resId = getResources().getIdentifier("back01" , "drawable", getPackageName());
        imageViewId= getResources().getIdentifier("imageView"+ 10 , "id", getPackageName());
        img= (ImageView)findViewById(imageViewId);
        img.setImageResource(resId);

        // Beim Spielstart kann der Spieler sofort gewinnen,
        // wenn er mit den ersten zwei Karten sofort 21 Punkte hat:

        if(spielerPunkte == 21 )
        {
            Toast.makeText(this,"Du gewinnst das Spiel!", Toast.LENGTH_LONG).show();
            spielerGewinne++;
        }
        else {
            // Wechselt die Sichtbarkeit der Button, so dass nun "Karte ziehen" und "Aufhoeren" zu sehen sind
            spielStartenButton.setVisibility(View.INVISIBLE);
            ergebnissButton.setVisibility(View.INVISIBLE);
            karteZiehenButton.setVisibility(View.VISIBLE);
            aufhoerenButton.setVisibility(View.VISIBLE);
        }
    }

    public void karteZiehen(View button)
    {
        if (spielerPunkte < 21 && zaehler <= 8){
            spielerPunkte=karteGeben(kartenstapel,zaehler,spielerPunkte);
            spielerPunkteView.setText(Integer.toString(spielerPunkte));
            zaehler++;
            if (spielerPunkte >21)
            {
                // Der Spieler verliert wenn er mehr als 21 Punkte zieht.
                Toast.makeText(this,"Dein Gegner gewinnt das Spiel!", Toast.LENGTH_LONG).show();
                croupierGewinne++;
                spielStartenButton.setVisibility(View.VISIBLE);
                ergebnissButton.setVisibility(View.VISIBLE);
                karteZiehenButton.setVisibility(View.INVISIBLE);
                aufhoerenButton.setVisibility(View.INVISIBLE);
            }
            else if (spielerPunkte == 21){
                // Der Spieler gewinnt falls er genau 21 Punkte zieht.
                Toast.makeText(this,"Du gewinnst das Spiel!", Toast.LENGTH_LONG).show();
                spielerGewinne++;
                spielStartenButton.setVisibility(View.VISIBLE);
                ergebnissButton.setVisibility(View.VISIBLE);
                karteZiehenButton.setVisibility(View.INVISIBLE);
                aufhoerenButton.setVisibility(View.INVISIBLE);
            }
        }
    }
    
    public void aufhoeren(View button)
    {
        // Der Croupier ist nun dran, die nächste Karte muss auf
        // ImageView iV10 gelegt werden, daher zaehler=10
        zaehler = 10;
        
        // Thread sorgt dafuer, dass der Zug jeder Karte des Croupiers um 1 Sekunde verzoegert wird.
        new Thread(){
            public void run(){
                while (croupierPunkte <17)
                {
                    try {
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run()
                            {
                                croupierPunkte=karteGeben(kartenstapel,zaehler,croupierPunkte);
                                croupierPunkteView.setText(Integer.toString(croupierPunkte));
                                zaehler++;
                                if (croupierPunkte>=17)
                                {
                                    spielAuswerten();
                                }
                            }
                        });
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        
        spielStartenButton.setVisibility(View.VISIBLE);
        ergebnissButton.setVisibility(View.VISIBLE);
        karteZiehenButton.setVisibility(View.INVISIBLE);
        aufhoerenButton.setVisibility(View.INVISIBLE);
    }
    public int karteGeben(Kartenstapel st, int n, int erg){
        Karte k;
        k = (Karte)st.gibKartenstapel().top();
        st.gibKartenstapel().pop();
        int nr = k.getKartennummer();
        erg = erg + k.kartenWert();
        resId = getResources().getIdentifier("ka_"+nr , "drawable", getPackageName());
        imageViewId= getResources().getIdentifier("imageView"+ n , "id", getPackageName());
        img=(ImageView) findViewById(imageViewId);
        img.setImageResource(resId);
        return erg;
    }
    public void spielAuswerten()
    {
        // Vergleich der Ergebnisse von Spieler und Croupier:
        if (croupierPunkte > spielerPunkte&& croupierPunkte <= 21)
        {
            croupierGewinne++;
            Toast.makeText(this,"Dein Gegner gewinnt das Spiel!", Toast.LENGTH_LONG).show();
        }
        else
        {
            spielerGewinne++;
            Toast.makeText(this,"Du gewinnst das Spiel!", Toast.LENGTH_LONG).show();
        }
    }

    public void ergebnisseAnzeigen(View v) {
        // Uebertraegt Ergebnisse an Ergebnis-Activity
        final Intent intent = new Intent(this,ErgebnisActivity.class);
        intent.putExtra("ANZSPIELE",""+spielNr);
        intent.putExtra("COGEWINNE",""+croupierGewinne);
        intent.putExtra("SPGEWINNE",""+spielerGewinne);
        startActivity(intent);
    }

}
