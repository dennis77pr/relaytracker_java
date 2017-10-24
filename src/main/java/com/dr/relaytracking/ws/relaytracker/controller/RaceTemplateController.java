package com.dr.relaytracking.ws.relaytracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dr.relaytracking.ws.relaytracker.data.service.RaceTemplateService;
import com.dr.relaytracking.ws.relaytracker.entity.RaceTemplate;

@RestController
public class RaceTemplateController {
	 @Autowired
	 private RaceTemplateService service;

	 @RequestMapping(value = "/templates", method = RequestMethod.GET)
	 public @ResponseBody List<RaceTemplate> templates(@RequestParam(value="series", required=false, defaultValue="all") String series) {
	     return service.findTemplate(series);
	 }	
}
