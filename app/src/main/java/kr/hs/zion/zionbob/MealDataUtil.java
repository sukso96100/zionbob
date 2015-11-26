package kr.hs.zion.zionbob;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by youngbin on 15. 11. 21.
 */
public class MealDataUtil {
    String TAG = "MealDataUtil";

    String provienceCode;
    String schoolCode;
    int schoolTypeA;
    String schoolTypeB;
    int mealType;
    String date;

    String URL = null;

    // Meal Data
    String[] Date = new String[7];
    String[] HeadCcount = new String[7];
    public String[] Meal = new String[7];
    // Origin of Ingredients Data
    public String[] RiceOrigin = new String[7];
    public String[] KimchiOrigin = new String[7];
    public String[] RedPepperOrigin = new String[7];
    public String[] BeefOrigin = new String[7];
    public String[] PorkOrigin = new String[7];
    public String[] ChickenOrigin = new String[7];
    public String[] DuckOrigin = new String[7];
    public String[] ProcessedBeefOrigin = new String[7];
    public String[] ProcessedPorkOrigin = new String[7];
    public String[] ProcessedChickenOrigin = new String[7];
    public String[] ProcessedDuckOrigin = new String[7];
    public String[] Notes = new String[7];
    // Nutrients Data
    public String[] Energy = new String[7];
    public String[] Carbohydrate = new String[7];
    public String[] Protein = new String[7];
    public String[] Fat = new String[7];
    public String[] VitaminA = new String[7];
    public String[] Thiamin = new String[7];
    public String[] Riboflavin = new String[7];
    public String[] VitaminC = new String[7];
    public String[] Calcium = new String[7];
    public String[] Iron = new String[7];

    boolean NOMEALROW = false;

    //ProvienceCode 시/도 교육청 코드
    // SchoolCode 학교 코드
    // SchoolTypeA 학교 종류 (1 병설유치원, 2 초등학교, 3 중학교, 4 고등학교)
    // SchoolTypeB 학교 종류 (01 병설유치원,0 2 초등학교, 03 중학교, 04 고등학교)
    // MealType 식사 종류 (1 조식, 2 중식, 3 석식)
    // Date 날짜 (예시 : 2015.11.21)
    public MealDataUtil(String ProvienceCode, String SchoolCode,
                        int SchoolTypeA, String SchoolTypeB,
                        int MealType, String Date){

        this.provienceCode = ProvienceCode;
        this.schoolCode = SchoolCode;
        this.schoolTypeA = SchoolTypeA;
        this.schoolTypeB = SchoolTypeB;
        this.mealType = MealType;
        this.date = Date;

        // Build URL
        this.URL = "http://hes."+ProvienceCode+".go.kr/sts_sci_md01_001.do?schulCode="+SchoolCode
                +"&schulCrseScCode="+SchoolTypeA+"&schulKndScCode="+SchoolTypeB+"&schMmealScCode="+MealType+"&schYmd="+Date;
    }

    public String getURL(){
        Log.d(TAG, URL);
        return this.URL;
    }

    public void parseData(String RawData){
        Document doc = Jsoup.parse(RawData);
        Log.d(TAG,RawData);
        Elements Table = doc.getElementsByClass("tbl_type3");
//        Log.d(TAG,Table.toString());
        Elements Trs = Table.get(0).getElementsByTag("tr");
        Log.d(TAG, Trs.toString());

        // Get Date
        for(int i=0; i<7; i++){
            Date[i] = Trs.get(0).getElementsByTag("th").get(i+1).text();
            Log.d(TAG, Date[i]);
        }

        // Get Headcount
        for(int i=0; i<7; i++){
            HeadCcount[i] = Trs.get(1).getElementsByClass("textC").get(i).text();
            Log.d(TAG, HeadCcount[i]);
        }

        // ERR! : When No Meal Row
        // Get Meal
        for(int i=0; i<7; i++){
            try{
                Meal[i] = Trs.get(2).getElementsByClass("textC").get(i).text();
                NOMEALROW = false;
            }catch (Exception e){
                Meal[i] = "";
                NOMEALROW = true;
            }

            Log.d(TAG, Meal[i]);
        }

        // Get RiceOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                RiceOrigin[i] = "";
                Log.d(TAG, RiceOrigin[i]);
            }else{
                RiceOrigin[i] = Trs.get(4).getElementsByClass("textC").get(i).text();
                Log.d(TAG, RiceOrigin[i]);
            }

        }

        // Get KimchiOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                KimchiOrigin[i] = "";
                Log.d(TAG, KimchiOrigin[i]);
            }else{
                KimchiOrigin[i] = Trs.get(5).getElementsByClass("textC").get(i).text();
                Log.d(TAG, KimchiOrigin[i]);
            }

        }

        // Get RedPepperOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                RedPepperOrigin[i] = "";
                Log.d(TAG, RedPepperOrigin[i]);
            }else{
                RedPepperOrigin[i] = Trs.get(6).getElementsByClass("textC").get(i).text();
                Log.d(TAG, RedPepperOrigin[i]);
            }

        }

        // Get BeefOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                BeefOrigin[i] = "";
                Log.d(TAG, BeefOrigin[i]);
            }else{
                BeefOrigin[i] = Trs.get(7).getElementsByClass("textC").get(i).text();
                Log.d(TAG, BeefOrigin[i]);
            }

        }

        // Get PorkOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                PorkOrigin[i] = "";
                Log.d(TAG, PorkOrigin[i]);
            }else{
                PorkOrigin[i] = Trs.get(8).getElementsByClass("textC").get(i).text();
                Log.d(TAG, PorkOrigin[i]);
            }

        }

        // Get ChickenOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                ChickenOrigin[i] = "";
                Log.d(TAG, ChickenOrigin[i]);
            }else{
                ChickenOrigin[i] = Trs.get(9).getElementsByClass("textC").get(i).text();
                Log.d(TAG, ChickenOrigin[i]);
            }

        }

        // Get DuckOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                DuckOrigin[i] = "";
                Log.d(TAG, DuckOrigin[i]);
            }else{
                DuckOrigin[i] = Trs.get(10).getElementsByClass("textC").get(i).text();
                Log.d(TAG, DuckOrigin[i]);
            }

        }

        // Get ProcessedBeefOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                ProcessedBeefOrigin[i] = "";
                Log.d(TAG, ProcessedBeefOrigin[i]);
            }else{
                ProcessedBeefOrigin[i] = Trs.get(11).getElementsByClass("textC").get(i).text();
                Log.d(TAG, ProcessedBeefOrigin[i]);
            }

        }

        // Get ProcessedPorkOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                ProcessedPorkOrigin[i] = "";
                Log.d(TAG, ProcessedPorkOrigin[i]);
            }else{
                ProcessedPorkOrigin[i] = Trs.get(12).getElementsByClass("textC").get(i).text();
                Log.d(TAG, ProcessedPorkOrigin[i]);
            }

        }

        // Get ProcessedChickenOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                ProcessedChickenOrigin[i] = "";
                Log.d(TAG, ProcessedChickenOrigin[i]);
            }else{
                ProcessedChickenOrigin[i] = Trs.get(13).getElementsByClass("textC").get(i).text();
                Log.d(TAG, ProcessedChickenOrigin[i]);
            }

        }

        // Get ProcessedDuckOrigin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                ProcessedDuckOrigin[i] = "";
                Log.d(TAG, ProcessedDuckOrigin[i]);
            }else{
                ProcessedDuckOrigin[i] = Trs.get(14).getElementsByClass("textC").get(i).text();
                Log.d(TAG, ProcessedDuckOrigin[i]);
            }

        }

        // Get notes
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Notes[i] = "";
                Log.d(TAG, Notes[i]);
            }else{
                Notes[i] = Trs.get(15).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Notes[i]);
            }

        }

        // Get Energy
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Energy [i] = "";
                Log.d(TAG, Energy [i]);
            }else{
                Energy [i] = Trs.get(17).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Energy [i]);
            }

        }

        // Get Carbohydrate
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Carbohydrate[i] = "";
                Log.d(TAG, Carbohydrate[i]);
            }else{
                Carbohydrate[i] = Trs.get(18).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Carbohydrate[i]);
            }

        }

        // Get Protein
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Protein[i] = "";
                Log.d(TAG, Protein[i]);
            }else{
                Protein[i] = Trs.get(19).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Protein[i]);
            }

        }

        // Get Fat
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Fat[i] = "";
                Log.d(TAG, Fat[i]);
            }else{
                Fat[i] = Trs.get(20).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Fat[i]);
            }

        }

        // Get VitaminA
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                VitaminA[i] = "";
                Log.d(TAG, VitaminA[i]);
            }else{
                VitaminA[i] = Trs.get(21).getElementsByClass("textC").get(i).text();
                Log.d(TAG, VitaminA[i]);
            }

        }

        // Get Thiamin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Thiamin[i] = "";
                Log.d(TAG, Thiamin[i]);
            }else{
                Thiamin[i] = Trs.get(22).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Thiamin[i]);
            }

        }

        // Get Riboflavin
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Riboflavin[i] = "";
                Log.d(TAG, Riboflavin[i]);
            }else{
                Riboflavin[i] = Trs.get(23).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Riboflavin[i]);
            }

        }

        // Get VitaminC
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                VitaminC[i] = "";
                Log.d(TAG, VitaminC[i]);
            }else{
                VitaminC[i] = Trs.get(24).getElementsByClass("textC").get(i).text();
                Log.d(TAG, VitaminC[i]);
            }

        }

        // Get Calcium
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Calcium[i] = "";
                Log.d(TAG, Calcium[i]);
            }else{
                Calcium[i] = Trs.get(25).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Calcium[i]);
            }

        }

        // Get Iron
        for(int i=0; i<7; i++){
            if(NOMEALROW){
                Iron[i] = "";
                Log.d(TAG, Iron[i]);
            }else{
                Iron[i] = Trs.get(26).getElementsByClass("textC").get(i).text();
                Log.d(TAG, Iron[i]);
            }

        }
    }
}
