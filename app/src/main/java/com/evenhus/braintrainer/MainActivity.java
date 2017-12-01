package com.evenhus.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startButton;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    TextView resultTextView;
    TextView scoreTextView;
    TextView sumTextView;
    TextView timer;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button playAgainButton;
    RelativeLayout gameRelativeLayout;
    ListView highScoreListView;
    ArrayList<Integer> arrayList;
    ArrayAdapter<Integer> adapter;
    TextView highScoreResult;

    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        resultTextView = (TextView)findViewById(R.id.resultText);
        scoreTextView = (TextView)findViewById(R.id.scoreText);
        sumTextView = (TextView)findViewById(R.id.sumText);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        timer = (TextView)findViewById(R.id.timerView);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);
        highScoreListView = (ListView) findViewById(R.id.highScoreListView);
        arrayList = new ArrayList<Integer>();
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, arrayList);
        highScoreResult = (TextView)findViewById(R.id.highestScore);


        playAgain(findViewById(R.id.playAgainButton));

        highScoreListView.setAdapter(adapter);


    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;

        timer.setText("30s");
        scoreTextView.setText("0/0");
        sumTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        generateQuestion();


        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                setHighScore(score);
                playAgainButton.setVisibility(View.VISIBLE);
                timer.setText("0s");
                resultTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);
            }
        }.start();
    }

    public void chooseAnswer(View view) {
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong");
        }

        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void start(View view) {
        startButton.setVisibility(startButton.INVISIBLE);
        gameRelativeLayout.setVisibility(RelativeLayout.VISIBLE);

        playAgain(findViewById(R.id.playAgainButton));

    }

    public void generateQuestion() {
        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(Integer.toString(a) + "+" + Integer.toString(b));

        locationOfCorrectAnswer = random.nextInt(4);

        answers.clear();

        int incorrectAnswer;

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                incorrectAnswer = random.nextInt(41);

                while (incorrectAnswer == a+b) {
                    incorrectAnswer = random.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));
    }

    public void setHighScore(int highScore){
        if(arrayList.isEmpty()) {
            arrayList.add(highScore);
            highScoreResult.setText(Integer.toString(highScore));
        } else {
            if (highScore > arrayList.get(0)) {
                System.out.println("Highscore: " + highScore + "List number one:" + arrayList.get(0));
                Log.i("HighScore", "Higher");
                highScoreResult.setText(Integer.toString(highScore));
                arrayList.add(highScore);
            } else {
                Log.i("HighScore", "Lower");
                arrayList.add(highScore);
            }
        }
    }
}
