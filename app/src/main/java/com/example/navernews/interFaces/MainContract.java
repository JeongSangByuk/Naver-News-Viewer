package com.example.navernews.interFaces;

public interface MainContract {

    interface View{
        void setNewTvData(String text);
    }

    interface Presenter{
        void changeData(String text1,String text2);
    }

}
