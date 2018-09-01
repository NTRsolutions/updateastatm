package com.atm.ast.astatm.model.newmodel;

public class Header {

    private long TotalSites;
    private long NoncomSites;
    private long AlarmSites;
    private long LowBatterySies;
    private long INVSites;
    private long NSMSies;

    public long getTotalSites() {
        return TotalSites;
    }

    public void setTotalSites(long totalSites) {
        TotalSites = totalSites;
    }

    public long getNoncomSites() {
        return NoncomSites;
    }

    public void setNoncomSites(long noncomSites) {
        NoncomSites = noncomSites;
    }

    public long getAlarmSites() {
        return AlarmSites;
    }

    public void setAlarmSites(long alarmSites) {
        AlarmSites = alarmSites;
    }

    public long getLowBatterySies() {
        return LowBatterySies;
    }

    public void setLowBatterySies(long lowBatterySies) {
        LowBatterySies = lowBatterySies;
    }

    public long getINVSites() {
        return INVSites;
    }

    public void setINVSites(long INVSites) {
        this.INVSites = INVSites;
    }

    public long getNSMSies() {
        return NSMSies;
    }

    public void setNSMSies(long NSMSies) {
        this.NSMSies = NSMSies;
    }

//----------------------getplanentry api--------------
    private long AttendanceCount;
    private String ReachedSite;
    private String OnTheWay;
    private long LeaveCount;
    private String LeftSite;
    private String Unknown;
    private long Planned;
    private long Executed;
    private long WorkingCount;
    private String Circle;

    public String getReachedSite() {
        return ReachedSite;
    }

    public void setReachedSite(String reachedSite) {
        ReachedSite = reachedSite;
    }

    public String getOnTheWay() {
        return OnTheWay;
    }

    public void setOnTheWay(String onTheWay) {
        OnTheWay = onTheWay;
    }

    public String getLeftSite() {
        return LeftSite;
    }

    public void setLeftSite(String leftSite) {
        LeftSite = leftSite;
    }

    public String getUnknown() {
        return Unknown;
    }

    public void setUnknown(String unknown) {
        Unknown = unknown;
    }

    public String getCircle() {
        return Circle;
    }

    public void setCircle(String circle) {
        Circle = circle;
    }

    public long getAttendanceCount() {
        return AttendanceCount;
    }

    public void setAttendanceCount(long attendanceCount) {
        AttendanceCount = attendanceCount;
    }

    public long getLeaveCount() {
        return LeaveCount;
    }

    public void setLeaveCount(long leaveCount) {
        LeaveCount = leaveCount;
    }

    public long getPlanned() {
        return Planned;
    }

    public void setPlanned(long planned) {
        Planned = planned;
    }

    public long getExecuted() {
        return Executed;
    }

    public void setExecuted(long executed) {
        Executed = executed;
    }

    public long getWorkingCount() {
        return WorkingCount;
    }

    public void setWorkingCount(long workingCount) {
        WorkingCount = workingCount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [AttendanceCount = "+AttendanceCount+", ReachedSite = "+ReachedSite+", OnTheWay = "+OnTheWay+", LeaveCount = "+LeaveCount+", LeftSite = "+LeftSite+", Unknown = "+Unknown+", Planned = "+Planned+", Executed = "+Executed+", WorkingCount = "+WorkingCount+", Circle = "+Circle+"]";
    }
}
