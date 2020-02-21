package com.example.calculatorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calculatorgame.models.Answer;
import com.example.calculatorgame.models.AnswerList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    AnswerList answerList = AnswerList.getInstance();

    ListView listViewAnswers;
    TextView textViewScore;
    EditText editTextRegisterName;
    Button btnShow;
    Button btnBack;

    RadioButton rbtnAll;
    RadioButton rbtnRight;
    RadioButton rbtnWrong;
    RadioButton rbtnSortA;
    RadioButton rbtnSortD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        initialize();
        initializeListView(answerList.getListOfAnswers());
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewScore.setText(answerList.generatePercentage());
    }


    private void initialize() {

        rbtnAll = findViewById(R.id.rbtnAll);
        rbtnAll.setOnClickListener(this);

        rbtnRight = findViewById(R.id.rbtnRight);
        rbtnRight.setOnClickListener(this);

        rbtnWrong = findViewById(R.id.rbtnWrong);
        rbtnWrong.setOnClickListener(this);

        rbtnSortA = findViewById(R.id.rbtnSortA);
        rbtnSortA.setOnClickListener(this);

        rbtnSortD = findViewById(R.id.rbtnSortD);
        rbtnSortD.setOnClickListener(this);

        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        textViewScore = findViewById(R.id.textViewScore);
        editTextRegisterName = findViewById(R.id.editTextRegister);
    }

    @Override
    public void onClick(View v) {
        int btnId = v.getId();
        Answer.AnswerCompare answerCompare = new Answer().new AnswerCompare();

        switch(btnId) {
            case R.id.rbtnAll:
                initializeListView(answerList.getListOfAnswers());
                break;
            case R.id.rbtnRight:
                initializeListView(answerList.getListOfRightAnswers());
                break;
            case R.id.rbtnWrong:
                initializeListView(answerList.getListOfWrongAnswers());
                break;
            case R.id.rbtnSortA:
                Collections.sort(answerList.getListOfAnswers(), answerCompare);
                initializeListView(answerList.getListOfAnswers());
                break;
            case R.id.rbtnSortD:
                Collections.sort(answerList.getListOfAnswers(), answerCompare);
                Collections.reverse(answerList.getListOfAnswers());
                initializeListView(answerList.getListOfAnswers());
                break;
            case R.id.btnShow:
                makeToast("Show", "short");
                break;
            case R.id.btnBack:
                goBack();
                break;
        }
    }

    private void goBack() {
        String registeredName = editTextRegisterName.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("register_name", registeredName);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void initializeListView(ArrayList<Answer> answerList) {
        listViewAnswers = findViewById(R.id.listViewAnswers);

        ArrayAdapter<Answer> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, answerList);

        listViewAnswers.setAdapter(listAdapter);
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
