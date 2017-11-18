package hearc.ch.roleplay;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import hearc.ch.roleplay.hearc.ch.roleplay.perso.Player;


/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView txtLORE;
    Button btnSuivant;
    EditText pseudoEditText = null;
    String pseudo = null;
    static final String FILENAME = "save.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_menu);

        btnSuivant = (Button)findViewById(R.id.btnSuivant);
        pseudoEditText = (EditText) findViewById(R.id.editPseudo);
        txtLORE = (TextView)findViewById(R.id.txtLORE);
        btnSuivant.setOnClickListener(this);
    }

    private String getPseudoEditText()
    {
        if(pseudoEditText.getText().length() > 0)
        {
            pseudo = pseudoEditText.getText().toString();
            return pseudo;
        }
        else
        {
            return null;
        }
    }

    private void setPseudoEditText(String msg)
    {
        pseudoEditText.setText(msg);
    }

    private void setTextView(String msg){ txtLORE.setText(msg); }

    @Override
    public void onClick(View v)
    {
        if(getPseudoEditText() != null)
        {
            ReadFile r = new ReadFile();
            if(r.isNameIsInFile(this, pseudo))
            {
                setTextView("Pseudo déjà utilisé");
            }
            else
            {
                Log.e("isNameIsInFile", "no");
                Player p = new Player(pseudo);
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("Pseudo", pseudo);
                startActivity(intent);
            }
        }
        else
        {
            setTextView("Rentrez un pseudoEditText");
        }

    }


}
