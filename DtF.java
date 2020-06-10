/*
  This class is basically a calendar used for date ranges.
  I use them in android projects.
  I don't use Date,LocalDate,etc...because i like to write everything on my own.JK.
  @DevJRM
  
*/
import java.util.*;

public class DtF {

    //To get date range of current week (from Sunday to Saturday)
    public HashMap<String,String> f_Week() {
        HashMap<String, String> ro_Week = new HashMap<String, String>();
        ro_Week.put("sdt", f_PrevDt());
        ro_Week.put("edt", f_NextDt());
        return ro_Week;
    }

    //To get date range of  current month
    public HashMap<String,String> f_Month() {
        HashMap<String, String> ro_Month = new HashMap<String, String>();
        String v_SDT = f_DtHelper("CY") + "-" + f_DtHelper("CM") + "01";
        String v_EDT = f_DtHelper("CY") + "-" + f_DtHelper("CM") + f_DaysInMonth(Integer.parseInt(f_DtHelper("CM")));
        ro_Month.put("sdt", v_SDT);
        ro_Month.put("edt", v_EDT);
        return ro_Month;
    }
    //To get date range of current year (just because im lazy) 
    public HashMap<String,String> f_Year() {
        HashMap<String, String> ro_Year = new HashMap<String, String>();
        ro_Year.put("sdt", f_DtHelper("CY") + "-01-01");
        ro_Year.put("edt", f_DtHelper("CY") + "-12-31");
        return ro_Year;
    }
    //To get start of the week
    String f_PrevDt() {
        int v_Day = Integer.parseInt(f_DtHelper("CD")),
                v_Month = Integer.parseInt(f_DtHelper("CM")),
                v_Year = Integer.parseInt(f_DtHelper("CY")),
                v_WeekDay = Integer.parseInt(f_DtHelper("CDW"));
        String rv_D = "",
                rv_M = "",
                rv_Y = "";
        if ((v_Day - f_WeekRangeValue(v_WeekDay)) < 1) {
            int v_M = v_Month;
            if ((--v_M) < 1) {
                v_M = 12;
                rv_D = String.valueOf(f_DaysInMonth(v_M) + (1 - v_Day));
                rv_M = String.valueOf(v_M);
                rv_Y = String.valueOf(--v_Year);
            } else {
                rv_D = String.valueOf(f_DaysInMonth(v_Month) + (1 - v_Day));
                rv_M = String.valueOf(--v_M);
                rv_Y = String.valueOf(v_Year);
            }
        } else {
            rv_D = String.valueOf(v_Day - f_WeekRangeValue(v_WeekDay));
            rv_M = String.valueOf(v_Month);
            rv_Y = String.valueOf(v_Year);
        }
        return rv_Y + "-" + rv_M + "-" + rv_D;
    }
    //To get end of the week
    String f_NextDt() {
        int v_Day = Integer.parseInt(f_DtHelper("CD")),
                v_Month = Integer.parseInt(f_DtHelper("CM")),
                v_Year = Integer.parseInt(f_DtHelper("CY")),
                v_WeekDay = Integer.parseInt(f_DtHelper("CDW"));
        String rv_D = "",
                rv_M = "",
                rv_Y = "";
        if ((v_Day + (6 - f_WeekRangeValue(v_WeekDay))) > f_DaysInMonth(v_Month)) {
            int v_M = v_Month;
            if ((++v_M) > 12) {
                v_M = 1;
                rv_D = String.valueOf(f_DaysInMonth(v_Month) - (f_DaysInMonth(v_Month) + (6 - f_WeekRangeValue(v_WeekDay))));
                rv_M = String.valueOf(v_M);
                rv_Y = String.valueOf(++v_Year);
            } else {
                rv_D = String.valueOf(f_DaysInMonth(v_Month) - (f_DaysInMonth(v_Month) + (6 - f_WeekRangeValue(v_WeekDay))));
                rv_M = String.valueOf((++v_M));
                rv_Y = String.valueOf(v_Year);
            }
        } else {
            rv_D = String.valueOf((v_Day + (6 - f_WeekRangeValue(v_WeekDay))));
            rv_M = String.valueOf(v_Month);
            rv_Y = String.valueOf(v_Year);
        }
        return rv_Y + "-" + rv_M + "-" + rv_D;
    }

    //weekday value
    int f_WeekRangeValue(int p_WeekDay) {
        switch (p_WeekDay) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
        }
        return 0;
    }
    //days in month
    int f_DaysInMonth(int p_Month) {
        if (p_Month == 2) {
            if (f_IsLeapYear(Integer.parseInt(f_DtHelper("CY"))) == 1) {
                return 29;
            } else {
                return 28;
            }
        } else if (p_Month == 4 || p_Month == 6 || p_Month == 11) {
            return 30;
        } else {
            return 31;
        }
    }
    //leap year 
    int f_IsLeapYear(int p_Year) {
        if (p_Year % 4 == 0) {
            if (p_Year % 100 == 0) {
                if (p_Year % 400 == 0) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    //get basic everything
    String f_DtHelper(String p_Type) {
        String rv_Value = "", v_Day = "", v_Month = "", v_Year = "";
        try {
            switch (p_Type) {
                case "CY":
                    rv_Value = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                    break;
                case "NY":
                    rv_Value = String.valueOf(Calendar.getInstance().get(Calendar.YEAR + 1));
                    break;
                case "CM":
                    rv_Value = String.valueOf(Calendar.getInstance().get(Calendar.MONTH + 1));
                    break;
                case "CD":
                    rv_Value = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
                    break;
                case "CDW":
                    rv_Value = String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                    break;
                case "CDT":
                    v_Day = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
                    v_Month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH + 1));
                    v_Year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                    if (v_Day.length() == 1) {
                        v_Day = "0" + v_Day;
                    }
                    rv_Value = v_Year + "-" + v_Month + "-" + v_Day;
                    break;
            }
        } catch (Exception p_Exception) {
        }
        return rv_Value;
    }
}
