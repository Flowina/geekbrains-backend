package com.geekbrains.backend.test.db;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DbService {

    private final SqlSessionFactory sessionFactory;
    private final CategoriesMapper categoriesMapper;
    private final ProductsMapper productsMapper;
    private SqlSession session;

    public DbService() {
        sessionFactory = new SqlSessionFactoryBuilder()
                .build(getClass().getResourceAsStream("myBatisConfig.xml"));
        openSession();
        categoriesMapper = session.getMapper(CategoriesMapper.class);
        productsMapper = session.getMapper(ProductsMapper.class);
    }

    public void openSession() {
        session = sessionFactory.openSession();
    }

    public void closeSession() {
        session.close();
    }

    public CategoriesMapper getCategoriesMapper() {
        return categoriesMapper;
    }

    public ProductsMapper getProductsMapper() {
        return productsMapper;
    }

}
