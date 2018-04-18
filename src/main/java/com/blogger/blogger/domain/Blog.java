package com.blogger.blogger.domain;

import com.github.rjeschke.txtmark.Processor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 博客实体
 */
@Entity
@Data
@DynamicUpdate
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 唯一
    private Long id;

    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "摘要不能为空")
    @Size(min = 2, max = 300)
    @Column(nullable = false)
    private String summary;

    @Lob    // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    @Lob    // 大对象，映射 MySQL 的 Long Text 类型
    @Basic(fetch = FetchType.LAZY) // 懒加载
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent;   // 将 md 转为 html

    /**
     * 作者
     */
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @CreatedDate
    private Timestamp createTime;

    /**
     * 访问量、阅读量
     */
    @Column(name = "reading")
    private long reading;
    /**
     * 评论量
     */
    @Column(name = "comments")
    private long comments;
    /**
     * 点赞量
     */
    @Column(name = "likes")
    private long likes;


    public void setContent(String content) {
        this.content = content;
        this.htmlContent=Processor.process(content);   // 将markdown 内容转换为html 格式
    }
}
