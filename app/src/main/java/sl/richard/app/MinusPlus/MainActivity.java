package sl.richard.app.MinusPlus;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    //changeable
    final static int TIME = 30 * 1000; //in milliseconds
    //------


    int playButtonState = 0; // 0=start, 1=stop, 2=retry
    String[] playButtonStateName = {"start","stop","retry"};


    QuestionSystem questionSystem;
    ScoreSystem scoreSystem;

    CountDownTimer countDownTimer;

    //Views
    TextView timeLeft;
    TextView score;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    TextView answer4;
    TextView problem;
    TextView aniBackground;

    Button playButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionSystem = new QuestionSystem();
        scoreSystem = new ScoreSystem();


        MobileAds.initialize(getApplicationContext(),"ca-app-pub-xxxxxxx");
        //views
        AdView adView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("3AE95FC9A8447EFC74AF14E86DB29DA1").build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("xxxxx").build();
        assert adView != null;
        adView.loadAd(adRequest);


        playButton = (Button)findViewById(R.id.playButton);
        timeLeft = (TextView) findViewById(R.id.timeLeft);
        answer1 = (TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);
        answer3 = (TextView) findViewById(R.id.answer3);
        answer4 = (TextView) findViewById(R.id.answer4);
        problem = (TextView) findViewById(R.id.problem);
        score = (TextView) findViewById(R.id.score);
        aniBackground = (TextView) findViewById(R.id.aniBackground);



    }

    /*
    * When play button clicked
    */
    public void playButton(View view){
        if((playButtonState == 0) || (playButtonState==2)) {

            playButtonState = 1;
            playButton.setText(playButtonStateName[1]);
            playButton.setBackgroundColor(Color.parseColor("#f25f5c"));
            //nextRound();

            countDownTimer = new CountDownTimer(TIME + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) * 60;

                    //Log.i("r89-minutes var: ", Long.toString(minutes));

                    String time = String.format("%02d:%02d", minutes, seconds);
                    timeLeft.setText(time);

                }

                @Override
                public void onFinish() {
                    timeLeft.setText("00:00");
                    playButtonState = 2;
                    playButton.setText(playButtonStateName[2]);
                    playButton.setBackgroundColor(Color.parseColor("#70c1b3"));
                }

            }.start();
            scoreSystem.start();
            //score.setText(scoreSystem.getScore());
            nextRound(true);


        }else{ //if playbutton = stop
            countDownTimer.cancel();
            countDownTimer = null;
            playButtonState = 2;
            playButton.setText(playButtonStateName[2]);
            playButton.setBackgroundColor(Color.parseColor("#70c1b3"));
        }


    }

    public void button(View view){
        if(playButtonState == 1) {
            //Log.i("richarder", view.getTag().toString());

            if  (view.getTag().toString() == "right") {
                Log.i("richarder", "yes");
                scoreSystem.add(1);

                animate(1);
                nextRound(false);


            }else{
                animate(0);
                nextRound(false);
            }

        }
    }

    public void nextRound(boolean firstRound){


        questionSystem.generateQuestion();
        problem.setText(questionSystem.getQuestion());
        List<Integer> answers = questionSystem.getAnswers();
        Log.i("r89-", answers.get(1).toString());
        Random r = new Random();
        int random = r.nextInt(4);
        if(random==0) {
            answer1.setText(answers.get(0).toString());
            answer1.setTag("right");
            answer2.setText(answers.get(1).toString());
            answer2.setTag("not");
            answer3.setText(answers.get(2).toString());
            answer3.setTag("not");
            answer4.setText(answers.get(3).toString());
            answer4.setTag("not");

        }else if(random==1) {
            answer1.setText(answers.get(1).toString());
            answer1.setTag("not");
            answer2.setText(answers.get(0).toString());
            answer2.setTag("right");
            answer3.setText(answers.get(2).toString());
            answer3.setTag("not");
            answer4.setText(answers.get(3).toString());
            answer4.setTag("not");

        }else if(random==2) {
            answer1.setText(answers.get(3).toString());
            answer1.setTag("not");
            answer2.setText(answers.get(1).toString());
            answer2.setTag("not");
            answer3.setText(answers.get(0).toString());
            answer3.setTag("right");
            answer4.setText(answers.get(2).toString());
            answer4.setTag("not");

        }else {
            answer1.setText(answers.get(3).toString());
            answer1.setTag("not");
            answer2.setText(answers.get(2).toString());
            answer2.setTag("not");
            answer3.setText(answers.get(1).toString());
            answer3.setTag("not");
            answer4.setText(answers.get(0).toString());
            answer4.setTag("right");

        }
        if(!firstRound)scoreSystem.newRound();
        score.setText(scoreSystem.getScore());

    }

    public void animate(int isRight){

        if(isRight == 1) {

            aniBackground.animate().cancel();
            aniBackground.setBackgroundColor(Color.parseColor("#70c1b3"));
            aniBackground.setAlpha(1);
            aniBackground.animate().alpha(0f).setDuration(1100);
            aniBackground.animate().start();




        }else{
            aniBackground.animate().cancel();
            aniBackground.setBackgroundColor(Color.parseColor("#f25f5c"));
            aniBackground.setAlpha(1);
            aniBackground.animate().alpha(0f).setDuration(1100);
            aniBackground.animate().start();
        }
    }





}

