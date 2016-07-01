package vuonuomit.freakingmath.until;

import android.content.Context;
import android.media.MediaPlayer;

import vuonuomit.freakingmath.R;

public class SoundUntil {
    public static int FAIL= R.raw.fail;
    public static int TRUE= R.raw.scored;

public static void hexat(Context context, int sound){
    final MediaPlayer mediaPlayer=MediaPlayer.create(context,sound);
    mediaPlayer.start();
    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mediaPlayer.release();
        }
    });
}
}
