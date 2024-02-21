package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Budget;

/* loaded from: classes3.dex */
public class BudgetDetailViewModel extends AndroidViewModel {
    private LiveData<Budget> budget;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BudgetDetailViewModel(Application application, int budgetId) {
        super(application);
        this.budget = AppDatabaseObject.getInstance(getApplication()).budgetDaoObject().getLiveBudgetById(budgetId);
    }

    public LiveData<Budget> getBudget() {
        return this.budget;
    }
}
