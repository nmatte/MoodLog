import com.nmatte.mood.util.CalendarUtil;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;





import java.util.Calendar;

public class CalendarDatabaseUtilTest
{
    Calendar mid2015Date;
    int DAYS_BETWEEN_MID_LATER_2015 = 123;
    Calendar later2015Date;
    int DAYS_BETWEEN_LATER_2015_EARLY_2016 = 32;
    Calendar early2016Date;


    @Before
    public void setUp(){
        mid2015Date = Calendar.getInstance();
        mid2015Date.set(Calendar.YEAR,2015);
        mid2015Date.set(Calendar.MONTH,Calendar.AUGUST);
        mid2015Date.set(Calendar.DAY_OF_MONTH,1);

        later2015Date = Calendar.getInstance();
        later2015Date.set(Calendar.YEAR,2015);
        later2015Date.set(Calendar.MONTH,Calendar.DECEMBER);
        later2015Date.set(Calendar.DAY_OF_MONTH,1);

        early2016Date = Calendar.getInstance();
        early2016Date.set(Calendar.YEAR,2016);
        early2016Date.set(Calendar.MONTH,Calendar.JANUARY);
        early2016Date.set(Calendar.DAY_OF_MONTH,1);
    }

    @Test
    public void testDatesSameYear(){
        int result = CalendarUtil.dayDiff(mid2015Date, later2015Date);
        assertTrue(result == DAYS_BETWEEN_MID_LATER_2015);
    }

    @Test
    public void testSwappedDates(){
        int result = CalendarUtil.dayDiff(later2015Date, mid2015Date);
        assertTrue(result == DAYS_BETWEEN_MID_LATER_2015);
    }

    @Test
    public void testDatesAcrossYear(){
        int result = CalendarUtil.dayDiff(later2015Date, early2016Date);
        assertTrue(result == DAYS_BETWEEN_LATER_2015_EARLY_2016);
    }


}
