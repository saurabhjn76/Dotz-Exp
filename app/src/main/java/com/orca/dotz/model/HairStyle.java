package com.orca.dotz.model;

/**
 * Created by master on 15/6/16.
 */
public class HairStyle {
    private String Name = "NA";
    private String Image = "NA";
    private String Length = "NA";
    private boolean liked = false;
    private boolean cart = false;
    private String parent;
    private String Likes = "NA";

    public boolean isCart() {
        return cart;
    }

    public void setCart(boolean cart) {
        this.cart = cart;
    }

    public String getNumLikes() {
        return Likes;
    }

    public void setNumLikes(String numLikes) {
        this.Likes = numLikes;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLength() {
        return Length;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
