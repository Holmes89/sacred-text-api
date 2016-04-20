package com.ferrumlabs.services.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferrumlabs.enums.ChristianSpecialDatesEnum;
import com.ferrumlabs.exceptions.FactoryException;
import com.ferrumlabs.exceptions.ServiceException;
import com.ferrumlabs.factories.ChristianCalendarFactory;
import com.ferrumlabs.services.interfaces.IChristianCalendarService;
import com.ferrumlabs.utils.ErrorCodes;

@Service("ChristianCalendarService")
public class ChristianCalendarService implements IChristianCalendarService {
	
	@Autowired
	ChristianCalendarFactory calFactory;

	@Override
	public DateTime getEaster(int year){
		return calFactory.getEasterDate(year);
	}

	@Override
	public Map<String, DateTime> getHolidays(DateTime date) throws ServiceException {
		if(date ==null){
			throw new ServiceException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		Map<String, DateTime> holidayMap = new HashMap<String, DateTime>();
		try{
			Map<ChristianSpecialDatesEnum, DateTime> eventMap = calFactory.getEventMap(date);
			for(ChristianSpecialDatesEnum key: eventMap.keySet()){
				holidayMap.put(key.getDisplayName(), eventMap.get(key));
			}
		}catch(FactoryException e){
			throw new ServiceException("Factory Error", e);
		}
		return holidayMap;
	}

	@Override
	public Set<String> getHoliday(DateTime date) throws ServiceException {
		if(date ==null){
			throw new ServiceException("Date cannot be null", ErrorCodes.NULL_INPUT);
		}
		date = cleanDate(date);
		Set<String> holidays = new HashSet<String>();
		try{
			Map<DateTime, Set<ChristianSpecialDatesEnum>> dateMap = calFactory.getDateMap(date);
			Set<ChristianSpecialDatesEnum> holidayEnums;
			if(dateMap.containsKey(date)){
				holidayEnums = dateMap.get(date);
			}
			else{
				date = date.minusWeeks(1).withDayOfWeek(DateTimeConstants.SUNDAY);
				holidayEnums = dateMap.get(date);
			}
			for(ChristianSpecialDatesEnum h: holidayEnums){
				holidays.add(h.getDisplayName());
			}
		}catch(FactoryException e){
			throw new ServiceException("Factory Error", e);
		}
		return holidays;
	}

	private DateTime cleanDate(DateTime date){
		return date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
	}

}
