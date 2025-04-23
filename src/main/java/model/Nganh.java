package model;

public class Nganh {
    private String maNganh;
    private String tenNganh;
    private String loaiNganh;

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public String getLoaiNganh() {
        return loaiNganh;
    }

    public void setLoaiNganh(String loaiNganh) {
        this.loaiNganh = loaiNganh;
    }

    @Override
    public String toString() {
        return "Nganh [maNganh=" + maNganh + ", tenNganh=" + tenNganh + "]";
    }
}