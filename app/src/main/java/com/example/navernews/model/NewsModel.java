package com.example.navernews.model;

import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;

import androidx.core.util.Consumer;

import com.example.navernews.interFaces.MainContract;
import com.example.navernews.presenter.NewsPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import javax.xml.transform.Result;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NewsModel {

    private NewsPresenter presenter;
    private Disposable backgroundtask;

    public NewsModel(NewsPresenter presenter) {
        this.presenter = presenter;
    }

    public ArrayList<NewsDTO> getNewsData() {

        ArrayList<NewsDTO> news = new ArrayList<>();
        backgroundTask("https://news.naver.com/main/list.naver?mode=LSD&mid=sec&sid1=100");

        return news;
    }

    void backgroundTask(String URLs) {
        //onPreExecute

        backgroundtask = Observable.fromCallable(() -> {

            //doInBackground
            try {
                Document doc = Jsoup.connect(URLs).get();
                Elements elements = doc.select("ul[class=type06_headline]").select("li");
                int size = elements.size();

                for(Element element : elements){

                    int index = 2;

                    String link = element.select("li dt[class=photo] a").attr("href");
                    String imgLink = element.select("li dt[class=photo] img").attr("src");

                    if(imgLink.equals("")){
                        index = 1;
                    }

                    String des = element.getElementsByIndexEquals(index).select("span[class=lede]").text();
                    String title = element.getElementsByIndexEquals(index-1).select("a[class=nclicks(fls.list)]").text();
                    String writing = element.getElementsByIndexEquals(index).select("span[class=writing]").text();
                    String time = element.getElementsByIndexEquals(index).select("span[class=date is_new]").text();
                    if(time.equals(""))
                        time = element.getElementsByIndexEquals(index).select("span[class=date is_outdated]").text();

                    presenter.getNews().add(new NewsDTO(title,des,time,imgLink));
                    Log.d("qwe",time);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

            //onPostExecute
            presenter.getMainView().onDataChange();
            backgroundtask.dispose();
        });
    }
}
