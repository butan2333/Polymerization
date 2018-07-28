package site.refrain.Polymerization;


public class Article {


    //文章私有变量，用到的有标题，文章id，文章缩略图
    private String title;
    private String id;
    private String titleImg;

    public Article(String title,String titleImg,String id){
        this.title=title;
        this.id=id;
        char[] before_img_URL=titleImg.toCharArray(),after_img_URL=new char[titleImg.length()-3];

        for(int i=0,j=0;i<before_img_URL.length;i++){
            if(before_img_URL[i]!='\\') {
                after_img_URL[j] = before_img_URL[i];
                j++;
            }
        }
        this.titleImg=new String(after_img_URL);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getTitleImg() {
        return titleImg;
    }
}
