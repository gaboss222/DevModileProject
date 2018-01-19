package hearc.ch.roleplay;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import hearc.ch.roleplay.hearc.ch.roleplay.perso.Player;

/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class GameActivity extends AppCompatActivity
{


    HistoryNode actualNode;

    ArrayList<Button> buttonNodes;
    ArrayList<String> specialNodes;
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
        accelerometer = new Accelerometer((SensorManager)getSystemService(SENSOR_SERVICE));
        setContentView(R.layout.game_menu);
        Initialization();
        DisplayNode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileHandler fileHandler = new FileHandler();
        fileHandler.savePlayer(this, Player.pseudo);
    }

    public void Restart()
    {
        ClearButtons();
        Initialization();
    }

    public void Initialization()
    {
        reader = new FileHandler();
        textDisplay = (TextView)findViewById(R.id.txtGameDescription);
        txtEndurance = (TextView)findViewById(R.id.txtEndurance);
        txtLife = (TextView)findViewById(R.id.txtLife);
        setTxt(txtEndurance);
        setTxt(txtLife);


        specialNodes = new ArrayList<>();
        buttonNodes = new ArrayList<>();
        if(Player.actualNodes != "")
            actualNode = reader.readNode(Player.actualNodes+".txt", this.getApplicationContext());
        else
            actualNode = reader.readNode("A1.txt", this.getApplicationContext());
        textDisplay.setText(actualNode.strText);
    }

    //Create buttonNodes from nodes
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
        buttonNodes.add(myButton);
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
    }

    //Create the buttonNodes when the game is over or the player die
    public void CreateEndButton()
    {
        Button btnRestart = new Button(this);
        Button btnQuit = new Button(this);
        btnRestart.setText("Restart");
        btnQuit.setText("Menu");
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart();
            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        buttonNodes.add(btnRestart);
        buttonNodes.add(btnQuit);
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(btnRestart,lp);
        ll.addView(btnQuit, lp);
    }

    //Remove the buttonNodes
    public void ClearButtons()
    {
        LinearLayout ll = (LinearLayout)findViewById(R.id.buttonLayout);
        for(Button b : buttonNodes)
            ll.removeView(b);
        buttonNodes.clear();
    }

    public void setTxt(TextView t)
    {
        if(t == txtEndurance)
        {
            txtEndurance.setText(strEndurance + String.valueOf(Player.endurance));
        }
        else
        {
            txtLife.setText(strLife + String.valueOf(Player.life));
        }
    }

    public void DisplayNode()
    {
        textDisplay.scrollTo(0,0);
        ClearButtons();
        String firstChar = actualNode.strText.substring(0, 1);
        if(firstChar.equals("*")) {
                if(actualNode.strText.contains("*Fight"))
                {
                    String strId;
                    if(actualNode.accessibleNodes.size() == 2)
                        strId = (String) actualNode.accessibleNodes.keySet().toArray()[FightBinary()];
                    else
                        strId = (String) actualNode.accessibleNodes.keySet().toArray()[Fight()];
                }
                else if(actualNode.strText.contains("*Run")) {
                    textDisplay.setText("Fuyez pauvre fou !!!");
                    Fleeing();
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
        setTxt(txtEndurance);
        setTxt(txtLife);
    }

    public void Death()
    {
        textDisplay.setText(textDisplay.getText() + "\n Vous êtes mort");
        CreateEndButton();

    }

    public void End()
    {
        textDisplay.setText("Vous avez fini le jeu, recommencez pour voir ce que les autres chemins de votre histoire vous résèrve.");
        CreateEndButton();
    }

    public void CallLoad(View v){CallLoad(v.getTag().toString());}

    public void CallLoad(String strId){LoadNode(strId + ".txt");}

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

    public void Fleeing()
    {
        accelerometer.onResume();
        int iExit = 0;
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                accelerometer.onPause();
                ComputeFleeingDistance();
            }
        }.start();
    }

    public void ComputeFleeingDistance()
    {
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
            CallLoad(actualNode.accessibleNodes.keySet().toArray()[0].toString());
        else
            CallLoad(actualNode.accessibleNodes.keySet().toArray()[1].toString());
    }

}
