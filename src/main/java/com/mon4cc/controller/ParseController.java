package com.mon4cc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mon4cc.entity.TopologyConfiguration;
import com.mon4cc.parse.ModelSave;
import com.mon4cc.service.IKafkaspoutService;
import com.mon4cc.service.ITopologyconfigurationService;
import com.mon4cc.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.mon4cc.parse.ModelParse;
import com.mon4cc.parse.entity.ModelDTO;

@RestController
@RequestMapping(value = "/mon4cc")
public class ParseController {

	@Autowired
	ModelSave modelSave ;

	@Autowired
	ModelParse modelParse;

	@Autowired
	private ITopologyconfigurationService iTopologyconfigurationService;
	
	@PostMapping("/model/save")
	public Json modelSave(@RequestBody String body) {
		String oper = "save xml";
		ModelDTO modelDTO = JSON.parseObject(body, ModelDTO.class);
		boolean success = iTopologyconfigurationService.insertXml(modelDTO.getTid(),modelDTO.getModelXml());
		return Json.result(oper, success);
	}
	
	@RequestMapping(value = "/model/code")
	public String codeSaved(){
		System.out.println("success") ;
		return "success" ;
	}

//	@RequestMapping(value="/model/parse")
	@PostMapping("/model/parse")
	public Json modelParse(@RequestBody String body){
		String oper = "parse a model";

		JSONObject jsonObj = JSON.parseObject(body);
		String tid = jsonObj.getString("tid");
		String success = modelParse.parseModel(tid);

		return Json.result(oper, true);
	}
	
	@RequestMapping(value="/model/test")
	public String testTrasfer(@RequestParam("modelxml") MultipartFile xmlFile) {
		
		return modelParse.parseXml(xmlFile) ;
	}

}
