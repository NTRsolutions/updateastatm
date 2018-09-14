package com.atm.ast.astatm.model.newmodel;

import com.atm.ast.astatm.FNObject;

import java.util.ArrayList;

public class Data extends FNObject {
    private String SiteName;
    private String CustomerSiteId;
    private long SiteId;
    private District[] District;
    private long CircleId;
    private String Circle;

    public Data() {
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String SiteName) {
        this.SiteName = SiteName;
    }

    public String getCustomerSiteId() {
        return CustomerSiteId;
    }

    public void setCustomerSiteId(String CustomerSiteId) {
        this.CustomerSiteId = CustomerSiteId;
    }

    public long getSiteId() {
        return SiteId;
    }

    public void setSiteId(long SiteId) {
        this.SiteId = SiteId;
    }

    private int siteStatus;

    public int getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(int siteStatus) {
        this.siteStatus = siteStatus;
    }

    public District[] getDistrict() {
        return District;
    }

    public void setDistrict(District[] district) {
        District = district;
    }

    public long getCircleId() {
        return CircleId;
    }

    public void setCircleId(long circleId) {
        CircleId = circleId;
    }

    public String getCircle() {
        return Circle;
    }

    public void setCircle(String circle) {
        Circle = circle;
    }

    /**
     * use for Circle API
     *
     * @return
     */
    private long ciid;
    private String co;
    private long civ;
    private String chp;
    private String cin;
    private long cd;

    public long getCiid() {
        return ciid;
    }

    public void setCiid(long ciid) {
        this.ciid = ciid;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public long getCiv() {
        return civ;
    }

    public void setCiv(long civ) {
        this.civ = civ;
    }

    public String getChp() {
        return chp;
    }

    public void setChp(String chp) {
        this.chp = chp;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public long getCd() {
        return cd;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }

    //--------------- site Search Data Variable key-----------------
    private double BaseLocationLat;
    private long BaseLocationId;
    private double BaseDistance;
    private double Lat;
    private long ClientId;
    private String BaseLocation;
    private double Lon;
    private String Client;
    private double BaseLocationLon;


    public double getBaseLocationLat() {
        return BaseLocationLat;
    }

    public void setBaseLocationLat(double baseLocationLat) {
        BaseLocationLat = baseLocationLat;
    }

    public long getBaseLocationId() {
        return BaseLocationId;
    }

    public void setBaseLocationId(long baseLocationId) {
        BaseLocationId = baseLocationId;
    }

    public double getBaseDistance() {
        return BaseDistance;
    }

    public void setBaseDistance(double baseDistance) {
        BaseDistance = baseDistance;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public long getClientId() {
        return ClientId;
    }

    public void setClientId(long clientId) {
        ClientId = clientId;
    }

    public String getBaseLocation() {
        return BaseLocation;
    }

    public void setBaseLocation(String baseLocation) {
        BaseLocation = baseLocation;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public double getBaseLocationLon() {
        return BaseLocationLon;
    }

    public void setBaseLocationLon(double baseLocationLon) {
        BaseLocationLon = baseLocationLon;
    }

    //----------use in CustomerData Api------------
    private String ctn;
    private int ct;

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public int getCt() {
        return ct;
    }

    public void setCt(int ct) {
        this.ct = ct;
    }

    //-------------use in site detail APi ------------------
    private String dt;
    private double bta;
    private String ca;
    private String sst;
    private double bda;
    private double la;
    private double btv;
    private double iov;
    private double soa;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public double getBta() {
        return bta;
    }

    public void setBta(double bta) {
        this.bta = bta;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getSst() {
        return sst;
    }

    public void setSst(String sst) {
        this.sst = sst;
    }

    public double getBda() {
        return bda;
    }

    public void setBda(double bda) {
        this.bda = bda;
    }

    public double getLa() {
        return la;
    }

    public void setLa(double la) {
        this.la = la;
    }

    public double getBtv() {
        return btv;
    }

    public void setBtv(double btv) {
        this.btv = btv;
    }

    public double getIov() {
        return iov;
    }

    public void setIov(double iov) {
        this.iov = iov;
    }

    public double getSoa() {
        return soa;
    }

    public void setSoa(double soa) {
        this.soa = soa;
    }

    //----------------SiteVisit screen api---------
    private String NewEquipmentInstalledMake;

    private String FEName;

    private String FaultEquipmentMake;

    private String VisitDate;

    public String getNewEquipmentInstalledMake() {
        return NewEquipmentInstalledMake;
    }

    public void setNewEquipmentInstalledMake(String NewEquipmentInstalledMake) {
        this.NewEquipmentInstalledMake = NewEquipmentInstalledMake;
    }

    public String getFEName() {
        return FEName;
    }

    public void setFEName(String FEName) {
        this.FEName = FEName;
    }

    public String getFaultEquipmentMake() {
        return FaultEquipmentMake;
    }

    public void setFaultEquipmentMake(String FaultEquipmentMake) {
        this.FaultEquipmentMake = FaultEquipmentMake;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String VisitDate) {
        this.VisitDate = VisitDate;
    }

    //-------------EBRun hour screen api use----------
    private String Date;
    private String EBRH;
    private String DGRH;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getEBRH() {
        return EBRH;
    }

    public void setEBRH(String EBRH) {
        this.EBRH = EBRH;
    }

    public String getDGRH() {
        return DGRH;
    }

    public void setDGRH(String DGRH) {
        this.DGRH = DGRH;
    }

    //----------------cluster screen api use------------
    private long dd;
    private long dv;
    private String dmp;
    private long did;
    private String dn;

    public long getDd() {
        return dd;
    }

    public void setDd(long dd) {
        this.dd = dd;
    }

    public long getDv() {
        return dv;
    }

    public void setDv(long dv) {
        this.dv = dv;
    }

    public String getDmp() {
        return dmp;
    }

    public void setDmp(String dmp) {
        this.dmp = dmp;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    //--------Site screen api-----
    private String stv;
    private long sid;
    private String it;
    private String stc;
    private String std;
    private String stn;
    private String stp;
    private String cln;

    public String getStv() {
        return stv;
    }

    public void setStv(String stv) {
        this.stv = stv;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getIt() {
        return it;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public String getStc() {
        return stc;
    }

    public void setStc(String stc) {
        this.stc = stc;
    }

    public String getStd() {
        return std;
    }

    public void setStd(String std) {
        this.std = std;
    }

    public String getStn() {
        return stn;
    }

    public void setStn(String stn) {
        this.stn = stn;
    }

    public String getStp() {
        return stp;
    }

    public void setStp(String stp) {
        this.stp = stp;
    }

    public String getCln() {
        return cln;
    }

    public void setCln(String cln) {
        this.cln = cln;
    }

    //----------------------------getactivitydropdown api-------------
    private Activity[] Activity;
    private long TaskId;
    private String TaskName;
    public Activity[] getActivity() {
        return Activity;
    }

    public void setActivity(Activity[] activity) {
        Activity = activity;
    }

    public long getTaskId() {
        return TaskId;
    }

    public void setTaskId(long taskId) {
        TaskId = taskId;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    //------------------------getplanentry api------------------
    private long PlanId;
    private long ActivityId;
    private String remark;
    private long FEId;
    private String PlanDate;
    private String md;
    private String Task;
    private String ComplaintMessage;
    private String ActivityName;

    public long getPlanId() {
        return PlanId;
    }

    public void setPlanId(long planId) {
        PlanId = planId;
    }

    public long getActivityId() {
        return ActivityId;
    }

    public void setActivityId(long activityId) {
        ActivityId = activityId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getFEId() {
        return FEId;
    }

    public void setFEId(long FEId) {
        this.FEId = FEId;
    }

    public String getPlanDate() {
        return PlanDate;
    }

    public void setPlanDate(String planDate) {
        PlanDate = planDate;
    }

    public String getMd() {
        return md;
    }

    public void setMd(String md) {
        this.md = md;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public String getComplaintMessage() {
        return ComplaintMessage;
    }

    public void setComplaintMessage(String complaintMessage) {
        ComplaintMessage = complaintMessage;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public EquipmnetContentData equipmnetContentData;
    public int id;


    public EquipmnetContentData getEquipmnetContentData() {
        return equipmnetContentData;
    }

    public void setEquipmnetContentData(EquipmnetContentData equipmnetContentData) {
        this.equipmnetContentData = equipmnetContentData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassPojo [SiteName = " + SiteName + ", CustomerSiteId = " + CustomerSiteId + ", SiteId = " + SiteId + "]";
    }

}


