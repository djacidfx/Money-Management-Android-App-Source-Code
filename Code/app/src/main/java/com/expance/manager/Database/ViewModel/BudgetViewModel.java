package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Budget;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class BudgetViewModel extends AndroidViewModel {
    private LiveData<List<Budget>> budgetList;

    public BudgetViewModel(Application application) {
        super(application);
        AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(getApplication());
        this.budgetList = appDatabaseObject.budgetDaoObject().getBudget(SharePreferenceHelper.getAccountId(getApplication()), 0);
    }

    public LiveData<List<Budget>> getBudgetList() {
        return this.budgetList;
    }
}
