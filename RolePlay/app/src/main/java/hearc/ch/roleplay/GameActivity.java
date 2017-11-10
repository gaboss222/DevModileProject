package hearc.ch.roleplay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class GameActivity extends AppCompatActivity
{

    private Button btnRep1, btnRep2, btnRep3;
    private TextView txtGameDescription = null;
    private TextView txtGameBienvenu = null;
    private String pseudo = null;
    private String filename = null;

    //TODO Créer méthode pour récupérer fichier texte test.txt
    //Puis l'ajouter dans player via une méthode

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_menu);

        btnRep1 = (Button)findViewById(R.id.btnRep1);
        btnRep2 = (Button)findViewById(R.id.btnRep2);
        btnRep3 = (Button)findViewById(R.id.btnRep3);
        txtGameDescription = (TextView)findViewById(R.id.txtGameDescription);
        txtGameDescription.setMovementMethod(new ScrollingMovementMethod());
        pseudo = getIntent().getStringExtra("Pseudo");
        btnRep1.setOnClickListener(clickRep1);
    }


    private void test(String msg)
    {
        txtGameDescription.setText(msg);
    }
    private View.OnClickListener clickRep1 = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            test(pseudo);
        }
    };

    private View.OnClickListener clickRep2 = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //On démarre une nouvelle partie

        }
    };

    private View.OnClickListener clickRep3 = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //On démarre une nouvelle partie

        }
    };

}
