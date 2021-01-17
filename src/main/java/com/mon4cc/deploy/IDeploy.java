package com.mon4cc.deploy;

/**
 * @Autor xjyou_
 * @Date 2021.01.10
 * the interface is for deploy storm application
 */
public interface IDeploy {
    String compile(String topologyName) ;
    String geneJar(String topologyName) ;
}
