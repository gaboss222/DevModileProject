package hearc.ch.roleplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import hearc.ch.roleplay.hearc.ch.roleplay.perso.Player;

/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class GameActivity extends AppCompatActivity
{

    HistoryNode actualNode;
    ArrayList<HistoryNode> hNodes;
    ArrayList<Button> buttons;
    ReadFile reader;
    TextView textDisplay;
    Player

    //TODO Créer méthode pour récupérer fichier texte test.txt
    //Puis l'ajouter dans player via une méthode

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_menu);

        Initialisation();
        DisplayNode();
    }


    public void Initialisation()
    {
        reader = new ReadFile();
        textDisplay = (TextView)findViewById(R.id.txtGameDescription);

        hNodes = new ArrayList<>();
        buttons = new ArrayList<>();
        actualNode = reader.readNode("A1.txt", this.getApplicationContext());
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

        }
        else {
            textDisplay.setText(textDisplay.getText() + actualNode.strText);
            if(actualNode.accessibleNodes != null) {
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

    }


    public void CallLoad(View v)
    {
        String strId = v.getTag().toString() + ".txt";
        LoadNode(strId);
    }

    public void LoadNode(String strId )
    {
        hNodes.add(actualNode);
        actualNode = reader.readNode(strId, this.getApplicationContext());
        DisplayNode();
    }


    public int FightBinary()
    {
        int iEnnemiPower = hNodes.size() * 3;
        if(Player.endurance > iEnnemiPower)
            return 0;
        return 1;
    }

    public int Fight()
    {
        int iEnnemiPower = hNodes.size() * 3;
        if(Player.endurance > iEnnemiPower)
            return 0;
        if(Player.life - (iEnnemiPower - Player.endurance) > 0)
            return 1;
        return 2;
    }

}
