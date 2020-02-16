package com.example.calculatorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculatorgame.models.Answer;
import com.example.calculatorgame.models.AnswerList;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AnswerList answerList = AnswerList.getInstance();
    double answer;

    Button btn9;
    Button btn8;
    Button btn7;
    Button btn6;
    Button btn5;
    Button btn4;
    Button btn3;
    Button btn2;
    Button btn1;
    Button btn0;
    Button btnDecimal;
    Button btnNegative;

    Button btnGenerate;
    Button btnValidate;
    Button btnScore;

    EditText editTextUserInput;
    TextView textViewQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        textViewQuestion = findViewById(R.id.question);
        editTextUserInput = findViewById(R.id.userInput);

        initializeButtonGrid();

        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(this);

        btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(this);

        btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(this);
    }

    //assigns number buttons to variables + sets up event listeners
    private void initializeButtonGrid() {
        btn9 = findViewById(R.id.btn9);
        btn9.setOnClickListener(this);

        btn8 = findViewById(R.id.btn8);
        btn8.setOnClickListener(this);

        btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);

        btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);

        btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(this);

        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btn0 = findViewById(R.id.btn0);
        btn0.setOnClickListener(this);

        btnDecimal = findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(this);

        btnNegative = findViewById(R.id.btnNegative);
        btnNegative.setOnClickListener(this);
    }

    //generates a random number between 0-9, calls generateOperator() to get operator,
    // sets question string to TextView
    private void generateQuestion() {
        Random random = new Random();
        int operand1 = random.nextInt(10);
        int operand2 = random.nextInt(10);
        String operator = generateOperatorAndAnswer(operand1, operand2);

        String operation = String.valueOf(operand1) + " " + operator + " " + String.valueOf(operand2) + " = ?";
        textViewQuestion.setText(operation);
    }

    //generates operator for question
    private String generateOperatorAndAnswer(int operand1, int operand2) {

        Random random = new Random();
        int operatorId = random.nextInt(4);
        String operator = "";

        switch(operatorId) {
            case 0:
                operator = "+";
                answer = operand1 + operand2;
                break;
            case 1:
                operator = "-";
                answer = operand1 - operand2;
                break;
            case 2:
                operator = "x";
                answer = operand1 * operand2;
                break;
            case 3:
                operator = "/";
                answer = operand1 / operand2;
                break;
        }

        return operator;
    }

    //invokes when btnValidate is hit, checks user input against answer
    private void validateInput() {
        double userAnswer = Double.parseDouble(editTextUserInput.getText().toString());
        int userQuestionLengthMinusLastChar = textViewQuestion.getText().toString().length() - 1;
        String userQuestion = textViewQuestion.getText().toString().substring(0, userQuestionLengthMinusLastChar);

        if(userAnswer == answer) {
            makeToast("Correct!", "short");
            answerList.addToListOfAnswers(new Answer(userQuestion, userAnswer, true));
        }
        else {
            makeToast("Incorrect!", "short");
            answerList.addToListOfAnswers(new Answer(userQuestion, userAnswer, false));
        }

        //clear text
        textViewQuestion.setText(null);
        editTextUserInput.setText(null);
    }

    private void showScore() {

        String result = answerList.displayAnswerList() + answerList.displayRightToWrongComparison();

        makeToast(result, "short");
    }

    //invokes each time a number btn is hit, appends number to userInput
    private void appendInputToEditText(Button btn) {

        String buttonText = btn.getText().toString();

        editTextUserInput.append(buttonText);
    }

    @Override
    public void onClick(View v) {

        int btnId = v.getId();


        switch(btnId) {
            case R.id.btnGenerate:
                generateQuestion();
                break;
            case R.id.btnValidate:
                validateInput();
            case R.id.btnScore:
                showScore();
                break;
            default:
                Button numberBtn = (Button) v;
                appendInputToEditText(numberBtn);
                break;
        }
    }

    private void makeToast(String message, String timeOut) {
        if(timeOut == "short") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }




}
