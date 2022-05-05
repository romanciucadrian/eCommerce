package com.shopme.admin.repository;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository  extends PagingAndSortingRepository<Category,Integer> {

        @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
        public List<Category> findRootCategories(Sort sort);

         public Category findByName(String name);

         public Category findByAlias(String alias);

         @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id= ?1")
         @Modifying
         void updateEnabledStatus(Integer id, boolean enabled);


}
