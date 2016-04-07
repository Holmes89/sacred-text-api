package com.ferrumlabs.services;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ferrumlabs.ReligiousCalendarApiApplication;
import com.ferrumlabs.services.interfaces.IChristianCalendarService;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReligiousCalendarApiApplication.class)
public class ChristianCalendarServiceTests {

	@Autowired
	IChristianCalendarService ccService;
	
	@Test
	public void testGetEaster(){
		DateTime easterDate = ccService.getEasterDate(2016);
		Assert.assertEquals(3, easterDate.getMonthOfYear());
		Assert.assertEquals(27, easterDate.getDayOfMonth());
		easterDate = ccService.getEasterDate(2015);
		Assert.assertEquals(4, easterDate.getMonthOfYear());
		Assert.assertEquals(5, easterDate.getDayOfMonth());
	}
}
