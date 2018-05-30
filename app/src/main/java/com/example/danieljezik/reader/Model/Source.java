package com.example.danieljezik.reader.Model;

import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    /**
     * Konstuktor tela Article, source ma viac parametrov
     *
     * @param id id zdroja
     * @param name meno zdroja
     */
    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
