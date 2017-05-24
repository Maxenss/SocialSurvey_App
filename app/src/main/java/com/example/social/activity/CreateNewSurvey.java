package com.example.social.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social.R;
import com.example.social.classes.Data;
import com.example.social.classes.Option;
import com.example.social.classes.Question;
import com.example.social.classes.Survey;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CreateNewSurvey extends AppCompatActivity implements View.OnClickListener {
    EditText etSurveyName;
    EditText etSurveyComment;
    EditText etCountOfQuestions;

    ScrollView svMainInfoNewSurvey;
    LinearLayout llNewSurveyQuetions;

    LinearLayout llResult;

    Switch swMultiple;

    EditText etQuestionText;

    EditText evNewAnswer1;
    EditText evNewAnswer2;
    EditText evNewAnswer3;
    EditText evNewAnswer4;
    EditText evNewAnswer5;
    EditText evNewAnswer6;
    EditText evNewAnswer7;
    EditText evNewAnswer8;
    EditText evNewAnswer9;
    EditText evNewAnswer10;
    EditText evNewAnswer11;
    EditText evNewAnswer12;
    EditText evNewAnswer13;
    EditText evNewAnswer14;
    EditText evNewAnswer15;
    EditText evNewAnswer16;

    EditText etAnswersArray[] = new EditText[16];

    TextView tvQuestionNumber;

    Button btContinueCreateSurvey;
    Button btNextQuestion;
    Button btPreviosQuestion;

    TextView tvResultOfCreate;
    Button btExit;
    Button btCreateMore;

    private Animation animation;

    Survey newSurvey;

    int countOfQuestions = 0;
    int questionIndexator = -1;

    private String requestData;
    private String responseData;
    private int responseCode;

    private boolean isCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_survey);

        initializeView();

        tvQuestionNumber.setText(1 + ". Вопрос : ");
        setTitle("Создание нового опроса");
    }

    private void initializeView() {
        etSurveyName = (EditText) findViewById(R.id.etSurveyName);
        etSurveyComment = (EditText) findViewById(R.id.etSurveyComment);
        etCountOfQuestions = (EditText) findViewById(R.id.etCountOfQuestions);

        tvQuestionNumber = (TextView) findViewById(R.id.tvQuestionNumber);

        btContinueCreateSurvey = (Button) findViewById(R.id.btContinueCreateSurvey);

        svMainInfoNewSurvey = (ScrollView) findViewById(R.id.svMainInfoNewSurvey);
        llNewSurveyQuetions = (LinearLayout) findViewById(R.id.llNewSurveyQuetions);

        swMultiple = (Switch) findViewById(R.id.swMultiple);

        etQuestionText = (EditText) findViewById(R.id.etQuestionText);

        evNewAnswer1 = (EditText) findViewById(R.id.evNewAnswer1);
        evNewAnswer2 = (EditText) findViewById(R.id.evNewAnswer2);
        evNewAnswer3 = (EditText) findViewById(R.id.evNewAnswer3);
        evNewAnswer4 = (EditText) findViewById(R.id.evNewAnswer4);
        evNewAnswer5 = (EditText) findViewById(R.id.evNewAnswer5);
        evNewAnswer6 = (EditText) findViewById(R.id.evNewAnswer6);
        evNewAnswer7 = (EditText) findViewById(R.id.evNewAnswer7);
        evNewAnswer8 = (EditText) findViewById(R.id.evNewAnswer8);
        evNewAnswer9 = (EditText) findViewById(R.id.evNewAnswer9);
        evNewAnswer10 = (EditText) findViewById(R.id.evNewAnswer10);
        evNewAnswer11 = (EditText) findViewById(R.id.evNewAnswer11);
        evNewAnswer12 = (EditText) findViewById(R.id.evNewAnswer12);
        evNewAnswer13 = (EditText) findViewById(R.id.evNewAnswer13);
        evNewAnswer14 = (EditText) findViewById(R.id.evNewAnswer14);
        evNewAnswer15 = (EditText) findViewById(R.id.evNewAnswer15);
        evNewAnswer16 = (EditText) findViewById(R.id.evNewAnswer16);

        tvResultOfCreate = (TextView) findViewById(R.id.tvResultOfCreate);
        btExit = (Button) findViewById(R.id.btExit);
        btCreateMore = (Button) findViewById(R.id.btCreateMore);

        llResult = (LinearLayout) findViewById(R.id.llResult);

        etAnswersArray[0] = evNewAnswer1;
        etAnswersArray[1] = evNewAnswer2;
        etAnswersArray[2] = evNewAnswer3;
        etAnswersArray[3] = evNewAnswer4;
        etAnswersArray[4] = evNewAnswer5;
        etAnswersArray[5] = evNewAnswer6;
        etAnswersArray[6] = evNewAnswer7;
        etAnswersArray[7] = evNewAnswer8;
        etAnswersArray[8] = evNewAnswer9;
        etAnswersArray[9] = evNewAnswer10;
        etAnswersArray[10] = evNewAnswer11;
        etAnswersArray[11] = evNewAnswer12;
        etAnswersArray[12] = evNewAnswer13;
        etAnswersArray[13] = evNewAnswer14;
        etAnswersArray[14] = evNewAnswer15;
        etAnswersArray[15] = evNewAnswer16;


        btContinueCreateSurvey = (Button) findViewById(R.id.btContinueCreateSurvey);
        btNextQuestion = (Button) findViewById(R.id.btNextQuestion);
        btPreviosQuestion = (Button) findViewById(R.id.btPreviosQuestion);

        btContinueCreateSurvey.setOnClickListener(this);
        btNextQuestion.setOnClickListener(this);
        btPreviosQuestion.setOnClickListener(this);
        btExit.setOnClickListener(this);
        btCreateMore.setOnClickListener(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.butanim);
    }

    private void clearAllEditText() {
        evNewAnswer1.setText("");
        evNewAnswer2.setText("");
        evNewAnswer3.setText("");
        evNewAnswer4.setText("");
        evNewAnswer5.setText("");
        evNewAnswer6.setText("");
        evNewAnswer7.setText("");
        evNewAnswer8.setText("");
        evNewAnswer9.setText("");
        evNewAnswer10.setText("");
        evNewAnswer11.setText("");
        evNewAnswer12.setText("");
        evNewAnswer13.setText("");
        evNewAnswer14.setText("");
        evNewAnswer15.setText("");
        evNewAnswer16.setText("");

        etQuestionText.setText("");

        swMultiple.setChecked(false);

    }

    @Override
    public void onClick(View v) {
        v.startAnimation(animation);

        try {
            switch (v.getId()) {
                case R.id.btContinueCreateSurvey: {
                    btContinueCreateSurveyClick();
                    break;
                }
                case R.id.btNextQuestion: {
                    btNextQuestionClick();
                    break;
                }
                case R.id.btPreviosQuestion: {
                    btPreviosQuestionClick();
                    break;
                }
                case R.id.btExit: {
                    btExitClick();
                    break;
                }
                case R.id.btCreateMore: {
                    btCreateMoreClick();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (questionIndexator < 0)
            createExitDialog();

        btPreviosQuestionClick();
    }

    public void getBack() {
        super.onBackPressed();
    }

    public void createExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewSurvey.this);
        builder.setTitle("Вы уверены, что хотите покинуть конструктор опросов ?")
                .setMessage("При выходе из конструктора, все данные будут удалены")
                .setCancelable(true).setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getBack();
                    }
                })
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void createNewSurveyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateNewSurvey.this);
        builder.setTitle("Создать новый опрос?")
                .setMessage("Создать новый опрос? Позже его можно будет изменить")
                .setCancelable(true)
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Отправляем на сервер
                try {
                    createSurveyMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void btContinueCreateSurveyClick() {
        if (etSurveyName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Название опроса\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etSurveyComment.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Комментарией о опросе\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etCountOfQuestions.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Количество вопросов\"", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            countOfQuestions = Integer.parseInt(etCountOfQuestions.getText().toString());

            if (countOfQuestions == 0) {
                Toast.makeText(this, "Количество вопросов не может быть равно 0", Toast.LENGTH_SHORT).show();
                return;
            } else if (countOfQuestions < 0) {
                Toast.makeText(this, "Количество вопросов не может быть меньше 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Введены некорректные данные ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newSurvey == null) {
            newSurvey = new Survey();
        }

        newSurvey.setName(etSurveyName.getText().toString());
        newSurvey.setComment(etSurveyComment.getText().toString());
        newSurvey.setArrayListQuestions(new ArrayList<Question>(countOfQuestions));

        svMainInfoNewSurvey.setVisibility(View.GONE);
        llNewSurveyQuetions.setVisibility(View.VISIBLE);

        questionIndexator = 0;
    }

    private void btNextQuestionClick() {
        if (etQuestionText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Текст вопроса\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (evNewAnswer1.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Обязательный вопрос 1\"", Toast.LENGTH_SHORT).show();
            return;
        }
        if (evNewAnswer1.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заполните поле \"Обязательный вопрос 1\"", Toast.LENGTH_SHORT).show();
            return;
        }

        Question question = new Question();
        question.setText(etQuestionText.getText().toString());
        question.setQuestionType(swMultiple.isChecked() ? "MultiSelect" : "Select");
        question.getArrayListOptions().add(new Option(evNewAnswer1.getText().toString()));
        question.getArrayListOptions().add(new Option(evNewAnswer2.getText().toString()));

        if (!evNewAnswer3.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer3.getText().toString()));

        if (!evNewAnswer4.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer4.getText().toString()));

        if (!evNewAnswer5.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer5.getText().toString()));

        if (!evNewAnswer6.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer6.getText().toString()));

        if (!evNewAnswer7.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer7.getText().toString()));

        if (!evNewAnswer8.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer8.getText().toString()));

        if (!evNewAnswer9.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer9.getText().toString()));

        if (!evNewAnswer10.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer10.getText().toString()));

        if (!evNewAnswer11.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer11.getText().toString()));

        if (!evNewAnswer12.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer12.getText().toString()));

        if (!evNewAnswer13.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer13.getText().toString()));

        if (!evNewAnswer14.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer14.getText().toString()));

        if (!evNewAnswer15.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer15.getText().toString()));

        if (!evNewAnswer16.getText().toString().isEmpty())
            question.getArrayListOptions().add(new Option(evNewAnswer16.getText().toString()));

        newSurvey.getArrayListQuestions().add(questionIndexator, question);

        ++questionIndexator;

        if (questionIndexator > 0)
            btPreviosQuestion.setText("Предыдущий вопрос");

        if (questionIndexator - 1 < countOfQuestions)
            btNextQuestion.setText("Создать опрос");

        if (questionIndexator < countOfQuestions) {
            tvQuestionNumber.setText(questionIndexator + 1 + ". Вопрос : ");

            clearAllEditText();
        }
        // Если вопросы закончились
        else {
            // Пока что костыль
            --questionIndexator;
            Toast.makeText(this, "Все вопросы созданы", Toast.LENGTH_SHORT).show();
            createNewSurveyDialog();
        }

    }

    private void btPreviosQuestionClick() {
        --questionIndexator;

        Question question;

        btNextQuestion.setText("Следующий вопрос");

        if (questionIndexator < 0) {
            svMainInfoNewSurvey.setVisibility(View.VISIBLE);
            llNewSurveyQuetions.setVisibility(View.GONE);
        } else {
            tvQuestionNumber.setText(questionIndexator + 1 + ". Вопрос : ");

            if (newSurvey.getArrayListQuestions().get(questionIndexator) != null) {
                etQuestionText.setText(newSurvey.getArrayListQuestions().get(questionIndexator).getText());

                swMultiple.setChecked(newSurvey.getArrayListQuestions().
                        get(questionIndexator).getQuestionType().equals("MultiSelect"));

                for (int i = 0; i < newSurvey.getArrayListQuestions().get(questionIndexator).
                        getArrayListOptions().size(); i++) {

                    if (newSurvey.getArrayListQuestions().get(questionIndexator).
                            getArrayListOptions().get(i) != null) {
                        etAnswersArray[i].setText(newSurvey.getArrayListQuestions().get(questionIndexator).
                                getArrayListOptions().get(i).getText());
                    }
                }

                if (questionIndexator == 0)
                    btPreviosQuestion.setText("Основное");

            } else {
                clearAllEditText();
            }
        }
    }

    private void btExitClick() {
        getBack();
    }

    private void btCreateMoreClick() {
        questionIndexator = -1;

        newSurvey = new Survey();

        svMainInfoNewSurvey.setVisibility(View.VISIBLE);
        llNewSurveyQuetions.setVisibility(View.GONE);
        llResult.setVisibility(View.GONE);
        btNextQuestion.setText("Следующий вопрос");

        clearAllEditText();
    }

    // --------------------------------------------------------------//
    //               Методы для создания опроса на сервере
    // --------------------------------------------------------------//

    private void createSurveyMethod() throws Exception {
        CreateSurveyTask cs = new CreateSurveyTask();
        cs.execute();
    }

    // HTTP Post request
    private String makeRequestPost(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";
        requestData = newSurvey.getNewSurveyOnServerJSON();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + Data.token);
        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(requestData.getBytes("UTF-8"));
        wr.flush();
        wr.close();

        responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + requestData);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();

        System.out.println(responseData);
        return responseData;
    }

    private class CreateSurveyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequestPost(Data.URL + "api/surveys");
                isCorrect = true;
            } catch (Exception e) {
                e.printStackTrace();
                isCorrect = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            if (isCorrect) {
                Toast.makeText(CreateNewSurvey.this, "Опрос добавлен на сервер", Toast.LENGTH_SHORT).show();

                svMainInfoNewSurvey.setVisibility(View.GONE);
                llNewSurveyQuetions.setVisibility(View.GONE);
                llResult.setVisibility(View.VISIBLE);

                tvResultOfCreate.setText("Опрос добавлен");

            } else
                Toast.makeText(CreateNewSurvey.this, "Что-то  пошло не так", Toast.LENGTH_SHORT).show();

            isCorrect = false;
        }
    }
}