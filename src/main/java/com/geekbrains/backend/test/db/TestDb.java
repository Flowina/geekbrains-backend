package com.geekbrains.backend.test.db;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.CategoriesExample;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDb {

    public static void main(String[] args) {
        DbService dbService = new DbService();

        Map<String, Products[]> testData = new HashMap<>();
        testData.put("Fruits", new Products[]{
                        new Products("Apples", 6),
                        new Products("Oranges", 10),
                        new Products("Bananas", 30)
                }
        );
        testData.put("Toys", new Products[]{
                        new Products("Dolls", 2),
                        new Products("Balls", 5),
                        new Products("Constructors", 11)
                }
        );
        testData.put("Flowers", new Products[]{
                        new Products("Daisies", 1),
                        new Products("Tulips", 2),
                        new Products("Lilies", 3)
                }
        );
        testData.put("Clothes", new Products[]{
                        new Products("T-shirts", 5),
                        new Products("Socks", 40),
                        new Products("Sweaters", 50)
                }
        );

        CategoriesMapper categoriesMapper = dbService.getCategoriesMapper();
        ProductsMapper productsMapper = dbService.getProductsMapper();

        //Создать категории и продукты

        for (Map.Entry<String, Products[]> item :
                testData.entrySet()) {
            Categories cat = createCategory(categoriesMapper, item.getKey());

            for (Products product :
                    item.getValue()) {
                product.setCategoryId(cat.getId());
                productsMapper.insert(product);
            }

        }

        // 3. Вывести продукты в каждой из категорий
        printAllCategories(categoriesMapper, productsMapper);

        // 4. Вывести топ 3 самых дорогих продукта
        System.out.println("Самые дорогие продукты");
        List<Products> topExpensiveProducts = getTopExpensiveProducts(productsMapper, 3);
        for (Products p:
            topExpensiveProducts) {
            System.out.println(p);
        }

        dbService.closeSession();
    }

    public static Categories createCategory(CategoriesMapper mapper, String name) {
        Categories cat = new Categories();
        cat.setTitle(name);
        mapper.insert(cat);
        return cat;
    }

    private static List<Categories> getAllCategories(CategoriesMapper mapper) {
        return mapper.selectByExample(new CategoriesExample());
    }

    private static List<Products> getAProductsByCategoryId(ProductsMapper mapper, Long categoryId) {
        ProductsExample filter = new ProductsExample();
        filter.createCriteria()
                .andCategoryIdEqualTo(categoryId);
        return mapper.selectByExample(filter);
    }

    private static void printAllCategories(CategoriesMapper cm, ProductsMapper pm) {
        List<Categories> allCategories = getAllCategories(cm);
        for (Categories x :
                allCategories) {
            System.out.println("категория = " + x.getTitle());
            List<Products> allProducts = getAProductsByCategoryId(pm, x.getId());
            for (Products p :
                    allProducts) {
                System.out.println(p);

            }
        }

    }

    private static List<Products> getTopExpensiveProducts(ProductsMapper pm, int count) {

        ProductsExample pe = new ProductsExample();
        pe.setOrderByClause("PRICE DESC");
        List<Products> products = pm.selectByExample(pe);
        int c = products.size() < count
                ? products.size()
                : count;
        return products.subList(0, c);
    }
}
