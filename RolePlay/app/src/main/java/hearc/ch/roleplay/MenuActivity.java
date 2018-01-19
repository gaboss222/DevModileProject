package hearc.ch.roleplay;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnNewGame;
    Button btnLoadGame;
    Button btnQuitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        btnNewGame = (Button)findViewById(R.id.btnNewGame);
        btnLoadGame = (Button)findViewById(R.id.btnLoadGame);
        btnQuitGame = (Button)findViewById(R.id.btnQuitGame);


        btnQuitGame.setOnClickListener(clickListenerBtnQuitGame);
        btnNewGame.setOnClickListener(clickListenerBtnNewGame);
        btnLoadGame.setOnClickListener(clickListenerBtnLoadGame);

    }

    private View.OnClickListener clickListenerBtnNewGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //On démarre une nouvelle introduction
            Intent intent = new Intent(getApplicationContext(), IntroductionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    private View.OnClickListener clickListenerBtnLoadGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    };
    private View.OnClickListener clickListenerBtnQuitGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            //On ferme l'appli
            finish();
            System.exit(0);
        }
    };
}
