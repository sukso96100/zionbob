package kr.hs.zion.zionbob.util;

import android.content.Context;

import kr.hs.zion.zionbob.MealDataUtil;
import kr.hs.zion.zionbob.R;

/**
 * Created by youngbin on 15. 11. 26.
 */
public class MealDataProcessor {
    public static String processOriginData(MealDataUtil MDU, int DayOfWeek, Context c){
        String originData = "";
        String[] OriginTitles = c.getResources().getStringArray(R.array.array_origin);
        if(MDU.RiceOrigin[DayOfWeek].length()>0)originData += OriginTitles[0] + " : " + MDU.RiceOrigin[DayOfWeek] + "\n";
        if(MDU.KimchiOrigin[DayOfWeek].length()>0)originData += OriginTitles[1] + " : " + MDU.KimchiOrigin[DayOfWeek] + "\n";
        if(MDU.RedPepperOrigin[DayOfWeek].length()>0)originData += OriginTitles[2] + " : " + MDU.RedPepperOrigin[DayOfWeek] + "\n";
        if(MDU.BeefOrigin[DayOfWeek].length()>0)originData += OriginTitles[3] + " : " + MDU.BeefOrigin[DayOfWeek] + "\n";
        if(MDU.PorkOrigin[DayOfWeek].length()>0)originData += OriginTitles[4] + " : " + MDU.PorkOrigin[DayOfWeek] + "\n";
        if(MDU.ChickenOrigin[DayOfWeek].length()>0)originData += OriginTitles[5] + " : " + MDU.ChickenOrigin[DayOfWeek] + "\n";
        if(MDU.DuckOrigin[DayOfWeek].length()>0)originData += OriginTitles[6] + " : " + MDU.DuckOrigin[DayOfWeek] + "\n";
        if(MDU.ProcessedBeefOrigin[DayOfWeek].length()>0)originData += OriginTitles[7] + " : " + MDU.ProcessedBeefOrigin[DayOfWeek] + "\n";
        if(MDU.ProcessedPorkOrigin[DayOfWeek].length()>0)originData += OriginTitles[8] + " : " + MDU.ProcessedPorkOrigin[DayOfWeek] + "\n";
        if(MDU.ProcessedChickenOrigin[DayOfWeek].length()>0)originData += OriginTitles[9] + " : " + MDU.ProcessedChickenOrigin[DayOfWeek] + "\n";
        if(MDU.ProcessedDuckOrigin[DayOfWeek].length()>0)originData += OriginTitles[10] + " : " + MDU.ProcessedDuckOrigin[DayOfWeek] + "\n";
        if(MDU.Notes[DayOfWeek].length()>0)originData += OriginTitles[11] + " : " + MDU.Notes[DayOfWeek] + "\n";

        return originData;
    }

    public static String processNutrientsData(MealDataUtil MDU, int DayOfWeek, Context c){
        String nutrientsData = "";
        String[] NutrinetTitles = c.getResources().getStringArray(R.array.array_nutrinets);
        if(MDU.Energy[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[0] + " : " + MDU.Energy[DayOfWeek] + "\n";
        if(MDU.Carbohydrate[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[1] + " : " + MDU.Carbohydrate[DayOfWeek] + "\n";
        if(MDU.Protein[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[2] + " : " + MDU.Protein[DayOfWeek] + "\n";
        if(MDU.Fat[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[3] + " : " + MDU.Fat[DayOfWeek] + "\n";
        if(MDU.VitaminA[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[4] + " : " + MDU.VitaminA[DayOfWeek] + "\n";
        if(MDU.Thiamin[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[5] + " : " + MDU.Thiamin[DayOfWeek] + "\n";
        if(MDU.Riboflavin[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[6] + " : " + MDU.Riboflavin[DayOfWeek] + "\n";
        if(MDU.VitaminC[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[7] + " : " + MDU.VitaminC[DayOfWeek] + "\n";
        if(MDU.Calcium[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[8] + " : " + MDU.Calcium[DayOfWeek] + "\n";
        if(MDU.Iron[DayOfWeek].length()>0)nutrientsData += NutrinetTitles[9] + " : " + MDU.Iron[DayOfWeek] + "\n";

        return nutrientsData;
    }
}
