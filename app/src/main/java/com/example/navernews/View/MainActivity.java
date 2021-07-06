package com.example.navernews.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.navernews.InterFaces.MainContract;
import com.example.navernews.Presenter.NewsPresenter;
import com.example.navernews.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mainPresenter;
    private TextView tv1,tv2,tv3;
    private Button bnt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObject();
        initListener();

        mainPresenter = new NewsPresenter(this);
    }

    private void initObject(){
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv222);
        tv3 = findViewById(R.id.tv33);
        bnt1 = findViewById(R.id.bnt1);
    }

    private void initListener(){

        bnt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = tv1.getText().toString();
                String text2 = tv2.getText().toString();
                mainPresenter.changeData(text1,text2);
            }
        });
    }

    @Override
    public void setNewTvData(String text) {
        tv3.setText(text);
    }
}