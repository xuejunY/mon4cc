package com.mon4cc.codeGenerated;

/**
 * @author xjyou_
 * @Date 2020.12.30
 * The interface(#ICodeGenerate） is used to generate storm code
 */
public interface ICodeGenerate {
    @Deprecated
    boolean generateCode(String topologyId) ;
    boolean generateCodeUpgrade(String topologyId) ;
}
