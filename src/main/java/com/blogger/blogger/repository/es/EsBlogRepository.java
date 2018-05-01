package com.blogger.blogger.repository.es;

import com.blogger.blogger.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Es BlogRepository
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog,String> {

    /**
     * 模糊查询(去重)
     *
     * @param title    标题
     * @param Summary  总结
     * @param content  内容
     * @param tags     标签
     * @param pageable 分页
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title,
                 String Summary, String content, String tags, Pageable pageable);

    /**
     * 通过ID查询博客
     *
     * @param blogId
     * @return
     */
    EsBlog findByBlogId(Long blogId);



}
