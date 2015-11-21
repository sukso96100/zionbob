package kr.hs.zion.zionbob;

/**
 * Created by youngbin on 15. 11. 21.
 */
// http://hes.goe.go.kr/sts_sci_md01_001.do?schulCode=J100000659&schulCrseScCode=4&schulKndScCode=04&schMmealScCode=2&schYmd=2015.11.21
public class MealDataURLBuilder {
    public MealDataURLBuilder(){}
    //ProvienceCode 시/도 교육청 코드
    // SchoolCode 학교 코드
    // SchoolTypeA 학교 종류 (1 병설유치원, 2 초등학교, 3 중학교, 4 고등학교)
    // SchoolTypeB 학교 종류 (01 병설유치원,0 2 초등학교, 03 중학교, 04 고등학교)
    // MealType 식사 종류 (1 조식, 2 중식, 3 석식)
    // Date 날짜 (예시 : 2015.11.21)
    public String buildURL(
            String ProvienceCode, String SchoolCode,
            String SchoolTypeA, String SchoolTypeB,
            String MealType, String Date){
        String URL = "http://hes."+ProvienceCode+".go.kr/sts_sci_md01_001.do?schulCode="+SchoolCode
                +"&schulCrseScCode="+SchoolTypeA+"&schulKndScCode="+SchoolTypeB+"&schMmealScCode="+MealType+"&schYmd"+Date;
        return URL;
    }
}
