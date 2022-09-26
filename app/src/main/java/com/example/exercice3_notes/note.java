package com.example.exercice3_notes;

import java.util.List;

public class note {
    private String name;
    private String body;


    public note(String name, String body){
        this.name = name;
        this.body = body;
    }

    public String GetName(){
        return this.name;
    }

    public String GetBody(){
        return this.body;
    }
}
