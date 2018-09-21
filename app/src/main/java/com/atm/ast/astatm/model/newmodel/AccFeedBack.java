package com.atm.ast.astatm.model.newmodel;

public class AccFeedBack {
    private int Id;
    private String Text;
    private int ParentId;
    private String Group;

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }


    public String getText() {
        return this.Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }


    public int getParentId() {
        return this.ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }


    public String getGroup() {
        return this.Group;
    }

    public void setGroup(String Group) {
        this.Group = Group;
    }
}
