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
    String[] Meal = new String[7];
    // Origin of Ingredients Data
    String[] RiceOrigin = new String[7];
    String[] KimchiOrigin = new String[7];
    String[] RedPepperOrigin = new String[7];
    String[] BeefOrigin = new String[7];
    String[] PorkOrigin = new String[7];
    String[] ChickenOrigin = new String[7];
    String[] DuckOrigin = new String[7];
    String[] ProcessedBeefOrigin = new String[7];
    String[] ProcessedPorkOrigin = new String[7];
    String[] ProcessedChickenOrigin = new String[7];
    String[] ProcessedDuckOrigin = new String[7];
    String[] Notes = new String[7];
    // Nutrients Data
    String[] Energy = new String[7];
    String[] Carbohydrate = new String[7];
    String[] Protein = new String[7];
    String[] Fat = new String[7];
    String[] VitaminA = new String[7];
    String[] Thiamin = new String[7];
    String[] Riboflavin = new String[7];
    String[] Calcium = new String[7];
    String[] Iron = new String[7];



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
                +"&schulCrseScCode="+SchoolTypeA+"&schulKndScCode="+SchoolTypeB+"&schMmealScCode="+MealType+"&schYmd"+Date;
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

        // Get Meal
        for(int i=0; i<7; i++){
            Meal[i] = Trs.get(2).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Meal[i]);
        }

        // Get RiceOrigin
        for(int i=0; i<7; i++){
            RiceOrigin[i] = Trs.get(4).getElementsByClass("textC").get(i).text();
            Log.d(TAG, RiceOrigin[i]);
        }

        // Get KimchiOrigin
        for(int i=0; i<7; i++){
            KimchiOrigin[i] = Trs.get(5).getElementsByClass("textC").get(i).text();
            Log.d(TAG, KimchiOrigin[i]);
        }

        // Get RedPepperOrigin
        for(int i=0; i<7; i++){
            RedPepperOrigin[i] = Trs.get(6).getElementsByClass("textC").get(i).text();
            Log.d(TAG, RedPepperOrigin[i]);
        }

        // Get BeefOrigin
        for(int i=0; i<7; i++){
            BeefOrigin[i] = Trs.get(7).getElementsByClass("textC").get(i).text();
            Log.d(TAG, BeefOrigin[i]);
        }

        // Get PorkOrigin
        for(int i=0; i<7; i++){
            PorkOrigin[i] = Trs.get(8).getElementsByClass("textC").get(i).text();
            Log.d(TAG, PorkOrigin[i]);
        }

        // Get ChickenOrigin
        for(int i=0; i<7; i++){
            ChickenOrigin[i] = Trs.get(9).getElementsByClass("textC").get(i).text();
            Log.d(TAG, ChickenOrigin[i]);
        }

        // Get DuckOrigin
        for(int i=0; i<7; i++){
            DuckOrigin[i] = Trs.get(10).getElementsByClass("textC").get(i).text();
            Log.d(TAG, DuckOrigin[i]);
        }

        // Get ProcessedBeefOrigin
        for(int i=0; i<7; i++){
            ProcessedBeefOrigin[i] = Trs.get(11).getElementsByClass("textC").get(i).text();
            Log.d(TAG, ProcessedBeefOrigin[i]);
        }

        // Get ProcessedPorkOrigin
        for(int i=0; i<7; i++){
            ProcessedPorkOrigin[i] = Trs.get(12).getElementsByClass("textC").get(i).text();
            Log.d(TAG, ProcessedPorkOrigin[i]);
        }

        // Get ProcessedChickenOrigin
        for(int i=0; i<7; i++){
            ProcessedChickenOrigin[i] = Trs.get(13).getElementsByClass("textC").get(i).text();
            Log.d(TAG, ProcessedChickenOrigin[i]);
        }

        // Get ProcessedDuckOrigin
        for(int i=0; i<7; i++){
            ProcessedDuckOrigin[i] = Trs.get(14).getElementsByClass("textC").get(i).text();
            Log.d(TAG, ProcessedDuckOrigin[i]);
        }

        // Get notes
        for(int i=0; i<7; i++){
            Notes[i] = Trs.get(15).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Notes[i]);
        }

        // Get Energy
        for(int i=0; i<7; i++){
            Energy [i] = Trs.get(17).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Energy [i]);
        }

        // Get Carbohydrate
        for(int i=0; i<7; i++){
            Carbohydrate[i] = Trs.get(18).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Carbohydrate[i]);
        }

        // Get Protein
        for(int i=0; i<7; i++){
            Protein[i] = Trs.get(19).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Protein[i]);
        }

        // Get Fat
        for(int i=0; i<7; i++){
            Fat[i] = Trs.get(20).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Fat[i]);
        }

        // Get VitaminA
        for(int i=0; i<7; i++){
            VitaminA[i] = Trs.get(21).getElementsByClass("textC").get(i).text();
            Log.d(TAG, VitaminA[i]);
        }

        // Get Thiamin
        for(int i=0; i<7; i++){
            Thiamin[i] = Trs.get(22).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Thiamin[i]);
        }

        // Get Riboflavin
        for(int i=0; i<7; i++){
            Riboflavin[i] = Trs.get(22).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Riboflavin[i]);
        }

        // Get Calcium
        for(int i=0; i<7; i++){
            Calcium[i] = Trs.get(23).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Calcium[i]);
        }

        // Get Iron
        for(int i=0; i<7; i++){
            Iron[i] = Trs.get(24).getElementsByClass("textC").get(i).text();
            Log.d(TAG, Iron[i]);
        }
    }
}
