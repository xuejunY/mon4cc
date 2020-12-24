package com.mon4cc.service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangsanhe
 * @since 2020-12-19
 */
public interface ITopologyconfigurationService{

    boolean insertXml(String tid, String modelXml);
    String selectXml(String tid);

}
