package site.refrain.Polymerization;

import java.io.Serializable;
import java.util.List;

public class zhihudailyBean implements Serializable {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String js;
    private String ga_prefix;
    private List<String> section;
    private int type;
    private int id;
    private List<String>  css;
    private List<String> images;

    public zhihudailyBean(String body, String image_source, String title, String image, String share_url, String js, String ga_prefix, List<String> section, int type, int id, List<String> css, List<String> images) {
        this.body = body;
        this.image_source = image_source;
        this.title = title;
        this.image = image;
        this.share_url = share_url;
        this.js = js;
        this.ga_prefix = ga_prefix;
        this.section = section;
        this.type = type;
        this.id = id;
        this.css = css;
        this.images=images;
    }
    public zhihudailyBean(){}
    public void setBody(String body) {
        this.body = body;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setSection(List<String> section) {
        this.section = section;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public void setImages(List<String> images){this.images=images;}

    public String getBody() {
        return body;
    }

    public String getImage_source() {
        return image_source;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getShare_url() {
        return share_url;
    }

    public String getJs() {
        return js;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public List<String> getSection() {
        return section;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<String> getCss() {
        return css;
    }
    public List<String> getImages() {
        return images;
    }


}
