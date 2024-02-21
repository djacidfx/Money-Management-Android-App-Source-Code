package com.expance.manager.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.expance.manager.Model.CalendarSummary;
import com.expance.manager.Model.DailyTrans;
import com.expance.manager.Model.Trans;
import com.expance.manager.Model.TransList;
import com.expance.manager.R;
import com.expance.manager.Utility.DataHelper;
import com.expance.manager.Utility.DateHelper;
import com.expance.manager.Utility.Helper;
import com.expance.manager.Utility.SharePreferenceHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class TransactionOverviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    CalendarSummary calendarSummary;
    Context context;
    LayoutInflater inflater;
    public List<TransList> list = new ArrayList();
    OnItemClickListener listener;
    String symbol;

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void OnItemClick(View view, int position);

        void OnItemLongClick(View view, int position);
    }

    public TransactionOverviewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new HeaderViewHolder(this.inflater.inflate(R.layout.list_transaction_header, parent, false));
        }
        if (viewType == 1) {
            return new TransactionViewHolder(this.inflater.inflate(R.layout.list_transaction, parent, false));
        }
        return new OverallViewHolder(this.inflater.inflate(R.layout.list_transaction_overview, parent, false));
    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder var1, int var2) {
//        int var3 = this.getItemViewType(var2);
//        String var5;
//        String var6;
//        if (var3 == 0) {
//            var6 = this.symbol;
//            var5 = var6;
//            if (var6 == null) {
//                var5 = SharePreferenceHelper.getAccountSymbol(this.context);
//            }
//
//            DailyTrans var7 = ((TransList) this.list.get(var2 - 1)).getDailyTrans();
//            HeaderViewHolder var13 = (HeaderViewHolder) var1;
//            Date var19 = var7.getDateTime();
//            var5 = Helper.getBeautifyAmount(var5, var7.getAmount());
//            String var21 = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMM yyyy");
//            var13.dayLabel.setText((new SimpleDateFormat("dd", Locale.getDefault())).format(var19));
//            var13.monthLabel.setText((new SimpleDateFormat(var21, Locale.getDefault())).format(var19));
//            var13.amountLabel.setText(var5);
//            if (!SharePreferenceHelper.isFutureBalanceOn(this.context)) {
//                var13.weekLabel.setText((new SimpleDateFormat("EEEE", Locale.getDefault())).format(var19));
//            } else if (DateHelper.isDatePast(var19)) {
//                var13.weekLabel.setText((new SimpleDateFormat("EEEE", Locale.getDefault())).format(var19));
//            } else {
//                var13.weekLabel.setText(2131820818);
//            }
//        } else {
//            String var14 = null;
//            if (var3 == 1) {
//                Trans var23 = ((TransList) this.list.get(var2 - 1)).getTrans();
//                TransactionViewHolder var8 = (TransactionViewHolder) var1;
//                if (var14 != null) {
//                    var14 = var23.getCurrency();
//                }
//                var5 = (String) DataHelper.getCurrencySymbolList().get(DataHelper.getCurrencyCode().indexOf(var14));
//                String var11 = var23.getMedia();
//                String var9 = var23.getNote(this.context);
//                if (var23.getColor() == null) {
//                    var14 = "#A7A9AB";
//                } else {
//                    var14 = var23.getColor();
//                }
//
//                var6 = var23.getWallet();
//                int var4 = var23.getType();
//                Date var12 = var23.getDateTime();
//                String var10 = Helper.getBeautifyAmount(var5, var23.getAmount());
//                boolean var16;
//                if (0L > var23.getAmount()) {
//                    var16 = true;
//                } else {
//                    var16 = false;
//                }
//
//                if (DateFormat.is24HourFormat(this.context)) {
//                    var5 = "kk:mm";
//                } else {
//                    var5 = "hh:mm aa";
//                }
//
//                String var27 = (new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), var5), Locale.getDefault())).format(var12);
//                if (var11 != null && var11.trim().length() != 0) {
//                    String[] var24 = var11.split(",");
//                    if (var24.length == 1) {
//                        var8.imageView1.setVisibility(0);
//                        var8.imageView2.setVisibility(8);
//                        var8.imageView3.setVisibility(8);
//                        Picasso.get().load(new File(var24[0])).centerCrop().resize(100, 100).into(var8.imageView1);
//                    } else if (var24.length == 2) {
//                        var8.imageView1.setVisibility(0);
//                        var8.imageView2.setVisibility(0);
//                        var8.imageView3.setVisibility(8);
//                        Picasso.get().load(new File(var24[0])).centerCrop().resize(100, 100).into(var8.imageView1);
//                        Picasso.get().load(new File(var24[1])).centerCrop().resize(100, 100).into(var8.imageView2);
//                    } else if (var24.length == 3) {
//                        var8.imageView1.setVisibility(0);
//                        var8.imageView2.setVisibility(0);
//                        var8.imageView3.setVisibility(0);
//                        Picasso.get().load(new File(var24[0])).centerCrop().resize(100, 100).into(var8.imageView1);
//                        Picasso.get().load(new File(var24[1])).centerCrop().resize(100, 100).into(var8.imageView2);
//                        Picasso.get().load(new File(var24[2])).centerCrop().resize(100, 100).into(var8.imageView3);
//                    }
//                } else {
//                    var8.imageView1.setVisibility(8);
//                    var8.imageView2.setVisibility(8);
//                    var8.imageView3.setVisibility(8);
//                }
//
//                if (var4 == 2) {
//                    StringBuilder var25 = new StringBuilder();
//                    var25.append(var6);
//                    var25.append(this.context.getResources().getString(2131821192));
//                    var25.append(var23.getTransferWallet());
//                    var5 = var25.toString();
//                    var8.imageView.setImageResource(2131165730);
//                } else {
//                    var8.imageView.setImageResource((Integer) DataHelper.getCategoryIcons().get(var23.getIcon()));
//                    var5 = var6;
//                }
//
//                var8.amountLabel.setText(var10);
//                TextView var20 = var8.amountLabel;
//                if (var23.getType() == 2) {
//                    var3 = Helper.getAttributeColor(this.context, 2130903970);
//                } else {
//                    Resources var26 = this.context.getResources();
//                    if (var16) {
//                        var3 = 2131034246;
//                    } else {
//                        var3 = 2131034255;
//                    }
//
//                    var3 = var26.getColor(var3);
//                }
//
//                var20.setTextColor(var3);
//                var8.nameLabel.setText(var9);
//                if (Build.VERSION.SDK_INT >= 29) {
//                    var8.colorView.getBackground().setColorFilter(new BlendModeColorFilter(Color.parseColor(var14), BlendMode.SRC_OVER));
//                } else {
//                    var8.colorView.getBackground().setColorFilter(Color.parseColor(var14), PorterDuff.Mode.SRC_OVER);
//                }
//
//                var8.detailLabel.setText(var5);
//                var8.timeLabel.setText(var27);
//                if (var2 >= this.list.size()) {
//                    var8.divider.setVisibility(0);
//                } else {
//                    TransList var17 = (TransList) this.list.get(var2);
//                    if (!var17.isDailyHeader() && var17.getTrans() != null) {
//                        var8.divider.setVisibility(8);
//                    } else {
//                        var8.divider.setVisibility(0);
//                    }
//                }
//
//                byte var15 = 0;
//                ImageView var18 = var8.recurringImage;
//                if (!var23.isRecurring()) {
//                    var15 = 8;
//                }
//
//                var18.setVisibility(var15);
//            } else if (var3 == 2 && this.calendarSummary != null) {
//                OverallViewHolder var22 = (OverallViewHolder) var1;
//                var5 = this.symbol;
//                var14 = var5;
//                if (var5 == null) {
//                    var14 = SharePreferenceHelper.getAccountSymbol(this.context);
//                }
//
//                var22.expenseLabel.setText(Helper.getBeautifyAmount(var14, this.calendarSummary.getExpense()));
//                var22.incomeLabel.setText(Helper.getBeautifyAmount(var14, this.calendarSummary.getIncome()));
//                var22.netLabel.setText(Helper.getBeautifyAmount(var14, this.calendarSummary.getNetIncome()));
//            }
//        }
//    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            // Handle HeaderViewHolder
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            Trans transaction = ((TransList) this.list.get(position - 1)).getTrans();
//            DailyTrans dailyTrans = ((TransList) this.list.get(position - 1)).getDailyTrans();

            if (transaction != null) { // aa null aave che
                String currencySymbol = this.symbol != null ? this.symbol : SharePreferenceHelper.getAccountSymbol(this.context);
                String formattedAmount = Helper.getBeautifyAmount(currencySymbol, transaction.getAmount());
                // Set data to HeaderViewHolder views
                headerViewHolder.dayLabel.setText((new SimpleDateFormat("dd", Locale.getDefault())).format(transaction.getDateTime()));
                headerViewHolder.monthLabel.setText((new SimpleDateFormat(DateFormat.getBestDateTimePattern(Locale.getDefault(), "MMM yyyy"), Locale.getDefault())).format(transaction.getDateTime()));
                headerViewHolder.amountLabel.setText(formattedAmount);

                if (!SharePreferenceHelper.isFutureBalanceOn(this.context) || DateHelper.isDatePast(transaction.getDateTime())) {
                    headerViewHolder.weekLabel.setText((new SimpleDateFormat("EEEE", Locale.getDefault())).format(transaction.getDateTime()));
                } else {
//                headerViewHolder.weekLabel.setText(R.string.your_week_label_string_resource);
                    headerViewHolder.weekLabel.setText("no data");
                }
                return;
            }
            Log.e("@@@", "onBindViewHolder: " + "dayli transaction was null" );

        } else if (viewType == 1) {
            // Handle TransactionViewHolder
            TransactionViewHolder transactionViewHolder = (TransactionViewHolder) viewHolder;
            TransList transList = (TransList) this.list.get(position - 1);
            Trans trans = transList.getTrans();

            if (trans != null) {
                String currencySymbol = ""; // Set a default currency symbol
                String currencyCode = trans.getCurrency();

                if (currencyCode != null) {
//                    Map<String, String> currencyMap = DataHelper.getCurrencySymbolMap();
//                    currencySymbol = currencyMap.getOrDefault(currencyCode, "");
                }

                // ... (rest of the data binding for TransactionViewHolder) ...

                // Reuse Picasso instance for image loading
//                Picasso.get().load(new File(var24[0])).centerCrop().resize(100, 100).into(transactionViewHolder.imageView1);

                // ... (rest of the data binding for TransactionViewHolder) ...
            }

        } else if (viewType == 2 && this.calendarSummary != null) {
            // Handle OverallViewHolder
            OverallViewHolder overallViewHolder = (OverallViewHolder) viewHolder;
            String currencySymbol = this.symbol != null ? this.symbol : SharePreferenceHelper.getAccountSymbol(this.context);

            // Set data to OverallViewHolder views
            overallViewHolder.expenseLabel.setText(Helper.getBeautifyAmount(currencySymbol, this.calendarSummary.getExpense()));
            overallViewHolder.incomeLabel.setText(Helper.getBeautifyAmount(currencySymbol, this.calendarSummary.getIncome()));
            overallViewHolder.netLabel.setText(Helper.getBeautifyAmount(currencySymbol, this.calendarSummary.getNetIncome()));
        }
    }


    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.list.size() == 0) {
            return 0;
        }
        return this.list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 2;
        }
        return this.list.get(position - 1).isDailyHeader() ? 1 : 0;
    }

    public void setSummary(CalendarSummary calendarSummary) {
        this.calendarSummary = calendarSummary;
        notifyDataSetChanged();
    }

    public void setList(List<TransList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /* loaded from: classes3.dex */
    private class OverallViewHolder extends RecyclerView.ViewHolder {
        TextView expenseLabel;
        TextView incomeLabel;
        TextView netLabel;

        OverallViewHolder(View itemView) {
            super(itemView);
            this.expenseLabel = (TextView) itemView.findViewById(R.id.expenseLabel);
            this.incomeLabel = (TextView) itemView.findViewById(R.id.incomeLabel);
            this.netLabel = (TextView) itemView.findViewById(R.id.netLabel);
        }
    }

    /* loaded from: classes3.dex */
    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView amountLabel;
        TextView dayLabel;
        TextView monthLabel;
        TextView weekLabel;

        HeaderViewHolder(View itemView) {
            super(itemView);
            this.dayLabel = (TextView) itemView.findViewById(R.id.dayLabel);
            this.weekLabel = (TextView) itemView.findViewById(R.id.weekLabel);
            this.monthLabel = (TextView) itemView.findViewById(R.id.monthLabel);
            this.amountLabel = (TextView) itemView.findViewById(R.id.amountLabel);
        }
    }

    /* loaded from: classes3.dex */
    private class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView amountLabel;
        View colorView;
        TextView detailLabel;
        View divider;
        ImageView imageView;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        TextView nameLabel;
        ImageView recurringImage;
        TextView timeLabel;

        TransactionViewHolder(View itemView) {
            super(itemView);
            this.nameLabel = (TextView) itemView.findViewById(R.id.nameLabel);
            this.detailLabel = (TextView) itemView.findViewById(R.id.detailLabel);
            this.colorView = itemView.findViewById(R.id.colorView);
            this.amountLabel = (TextView) itemView.findViewById(R.id.amountLabel);
            this.timeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            this.imageView1 = (ImageView) itemView.findViewById(R.id.image1);
            this.imageView2 = (ImageView) itemView.findViewById(R.id.image2);
            this.imageView3 = (ImageView) itemView.findViewById(R.id.image3);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.divider = itemView.findViewById(R.id.divider);
            this.recurringImage = (ImageView) itemView.findViewById(R.id.repeatImage);
            this.imageView1.setTag(0);
            this.imageView2.setTag(1);
            this.imageView3.setTag(2);
            this.imageView1.setOnClickListener(this);
            this.imageView2.setOnClickListener(this);
            this.imageView3.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TransactionOverviewAdapter.this.listener.OnItemClick(view, getLayoutPosition() - 1);
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            TransactionOverviewAdapter.this.listener.OnItemLongClick(view, getLayoutPosition() - 1);
            return false;
        }
    }
}
