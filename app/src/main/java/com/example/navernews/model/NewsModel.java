package com.example.navernews.model;

import android.util.Log;

import com.example.navernews.presenter.NewsPresenter;
import com.example.navernews.utils.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsModel {

    private NewsPresenter presenter;
    private Disposable backgroundtask;
    private ArrayList<NewsDTO> news;
    private boolean isAdding;

    public NewsModel(NewsPresenter presenter) {
        this.presenter = presenter;
    }

    public ArrayList<NewsDTO> getNewsData() {

        isAdding = false;
        String url = getURL();
        news = new ArrayList<>();
        backgroundTask(url);

        return news;
    }

    public ArrayList<NewsDTO> addNewsData(int nowPageNum){

        isAdding = true;
        String url = getURL();
        url = url + "&date=" + getNowTime() + "&page=" + nowPageNum;
        backgroundTask(url);

        return news;

    }

    void backgroundTask(String URLs) {
        
        presenter.isUpdating = true;

        //onPreExecute
        presenter.getMainView().showLoadingDialog();

        backgroundtask = Observable.fromCallable(() -> {

            //doInBackground
            try {
                Log.d("qwe",presenter.nowCategory.toString());
                Document doc = Jsoup.connect(URLs).timeout(3000).get();
                Elements elements = doc.select("ul[class=type06_headline]").select("li");
                Elements elements2 = doc.select("ul[class=type06]").select("li");

                getDataFromURL(elements);
                getDataFromURL(elements2);

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("qwe","eerr");
            }

            return false;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

            //onPostExecute
            if(isAdding)
                presenter.getMainView().onDataChange(true);
            else
                presenter.getMainView().onDataChange(false);
            presenter.getMainView().dismissLoadingdialog();
            presenter.onLoaded();
            presenter.isUpdating = false;
            backgroundtask.dispose();
        });
    }

    private void getDataFromURL(Elements elements){
        for(Element element : elements){

            int index = 2;

            String imgLink = element.select("li dt[class=photo] img").attr("src");

            if(imgLink.equals("")){
                index = 1;
            }

            String des = element.getElementsByIndexEquals(index).select("span[class=lede]").text();
            if(des.equals(""))
                des = "본문의 내용이 없습니다.";

            String title = element.getElementsByIndexEquals(index-1).select("a[class=nclicks(fls.list)]").text();
            String link = element.getElementsByIndexEquals(index-1).select("a[class=nclicks(fls.list)]").attr("href");
            //String writing = element.getElementsByIndexEquals(index).select("span[class=writing]").text();
            String time = element.getElementsByIndexEquals(index).select("span[class=date is_new]").text();
            if(time.equals(""))
                time = element.getElementsByIndexEquals(index).select("span[class=date is_outdated]").text();

            news.add(new NewsDTO(title,des,time,imgLink,link));
        }
    }

    private String getNowTime(){
        long nowTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String time = simpleDateFormat.format(new Date(nowTime));
        return time;
    }

    private String getURL(){
        switch (presenter.nowCategory){
            case POL:
                return Constants.POLITICS_URL;
            case ECO:
                return Constants.ECONOMY_URL;
            case SOC:
                return Constants.SOCIETY_URL;
            case LIF:
                return Constants.LIFE_URL;
            case IT:
                return Constants.IT_URL;
        }
        return "";
    }

}
