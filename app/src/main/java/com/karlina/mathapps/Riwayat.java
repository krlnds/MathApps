package com.karlina.mathapps;

public class Riwayat {

    int pertama;
    int kedua;
    double hasil;
    String operator;

    public Riwayat(int pertama, int kedua, double hasil, String operator) {
        this.pertama = pertama;
        this.kedua = kedua;
        this.hasil = hasil;
        this.operator = operator;
    }

    public int getPertama() {
        return pertama;
    }

    public void setPertama(int pertama) {
        this.pertama = pertama;
    }

    public int getKedua() {
        return kedua;
    }

    public void setKedua(int kedua) {
        this.kedua = kedua;
    }

    public double getHasil() {
        return hasil;
    }

    public void setHasil(double hasil) {
        this.hasil = hasil;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


}
