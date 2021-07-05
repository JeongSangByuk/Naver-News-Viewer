package com.example.navernews.InterFaces;

public interface MainContract {

    interface View{
        void setNewTvData(String text);
    }

    interface Presenter{
        void changeData(String text1,String text2);
    }

}
