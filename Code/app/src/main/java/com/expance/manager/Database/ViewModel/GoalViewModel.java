package com.expance.manager.Database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.Goal;
import com.expance.manager.Utility.SharePreferenceHelper;
import java.util.List;

/* loaded from: classes3.dex */
public class GoalViewModel extends AndroidViewModel {
    private LiveData<List<Goal>> goalList;

    public GoalViewModel(Application application) {
        super(application);
        AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(getApplication());
        this.goalList = appDatabaseObject.goalDaoObject().getGoal(SharePreferenceHelper.getAccountId(getApplication()), 0);
    }

    public LiveData<List<Goal>> getGoalList() {
        return this.goalList;
    }
}
