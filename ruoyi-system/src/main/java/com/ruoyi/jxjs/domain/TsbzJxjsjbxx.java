package com.ruoyi.jxjs.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;

/**
 * 见习教师基本信息对象 tsbz_jxjsjbxx
 *
 * @author ruoyi
 * @date 2020-08-20
 */
public class TsbzJxjsjbxx extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 标识
     */
    //@Excel(name = "编号", cellType = ColumnType.NUMERIC, prompt = "编号")
    private Long id;

    /**
     * 进修编号
     */
    @Excel(name = "进修编号", type = Type.IMPORT)
    private String jxbh;

    /**
     * 其他系统id
     */
    //@Excel(name = "其他系统id")
    private String otherid;

    /**
     * 基地校
     */
    @Excel(name = "基地校", type = Type.IMPORT)
    private String jdxid;

    /**
     * 姓名
     */
    @Excel(name = "姓名", type = Type.IMPORT)
    private String name;

    /**
     * 聘任单位名称
     */
    @Excel(name = "拟聘用单位")
    private String prdwmc;

    /**
     * 任教学段
     */
    //@Excel(name = "任教学段")
    private String rjxd;

    /**
     * 任教学科
     */
    //@Excel(name = "任教学科")
    private String rjxk;

    /**
     * 任教年级
     */
    //@Excel(name = "任教年级")
    private String rjnj;

    /**
     * 联系电话
     */
    //@Excel(name = "联系电话")
    private String phone;

    /**
     * 性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String xb;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date csrq;

    /**
     * 学位
     */
    //@Excel(name = "学位")
    private String xw;

    /**
     * 毕业院校
     */
    //@Excel(name = "毕业院校")
    private String byyx;


    /**
     * 电子邮件
     */
    //@Excel(name = "电子邮件")
    private String email;

    /**
     * 邮编
     */
    //@Excel(name = "邮编")
    private String yzbm;

    /**
     * 政治面貌
     */
    //@Excel(name = "政治面貌")
    private String zzmm;

    /**
     * 民族
     */
    //@Excel(name = "民族")
    private String mz;

    /**
     * 聘任单位
     */
    //@Excel(name = "聘任单位")
    private String prdwid;

    /**
     * 学历
     */
    //@Excel(name = "学历")
    private String xl;

    /**
     * 是否师范生
     */
    //@Excel(name = "是否师范生")
    private String sfsfs;

    /**
     * 录取年份
     */
    //@Excel(name = "录取年份")
    private String lqnf;

    private String faid;
    private String nf;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOtherid(String otherid) {
        this.otherid = otherid;
    }

    public String getOtherid() {
        return otherid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setJxbh(String jxbh) {
        this.jxbh = jxbh;
    }

    public String getJxbh() {
        return jxbh;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getXb() {
        return xb;
    }

    public void setCsrq(Date csrq) {
        this.csrq = csrq;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getZzmm() {
        return zzmm;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getMz() {
        return mz;
    }

    public void setPrdwid(String prdwid) {
        this.prdwid = prdwid;
    }

    public String getPrdwid() {
        return prdwid;
    }

    public void setPrdwmc(String prdwmc) {
        this.prdwmc = prdwmc;
    }

    public String getPrdwmc() {
        return prdwmc;
    }

    public void setJdxid(String jdxid) {
        this.jdxid = jdxid;
    }

    public String getJdxid() {
        return jdxid;
    }

    public void setRjxd(String rjxd) {
        this.rjxd = rjxd;
    }

    public String getRjxd() {
        return rjxd;
    }

    public void setRjxk(String rjxk) {
        this.rjxk = rjxk;
    }

    public String getRjxk() {
        return rjxk;
    }

    public void setRjnj(String rjnj) {
        this.rjnj = rjnj;
    }

    public String getRjnj() {
        return rjnj;
    }

    public void setByyx(String byyx) {
        this.byyx = byyx;
    }

    public String getByyx() {
        return byyx;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXl() {
        return xl;
    }

    public void setXw(String xw) {
        this.xw = xw;
    }

    public String getXw() {
        return xw;
    }

    public void setSfsfs(String sfsfs) {
        this.sfsfs = sfsfs;
    }

    public String getSfsfs() {
        return sfsfs;
    }

    public void setLqnf(String lqnf) {
        this.lqnf = lqnf;
    }

    public String getLqnf() {
        return lqnf;
    }

    public String getFaid() {
        return faid;
    }

    public void setFaid(String faid) {
        this.faid = faid;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("otherid", getOtherid())
                .append("name", getName())
                .append("jxbh", getJxbh())
                .append("xb", getXb())
                .append("csrq", getCsrq())
                .append("email", getEmail())
                .append("phone", getPhone())
                .append("yzbm", getYzbm())
                .append("zzmm", getZzmm())
                .append("mz", getMz())
                .append("prdwid", getPrdwid())
                .append("prdwmc", getPrdwmc())
                .append("jdxid", getJdxid())
                .append("rjxd", getRjxd())
                .append("rjxk", getRjxk())
                .append("rjnj", getRjnj())
                .append("byyx", getByyx())
                .append("xl", getXl())
                .append("xw", getXw())
                .append("sfsfs", getSfsfs())
                .append("lqnf", getLqnf())
                .append("createTime", getCreateTime())
                .append("faid", getFaid())
                .append("nf", getNf())
                .toString();
    }
}
