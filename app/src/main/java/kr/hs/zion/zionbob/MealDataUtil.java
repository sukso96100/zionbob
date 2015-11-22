package kr.hs.zion.zionbob;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by youngbin on 15. 11. 21.
 */
public class MealDataUtil {

    String provienceCode;
    String schoolCode;
    int schoolTypeA;
    String schoolTypeB;
    int mealType;
    String date;

    String URL = null;

    // Meal Data
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

    public void parseData(String RawData){
        Document doc = Jsoup.parse(RawData);

        Elements Table = doc.getElementsByClass("tbl_type3");
        Elements Trs = Table.get(0).getElementsByTag("tr");
    }
}
