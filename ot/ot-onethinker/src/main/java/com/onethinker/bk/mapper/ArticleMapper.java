package com.onethinker.bk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.bk.domain.Article;

import java.util.List;

/**
 * 文章Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 查询文章
     *
     * @param id 文章主键
     * @return 文章
     */
    public Article selectArticleById(Long id);

    /**
     * 查询文章列表
     *
     * @param article 文章
     * @return 文章集合
     */
    public List<Article> selectArticleList(Article article);

    /**
     * 新增文章
     *
     * @param article 文章
     * @return 结果
     */
    public int insertArticle(Article article);

    /**
     * 修改文章
     *
     * @param article 文章
     * @return 结果
     */
    public int updateArticle(Article article);

    /**
     * 删除文章
     *
     * @param id 文章主键
     * @return 结果
     */
    public int deleteArticleById(Long id);

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteArticleByIds(Long[] ids);

    /**
     * 获取文章统计数
     * @param id
     * @param viewCount
     * @return
     */
    int updateViewCount(Long id,Long viewCount);
}
