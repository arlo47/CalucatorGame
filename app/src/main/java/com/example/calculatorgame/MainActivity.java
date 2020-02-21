package com.example.calculatorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculatorgame.models.Answer;
import com.example.calculatorgame.models.AnswerList;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static int REQUEST_CODE1 = 1;

    AnswerList answerList = AnswerList.getInstance();
    double answer;
    boolean placedDecimal = false;

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
    Button btnDelete;
    Button btnClear;
    Button btnFinish;

    EditText editTextUserInput;
    TextView textViewQuestion;
    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        textViewQuestion = findViewById(R.id.question);
        textViewTitle = findViewById(R.id.textViewTitle);
        editTextUserInput = findViewById(R.id.userInput);

        initializeButtonGrid();

        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(this);

        btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(this);
        btnValidate.setEnabled(false);

        btnScore = findViewById(R.id.btnScore);
        btnScore.setOnClickListener(this);

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(this);
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

        String operator = generateOperatorAndAnswer(operand1, operand2, 4);

        String operation = String.valueOf(operand1) + " " + operator + " " + String.valueOf(operand2) + " = ?";
        textViewQuestion.setText(operation);

        btnDecimal.setEnabled(true);
        btnNegative.setEnabled(true);
    }

    //generates operator for question
    private String generateOperatorAndAnswer(int operand1, int operand2, int randomBound) {

        Random random = new Random();
        int operatorId = random.nextInt(randomBound);

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
                //generate new operator equation has 0
                if(operand1 == 0 || operand2 == 0)
                    generateOperatorAndAnswer(operand1, operand2, 3);

                operator = "/";
                answer = operand1 / operand2;
                break;
        }

        return operator;
    }

    //invokes when btnValidate is hit, checks user input against answer
    private void validateInput() {
        //stop if there is no input
        if(editTextUserInput.getText().toString().length() < 1)
            return;

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

        Bundle bundle = new Bundle();
        bundle.putSerializable("bundleExtra", answerList);

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("intentExtra", bundle);

        startActivityForResult(intent, REQUEST_CODE1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String receivedData = (String)data.getStringExtra("register_name");
        textViewTitle.setText(receivedData);

    }

    //invokes each time a number btn is hit, appends number to userInput
    private void appendInputToEditText(Button btn) {

        String buttonText = btn.getText().toString();

        //disable btnDecimal after used once
        if(buttonText.equals("."))
            btnDecimal.setEnabled(false);

        //disable btnNegative after first input
        if(btnNegative.isEnabled())
            btnNegative.setEnabled(false);

        editTextUserInput.append(buttonText);
        btnValidate.setEnabled(true);

    }

    private void deleteCharacter() {

        boolean hasUserInput = editTextUserInput.getText().toString().length() > 0;

        if(hasUserInput) {
            String userInput = editTextUserInput.getText().toString();
            String newUserInput = userInput.substring(0, userInput.length() -1);
            editTextUserInput.setText(newUserInput);
        }
        else
            btnValidate.setEnabled(false);
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
                break;
            case R.id.btnScore:
                showScore();
                break;
            case R.id.btnDelete:
                deleteCharacter();
                break;
            case R.id.btnClear:
                clearInput();
                break;
            case R.id.btnFinish:
                endApp();
                break;
            default:
                Button numberBtn = (Button) v;
                appendInputToEditText(numberBtn);
                break;
        }
    }

    private void clearInput() {
        editTextUserInput.setText(null);
    }

    private void endApp() {
        finish();
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
