package hu.gabornovak.rssreader.logic.model;

/**
 * Created by gnovak on 7/26/2016.
 */
public class RssItem {
    private final String title;
    private final String description;
    private final String link;
    private final String pubDate;
    private final String imageUrl;
    private boolean isVisited;

    public RssItem(String title, String description, String link, String pubDate, String imageUrl) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
