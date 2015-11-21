package kr.hs.zion.zionbob;

/**
 * Created by youngbin on 15. 11. 21.
 */
// http://hes.goe.go.kr/sts_sci_md01_001.do?schulCode=J100000659&schulCrseScCode=4&schulKndScCode=04&schMmealScCode=2&schYmd=2015.11.21
public class MealDataURLBuilder {
    public MealDataURLBuilder(){}
    public String buildURL(
            String ProvienceCode, String SchoolCode,
            String SchoolTypeA, String SchoolTypeB,
            String MealType, String Date){
        String URL = "http://hes."+ProvienceCode+".go.kr/sts_sci_md01_001.do?schulCode="+SchoolCode
                +"&schulCrseScCode="+SchoolTypeA+"&schulKndScCode="+SchoolTypeB+"&schMmealScCode="+MealType+"&schYmd"+Date;
        return URL;
    }
}
