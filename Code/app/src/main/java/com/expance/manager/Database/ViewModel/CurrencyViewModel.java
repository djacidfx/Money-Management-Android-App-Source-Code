package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Currencies;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class CurrencyViewModel extends AndroidViewModel {
    private final LiveData<List<Currencies>> currencyList;

    public CurrencyViewModel(Application application) {
        super(application);
        AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(getApplication());
        this.currencyList = appDatabaseObject.currencyDaoObject().getAllCurrencies(SharePreferenceHelper.getAccountId(getApplication()));
    }

    public LiveData<List<Currencies>> getCurrencyList() {
        return this.currencyList;
    }
}
