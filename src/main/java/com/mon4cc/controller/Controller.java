package com.mon4cc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mon4cc.codeGenerated.IBoltCodeGenerated;
import com.mon4cc.codeGenerated.IKafkaSpoutCodeGenerated;
import com.mon4cc.codeGenerated.ISpoutCodeGenerated;
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

/**
 * @author xjyou_
 * @Date 2020.12
 */
@RestController
@RequestMapping(value = "/mon4cc")
public class Controller {

	@Autowired
	ModelSave modelSave ;

	@Autowired
	ModelParse modelParse;

	@Autowired
	IBoltCodeGenerated iBoltCodeGenerated ;

	@Autowired
	ISpoutCodeGenerated iSpoutCodeGenerated ;

	@Autowired
	IKafkaSpoutCodeGenerated iKafkaSpoutCodeGenerated ;

	@Autowired
	private ITopologyconfigurationService iTopologyconfigurationService;
	
	@PostMapping("/model/save")
	public Json modelSave(@RequestBody String body) {
		String oper = "save xml";
		ModelDTO modelDTO = JSON.parseObject(body, ModelDTO.class);
		boolean success = iTopologyconfigurationService.insertXml(modelDTO.getTid(),modelDTO.getModelXml());
		return Json.result(oper, success);
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

	/**
	 * Method (#generateCode) is used to generate code
	 * @param body
	 * @return
	 */
	@PostMapping(value = "/model/generateCode")
	public Json generateCode(@RequestBody String body){
		String oper = "generate code" ;
		JSONObject jsonObject = JSON.parseObject(body);
		String topologyId = jsonObject.getString("topologyId") ;
		return Json.result(oper,iBoltCodeGenerated.boltCodeGenerated(topologyId)) ;
	}

	/**
	 * Method (#deploy) is used to package java file as jar
	 * @param topologyId
	 * @return
	 */
	@PostMapping(value = "/model/deploy")
	public Json deploy(@RequestBody String topologyId){
		String oper = "deploy model" ;

		return Json.result(oper,true) ;
	}

	/**
	 * Method (#start) is used to run topology
	 * @param body
	 * @return
	 */
	@PostMapping(value = "/model/start")
	public Json start(@RequestBody String body){
		String oper = "start topology" ;

		return Json.result(oper,true) ;
	}

	/**
	 * Method (#stop) is used to kill running topology and can't recover
	 * @param topologyId
	 * @return
	 */
	@PostMapping(value = "/model/stop")
	public Json stop(@RequestBody String topologyId){
		String oper = "kill topology" ;

		return Json.result(oper,true) ;
	}
	@RequestMapping(value = "/model/testCode")
	public boolean testcod(@RequestParam("topologyId") String topologyId){

		return iKafkaSpoutCodeGenerated.kafkaSpoutCodeGenerated(topologyId) ;
	}
	


}
