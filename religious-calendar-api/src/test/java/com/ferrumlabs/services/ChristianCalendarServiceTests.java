package com.ferrumlabs.services;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ferrumlabs.ReligiousCalendarApiApplication;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReligiousCalendarApiApplication.class)
public class ChristianCalendarServiceTests {
/*
	@Autowired
	IChristianCalendarService ccService;
	
	private DateTime testDate = new DateTime(2016, 4, 11, 0, 0);
	
	@Test
	public void testGetEaster(){
		DateTime easterDate = ccService.getEasterDate(2016);
		Assert.assertEquals(3, easterDate.getMonthOfYear());
		Assert.assertEquals(27, easterDate.getDayOfMonth());
		easterDate = ccService.getEasterDate(2015);
		Assert.assertEquals(4, easterDate.getMonthOfYear());
		Assert.assertEquals(5, easterDate.getDayOfMonth());
	}
	
	@Test
	public void testGetCalendarFromNow_2016(){
		ChristianCalendar cal = ccService.getCalendarFromNow(testDate);
		
		Assert.assertEquals('c', cal.getLiturgicalYear());
		
		//Ash Wednesday
		DateTime ashWednesday = cal.getAshWednesday();
		Assert.assertEquals(2, ashWednesday.getMonthOfYear());
		Assert.assertEquals(10, ashWednesday.getDayOfMonth());
		
		//Easter
		DateTime easter = cal.getEasterDate();
		Assert.assertEquals(3, easter.getMonthOfYear());
		Assert.assertEquals(27, easter.getDayOfMonth());
		
		//GoodFriday
		DateTime goodFriday = cal.getGoodFriday();
		Assert.assertEquals(3, goodFriday.getMonthOfYear());
		Assert.assertEquals(25, goodFriday.getDayOfMonth());
		
		//MaundyThursday
		DateTime maundyThursday = cal.getMaundyThursday();
		Assert.assertEquals(3, maundyThursday.getMonthOfYear());
		Assert.assertEquals(24, maundyThursday.getDayOfMonth());
		
		//AscensionFeast
		DateTime ascensionFeast = cal.getFeastOfTheAscension();
		Assert.assertEquals(DateTimeConstants.THURSDAY, ascensionFeast.getDayOfWeek());
		Assert.assertEquals(5, ascensionFeast.getMonthOfYear());
		Assert.assertEquals(5, ascensionFeast.getDayOfMonth());
		
		//AscensionSunday
		DateTime ascensionSunday = cal.getAscensionSunday();
		Assert.assertEquals(5, ascensionSunday.getMonthOfYear());
		Assert.assertEquals(8, ascensionSunday.getDayOfMonth());	
		
		//Pentecost
		DateTime pentecost = cal.getPentecost();
		Assert.assertEquals(5, pentecost.getMonthOfYear());
		Assert.assertEquals(15, pentecost.getDayOfMonth());	
		
		
		//Advent Start
		DateTime adventStart = cal.getAdventStart();
		Assert.assertEquals(DateTimeConstants.SUNDAY, adventStart.getDayOfWeek());
		Assert.assertEquals(2015, adventStart.getYear());
		Assert.assertEquals(11, adventStart.getMonthOfYear());
		Assert.assertEquals(29, adventStart.getDayOfMonth());
		
		//ChristmasEve
		DateTime christmasEve = cal.getChristmasEve();
		Assert.assertEquals(DateTimeConstants.THURSDAY, christmasEve.getDayOfWeek());
		
		//Christmas
		DateTime christmasDay = cal.getChristmasDate();
		Assert.assertEquals(DateTimeConstants.FRIDAY, christmasDay.getDayOfWeek());
	}
	
	@Test
	public void testGetCalendarFromWithinAdvent(){
		DateTime date = new DateTime(2016, 12, 11, 0, 0);
		ChristianCalendar cal = ccService.getCalendarFromNow(date);
		
		Assert.assertEquals('a', cal.getLiturgicalYear());
		
		//Ash Wednesday
		DateTime ashWednesday = cal.getAshWednesday();
		Assert.assertEquals(3, ashWednesday.getMonthOfYear());
		Assert.assertEquals(1, ashWednesday.getDayOfMonth());
		
		//Easter
		DateTime easter = cal.getEasterDate();
		Assert.assertEquals(4, easter.getMonthOfYear());
		Assert.assertEquals(16, easter.getDayOfMonth());
		
		//GoodFriday
		DateTime goodFriday = cal.getGoodFriday();
		Assert.assertEquals(4, goodFriday.getMonthOfYear());
		Assert.assertEquals(14, goodFriday.getDayOfMonth());
		
		//MaundyThursday
		DateTime maundyThursday = cal.getMaundyThursday();
		Assert.assertEquals(4, maundyThursday.getMonthOfYear());
		Assert.assertEquals(13, maundyThursday.getDayOfMonth());
		
		//AscensionFeast
		DateTime ascensionFeast = cal.getFeastOfTheAscension();
		Assert.assertEquals(DateTimeConstants.THURSDAY, ascensionFeast.getDayOfWeek());
		Assert.assertEquals(5, ascensionFeast.getMonthOfYear());
		Assert.assertEquals(25, ascensionFeast.getDayOfMonth());
		
		//AscensionSunday
		DateTime ascensionSunday = cal.getAscensionSunday();
		Assert.assertEquals(5, ascensionSunday.getMonthOfYear());
		Assert.assertEquals(28, ascensionSunday.getDayOfMonth());	
		
		//Pentecost
		DateTime pentecost = cal.getPentecost();
		Assert.assertEquals(6, pentecost.getMonthOfYear());
		Assert.assertEquals(4, pentecost.getDayOfMonth());	
		
		
		//Advent Start
		DateTime adventStart = cal.getAdventStart();
		Assert.assertEquals(DateTimeConstants.SUNDAY, adventStart.getDayOfWeek());
		Assert.assertEquals(2016, adventStart.getYear());
		Assert.assertEquals(11, adventStart.getMonthOfYear());
		Assert.assertEquals(27, adventStart.getDayOfMonth());
		
		//ChristmasEve
		DateTime christmasEve = cal.getChristmasEve();
		Assert.assertEquals(DateTimeConstants.SATURDAY, christmasEve.getDayOfWeek());
		
		//Christmas
		DateTime christmasDay = cal.getChristmasDate();
		Assert.assertEquals(DateTimeConstants.SUNDAY, christmasDay.getDayOfWeek());
	}
	@Test
	public void testGetCalendarFromNow_2017(){
		ChristianCalendar cal = ccService.getCalendarFromNow(testDate.plusYears(1));
		
		Assert.assertEquals('a', cal.getLiturgicalYear());
		
		//Ash Wednesday
		DateTime ashWednesday = cal.getAshWednesday();
		Assert.assertEquals(3, ashWednesday.getMonthOfYear());
		Assert.assertEquals(1, ashWednesday.getDayOfMonth());
		
		//Easter
		DateTime easter = cal.getEasterDate();
		Assert.assertEquals(4, easter.getMonthOfYear());
		Assert.assertEquals(16, easter.getDayOfMonth());
		
		//GoodFriday
		DateTime goodFriday = cal.getGoodFriday();
		Assert.assertEquals(4, goodFriday.getMonthOfYear());
		Assert.assertEquals(14, goodFriday.getDayOfMonth());

		//MaundyThursday
		DateTime maundyThursday = cal.getMaundyThursday();
		Assert.assertEquals(4, maundyThursday.getMonthOfYear());
		Assert.assertEquals(13, maundyThursday.getDayOfMonth());
		
		//AscensionFeast
		DateTime ascensionFeast = cal.getFeastOfTheAscension();
		Assert.assertEquals(DateTimeConstants.THURSDAY, ascensionFeast.getDayOfWeek());
		Assert.assertEquals(5, ascensionFeast.getMonthOfYear());
		Assert.assertEquals(25, ascensionFeast.getDayOfMonth());
		
		//AscensionSunday
		DateTime ascensionSunday = cal.getAscensionSunday();
		Assert.assertEquals(5, ascensionSunday.getMonthOfYear());
		Assert.assertEquals(28, ascensionSunday.getDayOfMonth());	
		
		//Pentecost
		DateTime pentecost = cal.getPentecost();
		Assert.assertEquals(6, pentecost.getMonthOfYear());
		Assert.assertEquals(4, pentecost.getDayOfMonth());	
	
		
		//Advent Start
		DateTime adventStart = cal.getAdventStart();
		Assert.assertEquals(DateTimeConstants.SUNDAY, adventStart.getDayOfWeek());
		Assert.assertEquals(2016, adventStart.getYear());
		Assert.assertEquals(11, adventStart.getMonthOfYear());
		Assert.assertEquals(27, adventStart.getDayOfMonth());
	
		//ChristmasEve
		DateTime christmasEve = cal.getChristmasEve();
		Assert.assertEquals(DateTimeConstants.SATURDAY, christmasEve.getDayOfWeek());

		//Christmas
		DateTime christmasDay = cal.getChristmasDate();
		Assert.assertEquals(DateTimeConstants.SUNDAY, christmasDay.getDayOfWeek());
	}
	
	*/
}
