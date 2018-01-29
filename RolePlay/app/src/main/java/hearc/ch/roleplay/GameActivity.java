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
import android.widget.ImageView;
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
    ImageView imageView;

    TextView txtLife;
    TextView txtEndurance;
    String strLife = "Life: ";
    String strEndurance = "Endurance: ";

    final int iTimeFleeing = 3;
    final double dblDistanceFleeing = 15;

    //onCreate, Initilize components and displaying story
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        accelerometer = new Accelerometer((SensorManager)getSystemService(SENSOR_SERVICE));
        setContentView(R.layout.game_menu);
        textDisplay = (TextView)findViewById(R.id.txtGameDescription);
        txtEndurance = (TextView)findViewById(R.id.txtEndurance);
        txtLife = (TextView)findViewById(R.id.txtLife);
        imageView = (ImageView)findViewById(R.id.imageView);
        Initialization();
        DisplayNode();
    }

    //onPause
    @Override
    protected void onPause() {
        super.onPause();
        Player.actualNodes = actualNode.strId;
        FileHandler fileHandler = new FileHandler(this.getApplicationContext());
        fileHandler.savePlayer();
    }

    //Restart --> reset nodes, initialization and clear buttons
    public void Restart()
    {
        ClearButtons();
        Player.life = 65;
        Player.endurance = 50;
        setTxt(txtEndurance);
        setTxt(txtLife);
        Player.actualNodes = "A1.txt";
        Initialization();
    }

    //Initialise reader, specialNodes and buttonNodes
    public void Initialization()
    {
        reader = new FileHandler(this);
        setTxt(txtEndurance);
        setTxt(txtLife);
        textDisplay.setMovementMethod(new ScrollingMovementMethod());
        specialNodes = new ArrayList<>();
        buttonNodes = new ArrayList<>();

        if(Player.actualNodes != "")
            actualNode = reader.readNode(Player.actualNodes);
        else
            actualNode = reader.readNode("A1.txt");

        DisplayNode();
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

    //Create the buttonNodes when the game is over or the player dies
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

    //set endurance/life text with player attributes
    public void setTxt(TextView t)
    {
        if(t == txtEndurance)
            txtEndurance.setText(strEndurance + String.valueOf(Player.endurance));
        else
            txtLife.setText(strLife + String.valueOf(Player.life));
    }

    //Display text from files
    //if action (fight/run), do something
    //Create and display buttons for each textfile
    public void DisplayNode()
    {
        imageView.setImageResource(0);
        textDisplay.scrollTo(0,0);
        ClearButtons();
        String firstChar = actualNode.strText.substring(0, 1);
        if(firstChar.equals("*"))
        {
                if(actualNode.strText.contains("*Fight"))
                {
                    String strId;
                    if(actualNode.accessibleNodes.size() == 2)
                        strId = (String) actualNode.accessibleNodes.keySet().toArray()[FightBinary()];
                    else
                        strId = (String) actualNode.accessibleNodes.keySet().toArray()[Fight()];

                    CallLoad(strId);
                }
                else if(actualNode.strText.contains("*Run")) {
                    Fleeing();
                }
                else
                {
                    End();
                }

        }
        else
        {
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
        if(reader.isAttributeChanged() == 1)
            imageView.setImageResource(R.drawable.endurancepotion);
        else if(reader.isAttributeChanged() == 2)
            imageView.setImageResource(R.drawable.lifepotion);

    }


    //AXEL
    public void CallLoad(View v){CallLoad(v.getTag().toString());}

    //AXEL
    public void CallLoad(String strId)
    {
        LoadNode(strId + ".txt");
    }

    //AXEL
    public void LoadNode(String strId)
    {
        Player.actualNodes = strId;
        Player.nbNode++;
        actualNode = reader.readNode(strId);
        DisplayNode();
    }

    //if player is dead
    public void Death()
    {
        textDisplay.setText(textDisplay.getText() + "\n Vous êtes mort");
        Player.life = 0;
        setTxt(txtLife);
        CreateEndButton();

    }

    //At the end of the story
    public void End()
    {
        textDisplay.setText("Vous avez fini le jeu, recommencez pour voir ce que les autres chemins de votre histoire vous résèrve.");
        CreateEndButton();
    }

    //FIGHT AXEL
    public int FightBinary()
    {
        int iEnnemiPower = Player.nbNode * 3;
        if(Player.endurance > iEnnemiPower)
            return 0;
        return 1;
    }

    //FIGHT AXEL
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

    //Accelerometer function
    public void Fleeing()
    {
        textDisplay.setText("Courrez aussi vite que vous le pouvez !");
        accelerometer.onResume();
        int iExit = 0;
        new CountDownTimer(iTimeFleeing*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                accelerometer.onPause();
                ComputeFleeingDistance();
            }
        }.start();
    }

    //AXEL
    public void ComputeFleeingDistance()
    {
        ArrayList<Double> lAcceleration = accelerometer.lAcceleration;
        ArrayList<Double> lVelocity = new ArrayList<>();
        ArrayList<Double> lDistance = new ArrayList<>();

        double accelerationSum = 0.0;
        for(Double acc : lAcceleration)
            accelerationSum += acc;


        double accelerationAvg = accelerationSum / lAcceleration.size();
        for(int i = 0; i <= iTimeFleeing; i++)
        {
            if(i == 0)
            {
                lVelocity.add(0.0);
                lDistance.add(0.0);
            }
            else
            {
                lVelocity.add(lVelocity.get(i-1) + accelerationAvg);
                lDistance.add(lDistance.get(i-1) + lVelocity.get(i));
            }
        }
        double distance = lDistance.get(lDistance.size()-1);
        if(distance >= dblDistanceFleeing)
            CallLoad(actualNode.accessibleNodes.keySet().toArray()[0].toString());
        else
            CallLoad(actualNode.accessibleNodes.keySet().toArray()[1].toString());
    }

}
