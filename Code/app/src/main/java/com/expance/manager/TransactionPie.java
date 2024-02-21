package com.expance.manager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.expance.manager.Adapter.TransactionPieAdapter;
import com.expance.manager.Adapter.WalletCategoryTransactionAdapter;
import com.expance.manager.Broadcast.BroadcastUpdated;
import com.expance.manager.Database.AppDatabaseObject;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Model.Recurring;
import com.expance.manager.Model.RecurringCalendarDate;
import com.expance.manager.Model.SubcategoryStats;
import com.expance.manager.Model.Trans;
import com.expance.manager.Model.TransList;
import com.expance.manager.Utility.CalendarHelper;
import com.expance.manager.Utility.Constant;
import com.expance.manager.Utility.DateHelper;
import com.expance.manager.Utility.Helper;
import com.expance.manager.Utility.RecurringHelper;
import com.expance.manager.Utility.SharePreferenceHelper;
import com.expance.manager.Utility.StatisticHelper;
import com.expance.manager.Utility.TransactionHelper;
import com.expance.manager.Widget.BottomDateModeDialog;

import org.apache.http.cookie.ClientCookie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

import vocsy.ads.GoogleAds;

/* loaded from: classes3.dex */
public class TransactionPie extends AppCompatActivity implements WalletCategoryTransactionAdapter.OnItemClickListener, View.OnClickListener, BottomDateModeDialog.OnItemClickListener, TransactionPieAdapter.OnItemClickListener, BroadcastUpdated.OnBroadcastListener {
    TransactionPieAdapter adapter;
    ImageView backImage;
    BroadcastUpdated broadcastUpdated;
    int categoryId;
    Date date;
    TextView dateLabel;
    ConstraintLayout emptyWrapper;
    Date endDate;
    EditText endEditText;
    Date holderEndDate;
    Date holderStartDate;
    String name;
    ImageView nextImage;
    RecyclerView recyclerView;
    Date startDate;
    EditText startEditText;
    int mode = 2;
    int transMode = 1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("startDate", this.startDate.getTime());
        outState.putLong("endDate", this.endDate.getTime());
        outState.putLong("date", this.date.getTime());
        outState.putInt("mode", this.mode);
        outState.putInt("transMode", this.transMode);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(SharePreferenceHelper.getThemeMode(this) == 1 ? R.style.AppThemeNight : R.style.AppTheme);
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (SharePreferenceHelper.getThemeMode(this) == 1) {
//                getWindow().getDecorView().setSystemUiVisibility(0);
//            } else {
//                getWindow().getDecorView().setSystemUiVisibility(8192);
//            }
//        }
//        if (Build.VERSION.SDK_INT >= 26) {
//            if (SharePreferenceHelper.getThemeMode(this) == 1) {
//                getWindow().getDecorView().setSystemUiVisibility(0);
//            } else {
//                getWindow().getDecorView().setSystemUiVisibility(8208);
//            }
//            getWindow().setNavigationBarColor(Color.parseColor(SharePreferenceHelper.getThemeMode(this) == 1 ? "#1F1F1F" : "#F8F8F8"));
//        } else {
//            getWindow().setNavigationBarColor(Color.parseColor("#000000"));
//        }
        setContentView(R.layout.activity_transaction_pie);
        SystemConfiguration.setStatusBarColor(this, R.color.theam, SystemConfiguration.IconColor.ICON_DARK);
        GoogleAds.getInstance().admobBanner(this, (LinearLayout) findViewById(R.id.nativeLay));
        if (!getResources().getBoolean(R.bool.isTablet)) {
            setRequestedOrientation(1);
        }
        BroadcastUpdated broadcastUpdated = new BroadcastUpdated();
        this.broadcastUpdated = broadcastUpdated;
        broadcastUpdated.setListener(this);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(getIntent().getLongExtra("date", java.util.Calendar.getInstance().getTimeInMillis()));
        this.date = calendar.getTime();
        this.name = getIntent().getStringExtra("name");
        this.categoryId = getIntent().getIntExtra("categoryId", 0);
        this.mode = getIntent().getIntExtra("mode", 2);
        this.transMode = getIntent().getIntExtra("transMode", 1);
        calendar.setTimeInMillis(getIntent().getLongExtra("startDate", java.util.Calendar.getInstance().getTimeInMillis()));
        this.startDate = calendar.getTime();
        calendar.setTimeInMillis(getIntent().getLongExtra("endDate", java.util.Calendar.getInstance().getTimeInMillis()));
        this.endDate = calendar.getTime();
        if (savedInstanceState != null) {
            calendar.setTimeInMillis(savedInstanceState.getLong("date"));
            this.date = calendar.getTime();
            this.mode = savedInstanceState.getInt("mode");
            this.transMode = savedInstanceState.getInt("transMode");
            calendar.setTimeInMillis(savedInstanceState.getLong("startDate"));
            this.startDate = calendar.getTime();
            calendar.setTimeInMillis(savedInstanceState.getLong("endDate"));
            this.endDate = calendar.getTime();
        }
        TransactionPieAdapter transactionPieAdapter = new TransactionPieAdapter(this);
        this.adapter = transactionPieAdapter;
        transactionPieAdapter.setListener(this);
        this.adapter.setMode(this.transMode);
        setUpLayout();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        AppEngine appEngine = (AppEngine) getApplication();
        if (appEngine.wasInBackground()) {
            appEngine.setWasInBackground(false);
            if (SharePreferenceHelper.checkPasscode(getApplicationContext())) {
                Intent intent = new Intent(getApplicationContext(), Passcode.class);
                intent.addFlags(65536);
                intent.addFlags(268435456);
                startActivity(intent);
            }
        }
    }

    private void setUpLayout() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(this.name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.backImage = (ImageView) findViewById(R.id.backImage);
        this.nextImage = (ImageView) findViewById(R.id.nextImage);
        this.dateLabel = (TextView) findViewById(R.id.dateLabel);
        this.emptyWrapper = (ConstraintLayout) findViewById(R.id.emptyWrapper);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(this.adapter);
        this.backImage.setOnClickListener(this);
        this.nextImage.setOnClickListener(this);
        this.dateLabel.setText(CalendarHelper.getPieFormattedDate(this, this.date, this.mode));
        long pieStartDate = StatisticHelper.getPieStartDate(getApplicationContext(), this.date, this.mode);
        long pieEndDate = StatisticHelper.getPieEndDate(getApplicationContext(), this.date, this.mode);
        if ((pieStartDate == 0 && pieEndDate == 0) || this.mode == 6) {
            this.backImage.setAlpha(0.25f);
            this.nextImage.setAlpha(0.25f);
            int i = this.mode;
            if (i == 6) {
                this.dateLabel.setText(CalendarHelper.getFormattedCustomDate(this, this.startDate, this.endDate));
                return;
            } else {
                this.dateLabel.setText(CalendarHelper.getPieFormattedDate(this, this.date, i));
                return;
            }
        }
        this.backImage.setAlpha(1.0f);
        this.nextImage.setAlpha(1.0f);
        this.dateLabel.setText(CalendarHelper.getPieFormattedDate(this, this.date, this.mode));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        long pieStartDate;
        long pieEndDate;
        super.onResume();
        int i = this.mode;
        if (i == 6) {
            pieStartDate = CalendarHelper.getCustomStartDate(this.startDate);
            pieEndDate = CalendarHelper.getCustomEndDate(this.endDate);
        } else {
            pieStartDate = StatisticHelper.getPieStartDate(this, this.date, i);
            pieEndDate = StatisticHelper.getPieEndDate(this, this.date, this.mode);
        }
        populateData(pieStartDate, pieEndDate);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastUpdated, new IntentFilter(Constant.BROADCAST_UPDATED));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastUpdated);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.ktwapps.walletmanager.TransactionPie$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 implements Runnable {
        final /* synthetic */ long val$endDate;
        final /* synthetic */ long val$startDate;

        AnonymousClass1(final long val$startDate, final long val$endDate) {
            this.val$startDate = val$startDate;
            this.val$endDate = val$endDate;
        }

        @Override // java.lang.Runnable
        public void run() {
            final List<SubcategoryStats> subcategoryStats;
            List<DailyTrans> pieTrans;
            ArrayList arrayList;
            boolean z;
            ArrayList arrayList2;
            boolean z2;
            AppDatabaseObject appDatabaseObject = AppDatabaseObject.getInstance(TransactionPie.this.getApplicationContext());
            int accountId = SharePreferenceHelper.getAccountId(TransactionPie.this.getApplicationContext());
            ArrayList arrayList3 = new ArrayList();
            if (this.val$startDate == 0 && this.val$endDate == 0) {
                List<SubcategoryStats> allSubcategoryStats = appDatabaseObject.statisticDaoObject().getAllSubcategoryStats(accountId, TransactionPie.this.categoryId);
                pieTrans = appDatabaseObject.statisticDaoObject().getAllPieTrans(accountId, TransactionPie.this.categoryId, TransactionPie.this.transMode);
                subcategoryStats = allSubcategoryStats;
            } else {
                subcategoryStats = appDatabaseObject.statisticDaoObject().getSubcategoryStats(accountId, this.val$startDate, this.val$endDate, TransactionPie.this.categoryId);
                pieTrans = appDatabaseObject.statisticDaoObject().getPieTrans(accountId, TransactionPie.this.categoryId, this.val$startDate, this.val$endDate, TransactionPie.this.transMode);
            }
            ArrayList<Trans> arrayList4 = new ArrayList();
            ArrayList<DailyTrans> arrayList5 = new ArrayList();
            boolean z3 = true;
            if (this.val$startDate == 0 || this.val$endDate == 0) {
                arrayList = arrayList3;
            } else {
                for (Recurring recurring : appDatabaseObject.recurringDaoObject().getAllRecurringListByAccountId(SharePreferenceHelper.getAccountId(TransactionPie.this.getApplicationContext()))) {
                    if (recurring.getIsFuture() == 1 && recurring.getCategoryId() == TransactionPie.this.categoryId) {
                        arrayList2 = arrayList3;
                        for (RecurringCalendarDate recurringCalendarDate : RecurringHelper.getStatisticRecurring(recurring, appDatabaseObject.currencyDaoObject().getCurrencyRate(SharePreferenceHelper.getAccountId(TransactionPie.this.getApplicationContext()), recurring.getCurrency()), this.val$startDate, this.val$endDate)) {
                            Trans trans = new Trans(recurring.getNote(TransactionPie.this.getApplicationContext()), recurring.getMemo(), recurring.getColor(), recurring.getIcon(), recurring.getCurrency(), recurringCalendarDate.getDate(), recurring.getAmount(), recurring.getWallet(), recurring.getType(), "", recurring.getWalletId(), 0, recurring.getCategory(TransactionPie.this.getApplicationContext()), recurring.getCategoryId(), recurring.getCategoryDefault(), 0, "", 0L, null, 0, 0, 0, 0, "", 0);
                            trans.setRecurring(true);
                            trans.setRecurringId(recurring.getId());
                            arrayList4.add(trans);
                            Iterator it = arrayList5.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    z2 = false;
                                    break;
                                }
                                DailyTrans dailyTrans = (DailyTrans) it.next();
                                if (dailyTrans.getYear() == recurringCalendarDate.getYear() && dailyTrans.getMonth() == recurringCalendarDate.getMonth() && dailyTrans.getDay() == recurringCalendarDate.getDay()) {
                                    dailyTrans.setAmount(dailyTrans.getAmount() + recurringCalendarDate.getAmount());
                                    z2 = true;
                                    break;
                                }
                            }
                            if (!z2) {
                                arrayList5.add(new DailyTrans(recurringCalendarDate.getDay(), recurringCalendarDate.getMonth(), recurringCalendarDate.getYear(), recurringCalendarDate.getAmount()));
                            }
                        }
                    } else {
                        arrayList2 = arrayList3;
                    }
                    arrayList3 = arrayList2;
                }
                arrayList = arrayList3;
                for (DailyTrans dailyTrans2 : arrayList5) {
                    Iterator<DailyTrans> it2 = pieTrans.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = false;
                            break;
                        }
                        DailyTrans next = it2.next();
                        if (dailyTrans2.getYear() == next.getYear() && dailyTrans2.getMonth() == next.getMonth() && dailyTrans2.getDay() == next.getDay()) {
                            next.setAmount(dailyTrans2.getAmount() + next.getAmount());
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        pieTrans.add(dailyTrans2);
                    }
                }
                Collections.sort(pieTrans, new Comparator<DailyTrans>() { // from class: com.ktwapps.walletmanager.TransactionPie.1.1
                    @Override // java.util.Comparator
                    public int compare(DailyTrans t1, DailyTrans t2) {
                        if (t1.getDateTime() == null || t2.getDateTime() == null) {
                            return 0;
                        }
                        return t2.getDateTime().compareTo(t1.getDateTime());
                    }
                });
            }
            for (DailyTrans dailyTrans3 : pieTrans) {
                ArrayList arrayList6 = arrayList;
                arrayList6.add(new TransList(z3, null, dailyTrans3));
                Date dateTime = dailyTrans3.getDateTime();
                AppDatabaseObject appDatabaseObject2 = appDatabaseObject;
                for (Trans trans2 : appDatabaseObject.statisticDaoObject().getPieTransFromDate(accountId, TransactionPie.this.categoryId, DateHelper.getTransactionStartDate(dateTime), DateHelper.getTransactionEndDate(dateTime), TransactionPie.this.transMode)) {
                    arrayList6.add(new TransList(false, trans2, null));
                }
                for (Trans trans3 : arrayList4) {
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(trans3.getDateTime());
                    if (calendar.get(1) == dailyTrans3.getYear() && calendar.get(2) == dailyTrans3.getMonth() - 1 && calendar.get(5) == dailyTrans3.getDay()) {
                        arrayList6.add(new TransList(false, trans3, null));
                    }
                }
                arrayList = arrayList6;
                appDatabaseObject = appDatabaseObject2;
                z3 = true;
            }
            final ArrayList arrayList7 = arrayList;
            TransactionPie.this.runOnUiThread(new Runnable() { // from class: com.ktwapps.walletmanager.TransactionPie$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TransactionPie.AnonymousClass1.this.m231lambda$run$0$comktwappswalletmanagerTransactionPie$1(arrayList7, subcategoryStats);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$run$0$com-ktwapps-walletmanager-TransactionPie$1  reason: not valid java name */
        public /* synthetic */ void m231lambda$run$0$comktwappswalletmanagerTransactionPie$1(List list, List list2) {
            TransactionPie transactionPie;
            int i;
            TransactionPie.this.emptyWrapper.setVisibility(list.size() == 0 ? 0 : 8);
            RecyclerView recyclerView = TransactionPie.this.recyclerView;
            if (list.size() == 0) {
                transactionPie = TransactionPie.this;
                i = R.attr.emptyBackground;
            } else {
                transactionPie = TransactionPie.this;
                i = R.attr.primaryBackground;
            }
            recyclerView.setBackgroundColor(Helper.getAttributeColor(transactionPie, i));
            TransactionPie.this.adapter.setList(list);
            TransactionPie.this.adapter.setSubcategoryStat(list2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateData(final long startDate, final long endDate) {
        Executors.newSingleThreadExecutor().execute(new AnonymousClass1(startDate, endDate));
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(R.anim.scale_in, R.anim.right_to_left);
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.scale_in, R.anim.right_to_left);
    }

    @Override
    // com.ktwapps.walletmanager.Adapter.WalletCategoryTransactionAdapter.OnItemClickListener, com.ktwapps.walletmanager.Adapter.TransactionPieAdapter.OnItemClickListener
    public void OnItemClick(View view, int position) {
        Trans trans = this.adapter.list.get(position).getTrans();
        if (view.getId() == R.id.image1 || view.getId() == R.id.image2 || view.getId() == R.id.image3) {
            Intent intent = new Intent(this, PhotoViewer.class);
            intent.putExtra(ClientCookie.PATH_ATTR, trans.getMedia());
            intent.putExtra(FirebaseAnalytics.Param.INDEX, ((Integer) view.getTag()).intValue());
            startActivity(intent);
        } else if (trans.getDebtId() != 0 && trans.getDebtTransId() == 0) {
            Intent intent2 = new Intent(this, DebtDetails.class);
            intent2.putExtra("debtId", trans.getDebtId());
            startActivity(intent2);
            overridePendingTransition(R.anim.left_to_right, R.anim.scale_out);
        } else {
            Intent intent3 = new Intent(this, Details.class);
            intent3.putExtra("transId", trans.getId());
            startActivity(intent3);
            overridePendingTransition(R.anim.left_to_right, R.anim.scale_out);
        }
    }

    @Override
    // com.ktwapps.walletmanager.Adapter.WalletCategoryTransactionAdapter.OnItemClickListener, com.ktwapps.walletmanager.Adapter.TransactionPieAdapter.OnItemClickListener
    public void OnItemLongClick(View view, int position) {
        TransactionHelper.showPopupMenu(this, this.adapter.list.get(position).getTrans(), view);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_date_mode, menu);
        for (int i = 0; i < menu.size(); i++) {
            Drawable icon = menu.getItem(i).getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(Helper.getAttributeColor(this, R.attr.primaryDarkTextColor), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_action_date_mode) {
            BottomDateModeDialog bottomDateModeDialog = new BottomDateModeDialog();
            bottomDateModeDialog.setMode(this.mode);
            bottomDateModeDialog.setListener(this);
            bottomDateModeDialog.show(getSupportFragmentManager(), "date_mode");
            return false;
        }
        return false;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.backImage /* 2131230831 */) {
            int i = this.mode;
            if (i != 5 && i != 6) {
                Date incrementPieDate = StatisticHelper.incrementPieDate(this.date, i, -1);
                this.date = incrementPieDate;
                this.dateLabel.setText(CalendarHelper.getPieFormattedDate(this, incrementPieDate, this.mode));
                populateData(StatisticHelper.getPieStartDate(getApplicationContext(), this.date, this.mode), StatisticHelper.getPieEndDate(getApplicationContext(), this.date, this.mode));
            }
        } else if (view.getId() == R.id.endDateEditText /* 2131231051 */) {
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i2, int i1, int i22) {
                    TransactionPie.this.holderEndDate = CalendarHelper.getDateFromPicker(i2, i1, i22);
                    TransactionPie.this.endEditText.setText(DateHelper.getFormattedDate(TransactionPie.this.getApplicationContext(), TransactionPie.this.holderEndDate));
                    if (DateHelper.isBeforeDate(TransactionPie.this.holderStartDate, TransactionPie.this.holderEndDate)) {
                        TransactionPie.this.holderStartDate = CalendarHelper.getDateFromPicker(i2, i1, i22);
                        TransactionPie.this.startEditText.setText(DateHelper.getFormattedDate(TransactionPie.this.getApplicationContext(), TransactionPie.this.holderStartDate));
                    }
                }
            }, CalendarHelper.getYearFromDate(this.holderEndDate), CalendarHelper.getMonthFromDate(this.holderEndDate), CalendarHelper.getDayFromDate(this.holderEndDate)).show();
        } else if (view.getId() == R.id.nextImage /* 2131231339 */) {
            int i2 = this.mode;
            if (i2 != 5 && i2 != 6) {
                Date incrementPieDate2 = StatisticHelper.incrementPieDate(this.date, i2, 1);
                this.date = incrementPieDate2;
                this.dateLabel.setText(CalendarHelper.getPieFormattedDate(this, incrementPieDate2, this.mode));
                populateData(StatisticHelper.getPieStartDate(getApplicationContext(), this.date, this.mode), StatisticHelper.getPieEndDate(getApplicationContext(), this.date, this.mode));
            }
        } else if (view.getId() == R.id.startDateEditText /* 2131231542 */) {
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i3, int i1, int i22) {
                    TransactionPie.this.holderStartDate = CalendarHelper.getDateFromPicker(i3, i1, i22);
                    TransactionPie.this.startEditText.setText(DateHelper.getFormattedDate(TransactionPie.this.getApplicationContext(), TransactionPie.this.holderStartDate));
                    if (DateHelper.isBeforeDate(TransactionPie.this.holderStartDate, TransactionPie.this.holderEndDate)) {
                        TransactionPie.this.holderEndDate = CalendarHelper.getDateFromPicker(i3, i1, i22);
                        TransactionPie.this.endEditText.setText(DateHelper.getFormattedDate(TransactionPie.this.getApplicationContext(), TransactionPie.this.holderEndDate));
                    }
                }
            }, CalendarHelper.getYearFromDate(this.holderStartDate), CalendarHelper.getMonthFromDate(this.holderStartDate), CalendarHelper.getDayFromDate(this.holderStartDate)).show();
        }
    }

    @Override // com.ktwapps.walletmanager.Widget.BottomDateModeDialog.OnItemClickListener
    public void onItemClick(int mode) {
        if (mode == 6) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_custom_date, (ViewGroup) null);
            this.startEditText = (EditText) inflate.findViewById(R.id.startDateEditText);
            this.endEditText = (EditText) inflate.findViewById(R.id.endDateEditText);
            this.startEditText.setText(DateHelper.getFormattedDate(this, this.startDate));
            this.endEditText.setText(DateHelper.getFormattedDate(this, this.endDate));
            this.startEditText.setFocusable(false);
            this.startEditText.setLongClickable(false);
            this.startEditText.setOnClickListener(this);
            this.endEditText.setFocusable(false);
            this.endEditText.setLongClickable(false);
            this.endEditText.setOnClickListener(this);
            this.holderStartDate = this.startDate;
            this.holderEndDate = this.endDate;
            builder.setView(inflate);
            builder.setTitle(R.string.select_date);
            builder.setPositiveButton(R.string.done, new DialogInterface.OnClickListener() { // from class: com.ktwapps.walletmanager.TransactionPie.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    TransactionPie transactionPie = TransactionPie.this;
                    transactionPie.startDate = transactionPie.holderStartDate;
                    TransactionPie transactionPie2 = TransactionPie.this;
                    transactionPie2.endDate = transactionPie2.holderEndDate;
                    TransactionPie.this.dateLabel.setText(CalendarHelper.getFormattedCustomDate(TransactionPie.this.getApplicationContext(), TransactionPie.this.startDate, TransactionPie.this.endDate));
                    TransactionPie.this.populateData(CalendarHelper.getCustomStartDate(TransactionPie.this.startDate), CalendarHelper.getCustomEndDate(TransactionPie.this.endDate));
                    TransactionPie.this.mode = 6;
                    TransactionPie.this.backImage.setAlpha(0.25f);
                    TransactionPie.this.nextImage.setAlpha(0.25f);
                }
            });
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            builder.show();
            return;
        }
        this.mode = mode;
        this.dateLabel.setText(CalendarHelper.getPieFormattedDate(this, this.date, mode));
        long pieStartDate = StatisticHelper.getPieStartDate(getApplicationContext(), this.date, mode);
        long pieEndDate = StatisticHelper.getPieEndDate(getApplicationContext(), this.date, mode);
        if (pieStartDate == 0 && pieEndDate == 0) {
            this.backImage.setAlpha(0.35f);
            this.nextImage.setAlpha(0.35f);
        } else {
            this.backImage.setAlpha(1.0f);
            this.nextImage.setAlpha(1.0f);
        }
        populateData(pieStartDate, pieEndDate);
    }

    @Override // com.ktwapps.walletmanager.Broadcast.BroadcastUpdated.OnBroadcastListener
    public void getBroadcast() {
        long pieStartDate;
        long pieEndDate;
        int i = this.mode;
        if (i == 6) {
            pieStartDate = CalendarHelper.getCustomStartDate(this.startDate);
            pieEndDate = CalendarHelper.getCustomEndDate(this.endDate);
        } else {
            pieStartDate = StatisticHelper.getPieStartDate(this, this.date, i);
            pieEndDate = StatisticHelper.getPieEndDate(this, this.date, this.mode);
        }
        populateData(pieStartDate, pieEndDate);
    }
}
