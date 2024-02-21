package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class TransactionViewModel extends AndroidViewModel {
    private LiveData<List<DailyTrans>> trans;

    public TransactionViewModel(Application application) {
        super(application);
        this.trans = AppDatabaseObject.getInstance(getApplication()).transDaoObject().getDailyTrans(SharePreferenceHelper.getAccountId(getApplication()));
    }

    public LiveData<List<DailyTrans>> getTrans() {
        return this.trans;
    }
}
