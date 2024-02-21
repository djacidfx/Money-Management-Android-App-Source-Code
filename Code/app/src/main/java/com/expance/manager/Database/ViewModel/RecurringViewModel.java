package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Recurring;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class RecurringViewModel extends AndroidViewModel {
    private LiveData<List<Recurring>> recurringList;

    public RecurringViewModel(Application application) {
        super(application);
        AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(getApplication());
        this.recurringList = appDatabaseObject.recurringDaoObject().getLiveRecurringList(SharePreferenceHelper.getAccountId(getApplication()));
    }

    public LiveData<List<Recurring>> getRecurringList() {
        return this.recurringList;
    }
}
