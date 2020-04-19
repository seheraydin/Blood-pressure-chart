package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmResults;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //1: Realm nesnesi oluştur
    Realm realm;

    //9: ilgili view elemanlarını tanımlayalım
    EditText buyukTaansiyonEditText, kucukTansiyonEditText;
    Button btnEkle;
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //2: realm tanımlama işlemini yapalım
        realm = Realm.getDefaultInstance();
        //11:tanımla fonk çağır
        tanimla();
        //16: DB ye verileri ekleme fonk
        ekle();
        //25: verileri grafikte göster
        goster();
        //26: log dan DB dekideğerlere bak
        listele();
        //30: ilk kayıtı sil
        //sil();


    }

    //10: ilgili taanımlamaları yapmak için tanımla fonk. oluştur
    public void tanimla() {
        buyukTaansiyonEditText = findViewById(R.id.idTxtBuyukTansiyon);
        kucukTansiyonEditText = findViewById(R.id.idTxtKucukTansiyon);
        btnEkle = findViewById(R.id.idBtnEkle);
        chart = findViewById(R.id.idBarChart);
    }

    //12:butona tıklandığında ekleme işlemi yapalım
    public void ekle() {
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //13: verilerin hepsinin aynı anda eklenmesi için transaction kullandık
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //14:tablo sınıfımızın nesnesini oluşturalım
                        Tansiyon tns = realm.createObject(Tansiyon.class);
                        //15: nesne oluşturulduktan sonra ilgili set işlemlerini yapalım
                        //bu şekilde veri tabanına ekleyeceğiz
                        tns.setBuyukTansiyon(buyukTaansiyonEditText.getText().toString());
                        tns.setKucuktansiyon(kucukTansiyonEditText.getText().toString());


                    }
                });

                //19: butona tıklandıktan sonra eklenen veriler log da listelensin
                listele();
                //28: chartımızı yenileyelim ve gösterelim
                chart.invalidate();
                goster();

            }
        });
    }

    //17: eklediğiimiz kayıtları göstermek için fonk. oluştur
    public void listele() {
        //18: DB deki Tansiyon tablomuzdaki bütün değişkenleri list değişkenine ata
        RealmResults<Tansiyon> list = realm.where(Tansiyon.class).findAll();
        for (Tansiyon t : list) {
            Log.i("ekleme", t.toString());
        }
    }

    //20: eklenen kayıtların grafik arayüzünde gösterilmesi işlemi
    public void goster() {
        //21:DB deki kayıtları çekelim
        RealmResults<Tansiyon> kisi = realm.where(Tansiyon.class).findAll();

        Float buyukTansiyon = 0.f;
        Float kucukTansiyon = 0.f;
        //27: numara 27 den sonra örnek2
        //DB deki elemanları ügrafte üzerine ekleyerek göstersin
        for (int i = 0; i < kisi.size(); i++) {
            buyukTansiyon = buyukTansiyon + Float.parseFloat(kisi.get(i).getBuyukTansiyon());
            kucukTansiyon = kucukTansiyon + Float.parseFloat(kisi.get(i).getKucuktansiyon());
        }

        //22: bir tane arraylist oluşturalım
        //bu arraylist BarEntry veri tipinde olmalı çünkü
        //bu arraylisti grafiğe ekleyeceğiz
        ArrayList<BarEntry> arrayList = new ArrayList<>();


        arrayList.add(new BarEntry(buyukTansiyon, 0));
        arrayList.add(new BarEntry(kucukTansiyon, 1));
        BarDataSet barDataSet = new BarDataSet(arrayList, "Toplam Değer");

        //23: sütun isimlerini girmemiz gerekiyor
        ArrayList<String> sutunIsmi = new ArrayList<>();
        sutunIsmi.add("Büyük Tansiyon");
        sutunIsmi.add("Küçük Tansiyon");

        //24:BarCaharta set edeceğimiz datayı oluşturalım
        BarData barData = new BarData(sutunIsmi, barDataSet);
        chart.setData(barData);
        chart.setDescription("Tansiyon Değerlerini Gösteren Grafik Arayüzüdür.");
    }

}
