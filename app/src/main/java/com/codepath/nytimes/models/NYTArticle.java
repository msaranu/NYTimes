package com.codepath.nytimes.models;

/**
 * Created by Saranu on 3/15/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NYTArticle implements Parcelable {


    @SerializedName("web_url")
    @Expose
    private String webUrl;

    @SerializedName("snippet")
    @Expose
    private String snippet;


    @SerializedName("lead_paragraph")
    @Expose
    private String leadParagraph;


    @SerializedName("source")
    @Expose
    private String source;


    @SerializedName("multimedia")
    @Expose
    private List<NYTArticleMultiMedia> multimedia = null;


    @SerializedName("headline")
    @Expose
    private NYTArticleHeadLine headline;

    @SerializedName("keywords")
    @Expose
    private List<NYTArticleKeyword> keywords = null;

    @SerializedName("pub_date")
    @Expose
    private String pubDate;

    @SerializedName("document_type")
    @Expose
    private String documentType;

    @SerializedName("news_desk")
    @Expose
    private String newsDesk;

    @SerializedName("section_name")
    @Expose
    private String sectionName;

    @SerializedName("subsection_name")
    @Expose
    private String subsectionName;

    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("word_count")
    @Expose
    private String wordCount;

    @SerializedName("slideshow_credits")
    @Expose
    private Object slideshowCredits;

    protected NYTArticle(Parcel in) {
        webUrl = in.readString();
        snippet = in.readString();
        leadParagraph = in.readString();
        source = in.readString();
        multimedia = in.createTypedArrayList(NYTArticleMultiMedia.CREATOR);
        headline = in.readParcelable(NYTArticleHeadLine.class.getClassLoader());
        keywords = in.createTypedArrayList(NYTArticleKeyword.CREATOR);
        pubDate = in.readString();
        documentType = in.readString();
        newsDesk = in.readString();
        sectionName = in.readString();
        subsectionName = in.readString();
        typeOfMaterial = in.readString();
        id = in.readString();
        wordCount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(snippet);
        dest.writeString(leadParagraph);
        dest.writeString(source);
        dest.writeTypedList(multimedia);
        dest.writeParcelable(headline, flags);
        dest.writeTypedList(keywords);
        dest.writeString(pubDate);
        dest.writeString(documentType);
        dest.writeString(newsDesk);
        dest.writeString(sectionName);
        dest.writeString(subsectionName);
        dest.writeString(typeOfMaterial);
        dest.writeString(id);
        dest.writeString(wordCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NYTArticle> CREATOR = new Creator<NYTArticle>() {
        @Override
        public NYTArticle createFromParcel(Parcel in) {
            return new NYTArticle(in);
        }

        @Override
        public NYTArticle[] newArray(int size) {
            return new NYTArticle[size];
        }
    };

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<NYTArticleMultiMedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<NYTArticleMultiMedia> multimedia) {
        this.multimedia = multimedia;
    }

    public NYTArticleHeadLine getHeadline() {
        return headline;
    }

    public void setHeadline(NYTArticleHeadLine headline) {
        this.headline = headline;
    }

    public List<NYTArticleKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<NYTArticleKeyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public void setSubsectionName(String subsectionName) {
        this.subsectionName = subsectionName;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public Object getSlideshowCredits() {
        return slideshowCredits;
    }

    public void setSlideshowCredits(Object slideshowCredits) {
        this.slideshowCredits = slideshowCredits;
    }
}