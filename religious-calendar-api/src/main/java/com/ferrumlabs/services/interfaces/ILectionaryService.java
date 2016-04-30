package com.ferrumlabs.services.interfaces;

import java.util.Set;

import org.joda.time.DateTime;

import com.ferrumlabs.exceptions.ServiceException;

public interface ILectionaryService {

	Set<String> getLectionaryVerses(DateTime now) throws ServiceException;

}
