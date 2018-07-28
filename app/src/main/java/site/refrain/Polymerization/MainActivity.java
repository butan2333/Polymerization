package site.refrain.Polymerization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import site.refrain.R;

public class MainActivity extends AppCompatActivity {
    private boolean isScrollToBottom=false;
    private int loadingTime=0;//设置加载次数，以确定加载日期
    ListView zhihudailyList;
    ArticleAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {//初次加载数据

                zhihudailyList.setAdapter(adapter);
            }
            if(msg.what==0x124){//再次加载数据
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get current time in string format
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);
        date=calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        adapter=new ArticleAdapter(this);
        final String time=simpleDateFormat.format(date);
        onFirstLoad(time);
        zhihudailyList=findViewById(R.id.zhihudaily);
        zhihudailyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//当点击Item时进入相应的文章界面
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article article=adapter.getItem(i);
                Intent intent=new Intent(MainActivity.this,zhihudailyPage.class);
                intent.putExtra("article_id",article.getId());
                intent.putExtra("article_title",article.getTitle());
                intent.putExtra("article_image",article.getTitleImg());
                startActivity(intent);
            }
        });
        zhihudailyList.setOnScrollListener(new AbsListView.OnScrollListener() {//检测滚到底加载上一天的内容
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(scrollState==SCROLL_STATE_IDLE||scrollState==SCROLL_STATE_FLING){
                    if(isScrollToBottom){
                        onLoadMore(time);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (absListView.getLastVisiblePosition() == (totalItemCount - 1)) {//如果相同则已经滑到底部
                    isScrollToBottom = true;
                } else {
                    isScrollToBottom = false;
                }

            }
        });


    }
    private void onFirstLoad(String time){//下面的函数是分别第一次加载时和再次加载时的函数，只有给handler的massage不同
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        try {
             date= simpleDateFormat.parse(time);
        } catch (ParseException e) {
            Log.d("Main Activity","Wrong Formate of time when loading more");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-loadingTime);
        date=calendar.getTime();
        final String new_time=simpleDateFormat.format(date);

        new Thread() {

            Document html_doc;
            @Override
            public void run(){
                try

                {
                    html_doc = Jsoup.connect("https://news-at.zhihu.com/api/4/news/before/"+new_time).ignoreContentType(true).get();
                } catch(IOException e) {
                    e.printStackTrace();
                }

                Pattern pattern = Pattern.compile("\\{\"images\":\\[\"(.*?)\"\\],\"type\":0,\"id\":(\\d+),\"ga_prefix\":\"(\\d+)\",\"title\":\"(.*?)\"");

                Matcher matcher = pattern.matcher(html_doc.body().toString());

                while(matcher.find()) {
                    Article temp_article;
                    temp_article = new Article(matcher.group(4).replace("\\r","\r").replace("\\n","\n"), matcher.group(1), matcher.group(2));
                    adapter.addArticle(temp_article);
                }
                handler.sendEmptyMessage(0x123);
            }
        }.start();
        Log.d("Main Activity",new_time);
        loadingTime++;
    }

    private void onLoadMore(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        try {
            date= simpleDateFormat.parse(time);
        } catch (ParseException e) {
            Log.d("Main Activity","Wrong Formate of time when loading more");
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-loadingTime);
        date=calendar.getTime();
        final String new_time=simpleDateFormat.format(date);

        new Thread() {

            Document html_doc;
            @Override
            public void run(){
                try

                {
                    html_doc = Jsoup.connect("https://news-at.zhihu.com/api/4/news/before/"+new_time).ignoreContentType(true).get();
                } catch(IOException e) {
                    e.printStackTrace();
                }

                Pattern pattern = Pattern.compile("\\{\"images\":\\[\"(.*?)\"\\],\"type\":0,\"id\":(\\d+),\"ga_prefix\":\"(\\d+)\",\"title\":\"(.*?)\"");

                Matcher matcher = pattern.matcher(html_doc.body().toString());

                while(matcher.find()) {
                    Article temp_article;
                    temp_article = new Article(matcher.group(4).replace("\\r","\r").replace("\\n","\n"), matcher.group(1), matcher.group(2));
                    adapter.addArticle(temp_article);
                }
                handler.sendEmptyMessage(0x124);
            }
        }.start();
        loadingTime++;
    }
}
