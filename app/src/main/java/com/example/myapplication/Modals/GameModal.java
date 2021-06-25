package com.example.myapplication.Modals;

import java.io.Serializable;

public class GameModal implements Serializable {
    private String id,title,pic;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPic() {
        return pic;
    }

    public GameModal(String id, String title, String pic) {
        this.id = id;
        this.title = title;
        this.pic = pic;
    }
}
