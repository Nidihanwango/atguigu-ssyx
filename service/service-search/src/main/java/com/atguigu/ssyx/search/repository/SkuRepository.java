package com.atguigu.ssyx.search.repository;

import com.atguigu.ssyx.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {

    // 获取爆品商品
    Page<SkuEs> findByOrderByHotScoreDesc(Pageable page);
    // 根据分类id和仓库id分页查询商品信息
    Page<SkuEs> findByCategoryIdAndWareId(Long categoryId, Long wareId, PageRequest pageRequest);
    // 根据分类id,仓库id和关键字分页查询商品信息
    Page<SkuEs> findByCategoryIdAndWareIdAndKeyword(Long categoryId, Long wareId, String keyword, PageRequest pageRequest);
}
