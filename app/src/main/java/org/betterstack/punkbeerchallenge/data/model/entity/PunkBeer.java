package org.betterstack.punkbeerchallenge.data.model.entity;

import com.google.gson.annotations.SerializedName;

import org.betterstack.punkbeerchallenge.data.model.realm.RealmPunkBeer;

public class PunkBeer {

    private Long id;

    private String name;

    private String tagline;

    @SerializedName("image_url")
    private String imageUrl;

    private Double abv;

    public PunkBeer() {
    }

    public PunkBeer(Long id, String name, String tagline, String imageUrl, Double abv) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.imageUrl = imageUrl;
        this.abv = abv;
    }

    public RealmPunkBeer toRealmObject(){
        return new RealmPunkBeer(id, name, tagline, imageUrl, abv);
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

        PunkBeer punkBeer = (PunkBeer) o;

        if (id != null ? !id.equals(punkBeer.id) : punkBeer.id != null) return false;
        if (name != null ? !name.equals(punkBeer.name) : punkBeer.name != null) return false;
        if (tagline != null ? !tagline.equals(punkBeer.tagline) : punkBeer.tagline != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(punkBeer.imageUrl) : punkBeer.imageUrl != null)
            return false;
        return abv != null ? abv.equals(punkBeer.abv) : punkBeer.abv == null;
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