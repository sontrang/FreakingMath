package vuonuomit.freakingmath.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vuonuomit.freakingmath.R;

public class GameOverDialog extends Dialog {
    Context context;
    ImageView img;
    TextView tvScore, tvMax;
    Button btnPlay, btnMenu;
    SharedPreferences sharedpreferences;


    public void setMenuListener(View.OnClickListener menuListener) {
        btnMenu.setOnClickListener(menuListener);
    }

    public GameOverDialog(Context context) {

        super(context);
        setContentView(R.layout.game_over);
        this.context = context;

        tvMax = (TextView) findViewById(R.id.tvMax);
        tvScore = (TextView) findViewById(R.id.tvScore);
        img = (ImageView) findViewById(R.id.image);

        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        sharedpreferences = context.getSharedPreferences("score", Context.MODE_PRIVATE);

        tvScore.setText(sharedpreferences.getInt("temp", 0) + "");
        tvMax.setText(sharedpreferences.getInt("max", 0) + "");

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.TWO");
                getContext().startActivity(intent);
                getOwnerActivity().finish();
                dismiss();
            }
        });
      /*  tgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.MAIN");
                getContext().startActivity(intent);
                getOwnerActivity().finish();
                dismiss();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getOwnerActivity().finish();
        dismiss();
    }
}