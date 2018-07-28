package site.refrain.Polymerization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import site.refrain.R;

public class ArticleAdapter extends BaseAdapter{
    private Context context;

    private List<Article> articleList=new ArrayList<Article>();
    public ArticleAdapter(Context context){

        this.context=context;
    }

    /*添加数据*/
    public void addArticle(Article article){

        articleList.add(article);
    }


    @Override
    public int getCount() {
        if(articleList!=null)
            return articleList.size();
        return 0;
    }

    @Override
    public Article getItem(int i) {
        return articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Article article =articleList.get(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.zhihudaily_list, parent, false);
            viewHolder=new ViewHolder();
            viewHolder.articleTitle=view.findViewById(R.id.title);
            viewHolder.articleImage=view.findViewById(R.id.imageView);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context).load(article.getTitleImg()).placeholder(R.drawable.loading).into(viewHolder.articleImage);
        viewHolder.articleTitle.setTextSize(18);
        viewHolder.articleTitle.setText(article.getTitle());
        return view;
    }



    class ViewHolder{
        TextView articleTitle;
        ImageView articleImage;
    }

}
