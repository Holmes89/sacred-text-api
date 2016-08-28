package com.joeldholmes.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.joeldholmes.services.interfaces.ISearchService;

public class SearchRepository {

	@Autowired
	ISearchService searchService;
}
