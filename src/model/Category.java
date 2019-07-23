package model;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private String description;
    private Category mainCategory;
    private int besteScore;
    private Object o;


    public Category(String name, String description) {
        this.setName(name);
        this.setDescription(description);
        this.besteScore = -1;
    }

    public Category(String name, String description, Category mainCategory) {
        this(name,description);
        this.setMainCategory(mainCategory);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    private void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    public int getBesteScore() {
        return besteScore;
    }

    public void setBesteScore(int besteScore) {
        this.besteScore = besteScore;
    }

    @Override
    public String toString() {
       String out = name;
       return out;
    }

    @Override
    public boolean equals(Object o) {
        try {
            return (o.hashCode() == this.hashCode());
        }catch (NullPointerException e){
            return false;
        }

    }

    @Override
    public int hashCode(){
        return name.hashCode()*31;
    }
}
