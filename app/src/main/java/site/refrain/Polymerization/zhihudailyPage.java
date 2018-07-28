package site.refrain.Polymerization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import site.refrain.R;


public class zhihudailyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihudaily_page);

        Intent intent=getIntent();
        final String article_id=intent.getStringExtra("article_id");

        ImageView title_image=findViewById(R.id.title_image);
        WebView wv=findViewById(R.id.wv);

        LoadJsonThread loadjsonthread=new LoadJsonThread();
        loadjsonthread.setUrl("https://news-at.zhihu.com/api/4/news/"+article_id);
        loadjsonthread.start();
        try {
            loadjsonthread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String html=loadjsonthread.getPage();
        String image=loadjsonthread.getImage();
        Glide.with(getBaseContext())
                .load(image)
                .into(title_image);
        wv.loadData(html,"text/html","UTF-8");

    }

    public class LoadJsonThread extends Thread {
        private String url;
        StringBuilder json = new StringBuilder();
        public void setUrl(String url){
            this.url=url;
        }
        public void run() {
            try
            {
                URL urlObject = new URL(url);
                URLConnection uc = urlObject.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    json.append(inputLine);
                }
                in.close();
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        public String getPage(){
            zhihudailyBean json_html = JSON.parseObject( json.toString(), zhihudailyBean.class);
            return "<html> " + "<head> " + "<link href=\""+json_html.getCss().get(0)+"\" rel=\"stylesheet\" type=\"text/css\"/> </head>"+json_html.getBody();

        }
        public String getImage(){
            zhihudailyBean json_html = JSON.parseObject( json.toString(), zhihudailyBean.class);
            return json_html.getImage();
        }

    }
}
