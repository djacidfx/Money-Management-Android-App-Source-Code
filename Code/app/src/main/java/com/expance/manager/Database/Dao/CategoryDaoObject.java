package com.expance.manager.Database.Dao;

import androidx.lifecycle.LiveData;
import com.expance.manager.Database.Entity.CategoryEntity;
import com.expance.manager.Database.Entity.SubcategoryEntity;
import com.expance.manager.Model.Category;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Model.Trans;
import java.util.List;

/* loaded from: classes3.dex */
public interface CategoryDaoObject {
    void deleteSubcategoryById(int id);

    int getAdjustmentId(int accountId, int type);

    Category[] getCategory(int type, int active, int accountId);

    CategoryEntity getCategoryById(int id);

    int getCategoryLastOrdering(int accountId, int type);

    List<DailyTrans> getDailyTrans(int accountId, int categoryId, long startDate, long endDate);

    int getFeeCategoryId(int accountId);

    LiveData<List<Category>> getLiveCategory(int type, int active, int accountId);

    List<Category> getSearchCategory(int active, int accountId);

    List<SubcategoryEntity> getSubCategoryByCategoryId(int id);

    SubcategoryEntity getSubcategoryById(int id);

    int getSubcategoryLastOrdering(int id);

    int getTotalSubcategoryByCategoryId(int categoryId);

    List<Trans> getTransFromDate(int accountId, int categoryId, long startDate, long endDate);

    long[] insertCategory(CategoryEntity... categoryEntities);

    long insertSubcategory(SubcategoryEntity subCategoryEntity);

    void updateCategory(CategoryEntity categoryEntity);

    void updateCategoryOrdering(int ordering, int id);

    void updateStatus(int id, int status);

    void updateSubcategory(SubcategoryEntity subCategoryEntity);

    void updateSubcategoryOrdering(int ordering, int id);
}
