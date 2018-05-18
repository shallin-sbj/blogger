package com.blogger.blogger.service;

import com.blogger.blogger.domain.User;
import com.blogger.blogger.domain.es.EsBlog;
import com.blogger.blogger.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * es 服务接口
 */
public interface EsBlogService {

    /**
     * 删除Blog
     * @param id
     * @return
     */
    void removeEsBlog(String id) throws Exception;

    /**
     * 更新 EsBlog
     * @param
     * @return
     */
    EsBlog updateEsBlog(EsBlog esBlog) throws Exception;

    /**
     * 根据id获取Blog
     * @param
     * @return
     */
    EsBlog getEsBlogByBlogId(Long blogId) throws Exception;

    /**
     * 最新博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable) throws Exception;

    /**
     * 最热博客列表，分页
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable) throws Exception;

    /**
     * 博客列表，分页
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable) throws Exception;
    /**
     * 最新前5
     * @return
     */
    List<EsBlog> listTop5NewestEsBlogs() throws Exception;

    /**
     * 最热前5
     */
    List<EsBlog> listTop5HotestEsBlogs() throws Exception;

    /**
     * 最热前 30 标签
     * @return
     */
    List<TagVO> listTop30Tags() throws Exception;

    /**
     * 最热前12用户
     * @return
     */
    List<User> listTop12Users() throws Exception;

}
