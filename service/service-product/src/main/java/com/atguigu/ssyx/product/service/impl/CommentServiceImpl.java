package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Comment;
import com.atguigu.ssyx.product.mapper.CommentMapper;
import com.atguigu.ssyx.product.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author syh
 * @since 2023-08-20
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
