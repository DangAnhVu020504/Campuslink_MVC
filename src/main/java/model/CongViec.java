package model;

import java.sql.Date;

public class CongViec {
    private String soCMND;
    private Date ngayVaoCongTy;
    private String maNganh;
    private String tenCongViec;
    private String tenCongTy;
    private String diaChiCongTy;
    private int thoiGianLamViec;

    public CongViec() {}

    public CongViec(String soCMND, Date ngayVaoCongTy, String maNganh, String tenCongViec, 
                    String tenCongTy, String diaChiCongTy, int thoiGianLamViec) {
        this.soCMND = soCMND;
        this.ngayVaoCongTy = ngayVaoCongTy;
        this.maNganh = maNganh;
        this.tenCongViec = tenCongViec;
        this.tenCongTy = tenCongTy;
        this.diaChiCongTy = diaChiCongTy;
        this.thoiGianLamViec = thoiGianLamViec;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public Date getNgayVaoCongTy() {
        return ngayVaoCongTy;
    }

    public void setNgayVaoCongTy(Date ngayVaoCongTy) {
        this.ngayVaoCongTy = ngayVaoCongTy;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }

    public String getDiaChiCongTy() {
        return diaChiCongTy;
    }

    public void setDiaChiCongTy(String diaChiCongTy) {
        this.diaChiCongTy = diaChiCongTy;
    }

    public int getThoiGianLamViec() {
        return thoiGianLamViec;
    }

    public void setThoiGianLamViec(int thoiGianLamViec) {
        this.thoiGianLamViec = thoiGianLamViec;
    }

    @Override
    public String toString() {
        return "CongViec{" +
               "soCMND='" + (soCMND != null ? soCMND : "N/A") + '\'' +
               ", ngayVaoCongTy=" + (ngayVaoCongTy != null ? ngayVaoCongTy.toString() : "N/A") +
               ", maNganh='" + (maNganh != null ? maNganh : "N/A") + '\'' +
               ", tenCongViec='" + (tenCongViec != null ? tenCongViec : "N/A") + '\'' +
               ", tenCongTy='" + (tenCongTy != null ? tenCongTy : "N/A") + '\'' +
               ", diaChiCongTy='" + (diaChiCongTy != null ? diaChiCongTy : "N/A") + '\'' +
               ", thoiGianLamViec=" + thoiGianLamViec +
               '}';
    }
}