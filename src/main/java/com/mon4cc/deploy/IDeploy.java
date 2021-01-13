package com.mon4cc.deploy;

/**
 * @Autor xjyou_
 * @Date 2021.01.10
 * the interface is for deploy storm application
 */
public interface IDeploy {
    boolean compile(String deployName) ;
    boolean geneJar(String deployName) ;
}
