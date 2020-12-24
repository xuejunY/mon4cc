package com.mon4cc.parse;



import com.mon4cc.parse.entity.ModelDTO;
import com.mon4cc.service.ITopologyconfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


/**
 * parse spout , bolt , flow, and model configuration
 */

@Configuration
public class ModelSave {

    private String tid;
    private String modelXml;



    @Autowired
    private ITopologyconfigurationService iTopologyconfigurationService;


    public boolean saveModel(ModelDTO modelDTO) {

        tid = modelDTO.getTid();
        modelXml = modelDTO.getModelXml();
        //save model
        iTopologyconfigurationService.insertXml(tid, modelXml);// actually update operation
        return true;
    }
}