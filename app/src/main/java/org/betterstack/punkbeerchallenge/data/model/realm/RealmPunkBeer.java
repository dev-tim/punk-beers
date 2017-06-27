package org.betterstack.punkbeerchallenge.data.model.realm;

import org.betterstack.punkbeerchallenge.data.model.entity.PunkBeer;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

public class RealmPunkBeer extends RealmObject {

    @PrimaryKey
    private Long id;

    @Index
    private String name;

    private String tagline;

    private String imageUrl;

    private Double abv;


    public RealmPunkBeer() {
    }

    public RealmPunkBeer(Long id, String name, String tagline, String imageUrl, Double abv) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.imageUrl = imageUrl;
        this.abv = abv;
    }

    public PunkBeer toPunkEntity(){
        return new PunkBeer(id, name, tagline, imageUrl, abv);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealmPunkBeer that = (RealmPunkBeer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tagline != null ? !tagline.equals(that.tagline) : that.tagline != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null)
            return false;
        return abv != null ? abv.equals(that.abv) : that.abv == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tagline != null ? tagline.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (abv != null ? abv.hashCode() : 0);
        return result;
    }
}
