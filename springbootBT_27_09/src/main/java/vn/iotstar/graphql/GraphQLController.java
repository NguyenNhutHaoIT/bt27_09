package vn.iotstar.graphql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import vn.iotstar.entity.*;
import vn.iotstar.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller

public class GraphQLController {
	@Autowired
    private  UserRepository userRepository ;
	@Autowired
    private  ProductRepository productRepository;
	@Autowired
    private  CategoryRepository categoryRepository;

    // ================= QUERIES =================

    @QueryMapping
    public List<Product> sanPhamTheoGiaTangDan() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @QueryMapping
    public List<Product> sanPhamTheoDanhMuc(@Argument Long danhMucId) {
        return productRepository.findDistinctByCategories_Id(danhMucId);
    }

    @QueryMapping
    public List<Product> tatCaProduct() {
        return productRepository.findAll();
    }

    @QueryMapping
    public List<User> tatCaUser() {
        return userRepository.findAll();
    }

    @QueryMapping
    public List<Category> tatCaCategory() {
        return categoryRepository.findAll();
    }

    @QueryMapping
    public User layUser(@Argument Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // ================= MUTATIONS =================

    // ---- User ----
    @MutationMapping
    public User taoUser(@Argument String fullname,
                        @Argument String email,
                        @Argument String password,
                        @Argument String phone,
                        @Argument List<Long> categoryIds) {
        User u = new User();
        u.setFullname(fullname);
        u.setEmail(email);
        u.setPassword(password);
        u.setPhone(phone);

        if (categoryIds != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            u.setCategories(categories);
        }

        return userRepository.save(u);
    }

    @MutationMapping
    public User capNhatUser(@Argument Long id,
                            @Argument String fullname,
                            @Argument String email,
                            @Argument String password,
                            @Argument String phone,
                            @Argument List<Long> categoryIds) {
        User u = userRepository.findById(id).orElse(null);
        if (u == null) return null;

        if (fullname != null) u.setFullname(fullname);
        if (email != null) u.setEmail(email);
        if (password != null) u.setPassword(password);
        if (phone != null) u.setPhone(phone);

        if (categoryIds != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            u.setCategories(categories);
        }

        return userRepository.save(u);
    }

    @MutationMapping
    public Boolean xoaUser(@Argument Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }

    // ---- Category ----
    @MutationMapping
    public Category taoCategory(@Argument String name,
                                @Argument String images,
                                @Argument List<Long> productIds) {
        Category c = new Category();
        c.setName(name);
        c.setImages(images);

        if (productIds != null) {
            Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));
            c.setProducts(products);
        }

        return categoryRepository.save(c);
    }

    @MutationMapping
    public Category capNhatCategory(@Argument Long id,
                                    @Argument String name,
                                    @Argument String images,
                                    @Argument List<Long> productIds) {
        Category c = categoryRepository.findById(id).orElse(null);
        if (c == null) return null;

        if (name != null) c.setName(name);
        if (images != null) c.setImages(images);

        if (productIds != null) {
            Set<Product> products = new HashSet<>(productRepository.findAllById(productIds));
            c.setProducts(products);
        }

        return categoryRepository.save(c);
    }

    @MutationMapping
    public Boolean xoaCategory(@Argument Long id) {
        if (!categoryRepository.existsById(id)) return false;
        categoryRepository.deleteById(id);
        return true;
    }

    // ---- Product ----
    @MutationMapping
    public Product taoProduct(@Argument String title,
                              @Argument Integer quantity,
                              @Argument String desc,
                              @Argument Double price,
                              @Argument Long userId,
                              @Argument List<Long> categoryIds) {
        Product p = new Product();
        p.setTitle(title);
        p.setQuantity(quantity);
        p.setDesc(desc);
        p.setPrice(price);

        if (userId != null) {
            User u = userRepository.findById(userId).orElse(null);
            p.setUser(u);
        }

        if (categoryIds != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            p.setCategories(categories);
        }

        return productRepository.save(p);
    }

    @MutationMapping
    public Product capNhatProduct(@Argument Long id,
                                  @Argument String title,
                                  @Argument Integer quantity,
                                  @Argument String desc,
                                  @Argument Double price,
                                  @Argument Long userId,
                                  @Argument List<Long> categoryIds) {
        Product p = productRepository.findById(id).orElse(null);
        if (p == null) return null;

        if (title != null) p.setTitle(title);
        if (quantity != null) p.setQuantity(quantity);
        if (desc != null) p.setDesc(desc);
        if (price != null) p.setPrice(price);

        if (userId != null) {
            User u = userRepository.findById(userId).orElse(null);
            p.setUser(u);
        }

        if (categoryIds != null) {
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
            p.setCategories(categories);
        }

        return productRepository.save(p);
    }

    @MutationMapping
    public Boolean xoaProduct(@Argument Long id) {
        if (!productRepository.existsById(id)) return false;
        productRepository.deleteById(id);
        return true;
    }
}
