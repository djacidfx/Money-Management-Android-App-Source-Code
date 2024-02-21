package com.expance.manager.Database.Dao;

import androidx.lifecycle.LiveData;
import com.expance.manager.Database.Entity.WalletEntity;
import com.expance.manager.Model.CalendarSummary;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Model.Stats;
import com.expance.manager.Model.Trans;
import com.expance.manager.Model.WalletDetail;
import com.expance.manager.Model.WalletTrans;
import com.expance.manager.Model.Wallets;
import java.util.List;

/* loaded from: classes3.dex */
public interface WalletDaoObject {
    void archiveWallet(int id);

    void deleteWallet(int id, int active);

    List<Stats> getAllExpensePieStats(int walletId);

    List<Stats> getAllIncomePieStats(int walletId);

    List<DailyTrans> getAllOverviewTrans(int accountId, int walletId);

    List<DailyTrans> getAllPieExpenseTransferTrans(int accountId, int walletId);

    List<DailyTrans> getAllPieIncomeTransferTrans(int accountId, int walletId);

    List<DailyTrans> getAllPieTrans(int accountId, int walletId, int categoryId, int type);

    LiveData<List<Wallets>> getAllWallets(int accountId, int active, long date);

    LiveData<Integer> getArchieveWalletRow(int accountId);

    List<WalletEntity> getCurrencyWalletEntity(int id, String code);

    List<DailyTrans> getDailyTrans(int accountId, int walletId);

    List<DailyTrans> getDailyTrans(int accountId, int walletId, int categoryId);

    List<Stats> getExpensePieStats(int walletId, long startDate, long endDate);

    LiveData<List<Wallets>> getHiddenWallets(int accountId, int active, long date);

    List<Stats> getIncomePieStats(int walletId, long startDate, long endDate);

    LiveData<WalletDetail> getLiveWalletById(int id, int accountId, long date);

    List<DailyTrans> getOverviewTrans(int accountId, int walletId, long startDate, long endDate);

    List<Trans> getOverviewTransFromDate(int accountId, int walletId, long startDate, long endDate);

    List<DailyTrans> getPieExpenseTransferTrans(int accountId, int walletId, long startDate, long endDate);

    List<Trans> getPieExpenseTransferTransFromDate(int accountId, int walletId, long startDate, long endDate);

    List<DailyTrans> getPieIncomeTransferTrans(int accountId, int walletId, long startDate, long endDate);

    List<Trans> getPieIncomeTransferTransFromDate(int accountId, int walletId, long startDate, long endDate);

    List<DailyTrans> getPieTrans(int accountId, int walletId, int categoryId, long startDate, long endDate, int type);

    List<Trans> getPieTransFromDate(int accountId, int walletId, int categoryId, long startDate, long endDate, int type);

    List<Trans> getTopFiveSpending(int walletId);

    List<Trans> getTopFiveSpending(int walletId, long startDate, long endDate);

    List<Trans> getTransFromDate(int accountId, int walletId, int categoryId, long startDate, long endDate);

    List<Trans> getTransFromDate(int accountId, int walletId, long startDate, long endDate);

    long getWalletBalance(int id, long date);

    WalletEntity getWalletById(int id);

    int getWalletLastOrdering(int accountId);

    CalendarSummary getWalletOverview(int id);

    CalendarSummary getWalletOverview(int id, long startDate, long endDate);

    List<WalletTrans> getWalletTransById(int id, int accountId);

    List<Wallets> getWallets(int accountId, int active, long date);

    void insertWallet(WalletEntity walletEntity);

    void unArchiveWallet(int id);

    void updateWallet(WalletEntity walletEntity);

    void updateWalletOrdering(int order, int id);
}
