package com.fm.framework.core.id.leaf;

import com.fm.framework.core.id.IdGenerator;
import com.fm.framework.core.id.leaf.segment.IDGen;
import com.fm.framework.core.id.leaf.segment.SegmentIDGenImpl;
import com.fm.framework.core.id.leaf.segment.dao.IDAllocDao;
import com.fm.framework.core.id.leaf.segment.dao.impl.IDAllocDaoImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * <p>叶子ID生成器实现</p>
 *
 * @author clufeng
 */
@Service
public class LeafIdGenerator implements IdGenerator, InitializingBean {

    @Autowired
    private DataSource dataSource;

    private IDGen idGen;

    @Override
    public Long gen(String key) {
        return idGen.get(key).getId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        idGen = new SegmentIDGenImpl();
        IDAllocDao dao = new IDAllocDaoImpl(dataSource);
        ((SegmentIDGenImpl) idGen).setDao(dao);
        idGen.init();
    }
}
