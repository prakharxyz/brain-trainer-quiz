package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //declare necessary variables and objects
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAns; //location of correct answer will be randomized everytime new question is asked
    TextView resultTextView; //it shows the outcome of question , whether ans given by user is correct or wrong
    int score=0; //initialize var score as 0
    int numberOfQuestions=0; //initialize var nOQ as 0
    //define all 4 buttons or choices in gridLayout
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    TextView sumTextView; //shows the sum question in textView
    TextView timerTextView; //timer of 30s starts with first ques and user have to ans as many ques correct until timer runs out
    TextView scoreTextView; //it will display user's current score
    Button playAgainButton; //button to restart or play again when time gets over
    CountDownTimer countDownTimer; //for timer

    //when user wants to restart the game
    public void playAgain(View view)
    {
        score=0;
        numberOfQuestions=0;
        scoreTextView.setText("0/0");
        //score & nOQ initialized to 0 and set in textView
        timerTextView.setText("30s"); //timer tv initialised to 30s
        newQuestion(); //func called to generate new ques
        playAgainButton.setVisibility(View.INVISIBLE); //play again button set hidden as it is not needed while timer is on
        startGame(); //func called to start the timer
    }

    public void startGame() { //to start timer
        countDownTimer = new CountDownTimer(30100,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000)+"s"); //tv set to sec left
                button0.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                //user can choose answers ,ie, game is enabled
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Done"); //tv says 'done'
                playAgainButton.setVisibility(View.VISIBLE); //play button is now visible
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                //timer is over so user now cannot answer or choose, he has to play again
            }
        }.start();
    }

    public void chooseAnswer(View view) //when any choice is clicked by user
    {
        if(Integer.toString(locationOfCorrectAns).equals(view.getTag().toString())){
            //if location of correct ans matches with tag of clicked button update score & display result in tv
            resultTextView.setText("correct");
            score++;
        }
        else{
            //dont update score else & diplay result as wrong
            resultTextView.setText("Wrong");
        }
        //update nOQ everytime user clicks & call newQ func & update scoreTV
        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
        newQuestion();
    }

    public void newQuestion(){ //to generate new question and new choices
        //declare 2 random var having value b/w 1-20 & display as sum in sumTV
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        sumTextView.setText(Integer.toString(a)+"+"+Integer.toString(b));

        locationOfCorrectAns = rand.nextInt(4); //randomize var from (0,1,2,3) to store a random loc of correct ans among wrong choices
        answers.clear(); //clear answers arrayList so that new choices can be set with each question
        //set 4 choices in arrayList by loop.
        for(int i=0;i<4;i++)
        {
            if(i==locationOfCorrectAns){ //add correct ans for this condition
                answers.add(a+b);
            }
            else{
                int wrongAns = rand.nextInt(41); //randomize wrongAns from 1-40
                while (wrongAns==a+b){ //if random comes equal to correct one rerandomize
                    wrongAns = rand.nextInt(41);
                }
                answers.add(wrongAns); //add in arrayList
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        //set in buttons
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        startGame();
    }
}