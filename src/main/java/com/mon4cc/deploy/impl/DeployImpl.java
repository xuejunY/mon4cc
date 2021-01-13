package com.mon4cc.deploy.impl;

import com.mon4cc.deploy.IDeploy;
import org.springframework.stereotype.Component;

import java.io.File;
@Component
public class DeployImpl implements IDeploy {

    //location of dependency jar package
    String libJar = "d:\\com\\mon4cc\\lib\\" ;
    String jar = libJar+"log4j-api-2.8.2.jar;"
                +libJar+"log4j-core-2.8.2.jar;"
                +libJar+"log4j-over-slf4j-1.6.6.jar;"
                +libJar+"log4j-slf4j-impl-2.8.2.jar;"
                +libJar+"asm-5.0.3.jar;"
                +libJar+"clojure-1.7.0.jar;"
                +libJar+"disruptor-3.3.11.jar;"
                +libJar+"kafka_2.11-2.1.0.jar;"
                +libJar+"kafka-clients-2.1.0.jar;"
                +libJar+"kafka-log4j-appender-2.1.0.jar;"
                +libJar+"kryo-3.0.3.jar;"
                +libJar+"metrics-core-3.1.0.jar;"
                +libJar+"metrics-graphite-3.1.0.jar;"
                +libJar+"minlog-1.3.0.jar;"
                +libJar+"objenesis-2.1.jar;"
                +libJar+"reflectasm-1.10.1.jar;"
                +libJar+"ring-core-0.2.0.jar;"
                +libJar+"servlet-api-2.5.jar;"
                +libJar+"storm-rename-hack-1.2.3.jar;"
                +libJar+"self-log-1.0.jar;"
                +libJar+"slf4j-api-1.7.21.jar;"
                +libJar+"storm-core-1.2.3.jar";



    @Override
    public boolean compile(String topologyName) {
        //compile command
        String complileCMD = "cmd /c javac -cp .;"+jar+" *.java " ;
        runCMD(complileCMD, topologyName) ;
        return true ;

    }

    @Override
    public boolean geneJar(String topologyName) {
        //generate command
        String generateCMD = "cmd /c jar -cvfe "+topologyName+".jar com.mon4cc."+topologyName+"."+topologyName
                +"com.mon4cc."+topologyName ;
        runCMD(generateCMD,topologyName) ;
        return true;
    }

    //command is run in specify location
    public boolean runCMD(String command,String topologyName){
        boolean flag = false;
        try{
            Runtime.getRuntime().exec(command,null,new File("d:/com/mon4cc/"+topologyName));
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
