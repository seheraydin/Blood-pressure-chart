package com.example.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

//5:Anotention eklemeliyiz
@RealmClass
//4: Veritabanı için gerekli tablomuzu oluşturmak için class oluşturduk
//bu class RealmObject ten kalıtılmalı
public class Tansiyon extends RealmObject {
    //6: tablo için alanlarımızı ekleyelim
    private String buyukTansiyon;
    private String kucuktansiyon;

    //7: tablo alanları için get ve set metotlarını oluştur
    //sağ tık-generate-getter and setter
    public String getBuyukTansiyon() {
        return buyukTansiyon;
    }

    public void setBuyukTansiyon(String buyukTansiyon) {
        this.buyukTansiyon = buyukTansiyon;
    }

    public String getKucuktansiyon() {
        return kucuktansiyon;
    }

    public void setKucuktansiyon(String kucuktansiyon) {
        this.kucuktansiyon = kucuktansiyon;
    }

    //8: toString metodunu override et
    //sağ tık-generate-toString

    @Override
    public String toString() {
        return "Tansiyon{" +
                "buyukTansiyon='" + buyukTansiyon + '\'' +
                ", kucuktansiyon='" + kucuktansiyon + '\'' +
                '}';
    }
}
