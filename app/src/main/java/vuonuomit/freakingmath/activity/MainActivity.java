package vuonuomit.freakingmath.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Random;

import vuonuomit.freakingmath.R;
import vuonuomit.freakingmath.until.SoundUntil;

public class MainActivity extends Activity {

    Button btnTrue, btnFalse;
    ToggleButton tgSound;
    private int a, b, c;
    RelativeLayout activity_main;
    TextView tvScoreTemp;
    TextView tvMath;
    SharedPreferences sharedpreferences;
    ProgressBar prBar;
    MyCountDownTimer countDownTimer;
    SharedPreferences.Editor editor;
    Random random = new Random();
    AudioManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.visi_in, 0);
        int[] randomColor = getResources().getIntArray(R.array.randomColor);
        int randomBackgroundColor = randomColor[new Random().nextInt(randomColor.length)];
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
        activity_main.setBackgroundColor(randomBackgroundColor);
        prBar = (ProgressBar) findViewById(R.id.prBar);
        tvMath = (TextView) findViewById(R.id.tvMath);
        tvScoreTemp = (TextView) findViewById(R.id.tvScoreTemp);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        tgSound = (ToggleButton) findViewById(R.id.tgSound);
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        sharedpreferences = getSharedPreferences("score", MODE_PRIVATE);
        editor = sharedpreferences.edit();

        setMath();

        btnTrue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleResult(true);
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleResult(false);
            }
        });

        tgSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    manager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                } else {
                    manager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                }
            }
        });
    }

    private void setMath() {
        a = random.nextInt(12) + 1;
        b = random.nextInt(12) + 1;
        c = random.nextInt(3) + (a + b);
        tvMath.setText(a + "+" + b + "\n=" + c);
        turnOffAllButton(false);
        tvMath.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.swipe_in_left));
    }

    private void handleResult(boolean isTrueButton) {

        turnOffAllButton(true);
        if ((c == a + b && isTrueButton) || (c != a + b && !isTrueButton)) {
//            tvMath.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.swipe_out_left));
            SoundUntil.hexat(getApplicationContext(), SoundUntil.TRUE);
            editor.putInt("temp", sharedpreferences.getInt("temp", 0) + 1).commit();
            tvScoreTemp.setText("" + sharedpreferences.getInt("temp", 0));
            setMath();
            restartCountDown();
        } else {
            activity_main.startAnimation(new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.vibrate));
            cancelCountDown();
            if (sharedpreferences.getInt("temp", 0) > sharedpreferences.getInt("max", 0)) {
                editor.putInt("max", sharedpreferences.getInt("temp", 0)).commit();
            }
            SoundUntil.hexat(getApplicationContext(), SoundUntil.FAIL);
            showDialog(false);
            editor.putInt("temp", 0).commit();
        }
    }

    private void turnOffAllButton(boolean turnOff) {
        if (turnOff) {
            btnTrue.setClickable(false);
            btnFalse.setClickable(false);
        } else {
            btnTrue.setClickable(true);
            btnFalse.setClickable(true);
        }
    }


    private void showDialog(boolean isTimeout) {
        final GameOverDialog dialog = new GameOverDialog(new ContextThemeWrapper(this, R.style.Dialog));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
        ImageView img = (ImageView) dialog.findViewById(R.id.image);
        if (isTimeout) {
            img.setImageResource(R.drawable.game_over_timer_bg);
        } else {
            img.setImageResource(R.drawable.game_over_bg);
        }
        dialog.setMenuListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void cancelCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void restartCountDown() {
        cancelCountDown();
        countDownTimer = new MyCountDownTimer(1000, 1);
        countDownTimer.start();
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) millisUntilFinished / 10;
            prBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            prBar.setProgress(0);
            SoundUntil.hexat(getApplicationContext(), SoundUntil.FAIL);
            showDialog(true);
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
