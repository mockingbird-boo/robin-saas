package cn.com.mockingbird.robin.mybatis.service;

import cn.com.mockingbird.robin.mybatis.base.BaseMapperX;
import cn.com.mockingbird.robin.mybatis.util.MyBatisPlusUtils;
import cn.com.mockingbird.robin.webmvc.model.PageData;
import cn.com.mockingbird.robin.webmvc.model.PageParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * IService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ）的扩展
 *
 * @author zhaopeng
 * @date 2023/10/8 1:59
 **/
public class ServiceImplX<M extends BaseMapperX<T>, T> extends ServiceImpl<M, T> {

    private static final int DEFAULT_BATCH_SIZE = 30;

    /**
     * 批量写入
     * @param entities 实体集合
     * @param batchSize 批数据量
     * @return true - 写入成功；false - 写入失败
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean saveBatch(Collection<T> entities, Integer batchSize) {
        try {
            int size = entities.size();
            if (batchSize == null) {
                batchSize = DEFAULT_BATCH_SIZE;
            }
            int idxLimit = Math.min(batchSize, size);
            int i = 1;
            //保存单批提交的数据集合
            List<T> batchDataList = new ArrayList<>();
            for (Iterator<T> iterator = entities.iterator(); iterator.hasNext(); ++i) {
                T entity = iterator.next();
                batchDataList.add(entity);
                if (i == idxLimit) {
                    baseMapper.insertBatchSomeColumn(batchDataList);
                    batchDataList.clear();
                    idxLimit = Math.min(idxLimit + batchSize, size);
                }
            }
        } catch (Exception e) {
            log.error("ServiceImplX#saveBatch failure:", e);
            return false;
        }
        return true;
    }

    /**
     * 分页查询
     * 注意：暂时仅支持 eq 查询，不支持模糊查询、范围查询等，如需要可以自己创建 QueryWrapper 实例
     * @param pageParams 分页
     * @param entity 实体类分装的查询条件
     * @return 分页数据
     */
    public PageData<T> selectPage(PageParams pageParams, T entity) {
        QueryWrapper<T> queryWrapper = MyBatisPlusUtils.entity2Wrapper(entity);
        return baseMapper.selectPage(pageParams, queryWrapper);
    }

}
