package hearc.ch.roleplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    //onCreate, initialize button/edittext/textview and waiting for a click on the button
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_menu);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        btnSuivant = (Button)findViewById(R.id.btnSuivant);
        pseudoEditText = (EditText) findViewById(R.id.editPseudo);
        txtLORE = (TextView)findViewById(R.id.txtLORE);
        txtLORE.setText("Bonjour jeune aventurier, prépare toi à vivre une aventure...");
        btnSuivant.setOnClickListener(this);
    }

    //check if pseudo is empty or not
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

    //Check if pseudo is existing and launch new game
    @Override
    public void onClick(View v)
    {
        if(getPseudoEditText() != null)
        {
            FileHandler r = new FileHandler(this);
            if(r.isNameInFile(pseudo))
            {
                setTextView("Le pseudo est déjà utilisé");
            }
            else
            {
                Player.pseudo = pseudo;
                Player.endurance = 50;
                Player.life = 65;
                Intent intent = new Intent(this, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Pseudo", pseudo);
                startActivity(intent);
                r.savePlayer();
            }
        }
        else
        {
            setTextView("Rentrez un pseudo");
        }

    }


}
