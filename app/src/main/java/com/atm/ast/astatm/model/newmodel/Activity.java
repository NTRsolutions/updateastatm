package com.atm.ast.astatm.model.newmodel;

public class Activity {
    private long ActivityId;

    private String ActivityName;

    private Reason[] Reason;

    public long getActivityId() {
        return ActivityId;
    }

    public void setActivityId(long activityId) {
        ActivityId = activityId;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public com.atm.ast.astatm.model.newmodel.Reason[] getReason() {
        return Reason;
    }

    public void setReason(com.atm.ast.astatm.model.newmodel.Reason[] reason) {
        Reason = reason;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ActivityId = "+ActivityId+", ActivityName = "+ActivityName+", Reason = "+Reason+"]";
    }
}
