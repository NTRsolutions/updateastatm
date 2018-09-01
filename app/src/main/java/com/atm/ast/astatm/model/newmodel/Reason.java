package com.atm.ast.astatm.model.newmodel;

public class Reason {
    private String Text;
    private long Id;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Text = "+Text+", Id = "+Id+"]";
    }
}
