package hearc.ch.roleplay;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import hearc.ch.roleplay.hearc.ch.roleplay.perso.Player;

/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class GameActivity extends AppCompatActivity
{


    HistoryNode actualNode;

    ArrayList<Button> buttons;
    FileHandler reader;
    TextView textDisplay;
    Accelerometer accelerometer;

    TextView txtLife;
    TextView txtEndurance;
    String strLife = "Life: ";
    String strEndurance = "Endurance: ";
    Player p;

    //TODO Créer méthode pour récupérer fichier texte test.txt
    //Puis l'ajouter dans player via une méthode

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_menu);
        accelerometer = new Accelerometer();
        Initialisation();
        DisplayNode();
    }


    public void Initialisation()
    {
        reader = new FileHandler();
        textDisplay = (TextView)findViewById(R.id.txtGameDescription);
        txtEndurance = (TextView)findViewById(R.id.txtEndurance);
        txtLife = (TextView)findViewById(R.id.txtLife);
        txtEndurance.setText(strEndurance + String.valueOf(Player.endurance));
        txtLife.setText(strLife + String.valueOf(Player.life));


        buttons = new ArrayList<>();
        if(Player.actualNodes != "")
            actualNode = reader.readNode(Player.actualNodes+".txt", this.getApplicationContext());
        else
            actualNode = reader.readNode("A1.txt", this.getApplicationContext());
        textDisplay.setText(actualNode.strText);
    }

    public void CreateButton(String tag, String val)
    {
        Button myButton = new Button(this);
        myButton.setText(val);
        myButton.setTag(tag);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallLoad(v);
            }
        });
        buttons.add(myButton);
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }


    public void DisplayNode()
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLayout);
        for(Button b : buttons)
            ll.removeView(b);
        buttons.clear();
        String firstChar = actualNode.strText.substring(0, 1);
        if(firstChar.equals("*")) {
                if(actualNode.strText == "*Fight")
                {
                    String strId;
                    if(actualNode.accessibleNodes.size() == 2)
                        strId = (String) actualNode.accessibleNodes.keySet().toArray()[FightBinary()];
                    else
                        strId = (String) actualNode.accessibleNodes.keySet().toArray()[Fight()];
                }
                else if(actualNode.strText == "*Run") {
                    if (actualNode.strText.contains("*End")) {
                        End();
                    }
                }

        }
        else {
            textDisplay.setText(actualNode.strText);
            if(actualNode.accessibleNodes != null)
            {
                for (Map.Entry<String, String> entry : actualNode.accessibleNodes.entrySet()) {
                    CreateButton(entry.getKey(), entry.getValue());
                }
            }
            else
                Death();
        }
    }

    public void Death()
    {
        textDisplay.setText("Vous êtes mort");
    }

    public void End()
    {
        textDisplay.setText("Vous avez fini");
    }

    public int Fleeing()
    {
        accelerometer.onResume();
        int iExit = 0;
        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();



        accelerometer.onPause();
        ArrayList<Double> lAcceleration = accelerometer.lAcceleration;
        ArrayList<Double> lVelocity = new ArrayList<>();
        ArrayList<Double> lDistance = new ArrayList<>();


        for(int i = 0; i < lAcceleration.size(); i++)
        {
            if(i == 0)
            {
                lVelocity.add(0.0);
                lDistance.add(0.0);
            }
            else
            {
                lVelocity.add(lVelocity.get(i-1) + lAcceleration.get(i));
                lDistance.add(lDistance.get(i-1) + lVelocity.get(i));
            }
        }
        double distance = lDistance.get(lDistance.size()-1);
        if(distance >= 10)
            return 1;
        return 0;
    }


    public void CallLoad(View v)
    {
        String strId = v.getTag().toString() + ".txt";
        LoadNode(strId);
    }

    public void LoadNode(String strId)
    {
        Player.actualNodes = strId;
        Player.nbNode++;
        actualNode = reader.readNode(strId, this.getApplicationContext());
        DisplayNode();
    }


    public int FightBinary()
    {
        int iEnnemiPower = Player.nbNode * 3;
        if(Player.endurance > iEnnemiPower)
            return 0;
        return 1;
    }

    public int Fight()
    {
        int iEnnemiPower = Player.nbNode * 3;
        if(Player.endurance > iEnnemiPower)
            return 0;
        if(Player.life - (iEnnemiPower - Player.endurance) > 0)
        {
            Player.life = (Player.life - (iEnnemiPower - Player.endurance));
            txtLife.setText(strLife + String.valueOf(Player.life));
            return 1;
        }
        return 2;
    }

}
