package com.crucifix.software.coffeeshop.adpapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crucifix.software.coffeeshop.R;
import com.crucifix.software.coffeeshop.value.objects.BeverageOptionsVO;
import com.crucifix.software.coffeeshop.value.objects.BeverageVO;
import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeverageSubmitOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String LOG_TAG = "KEKHANE";

    public interface Optionable {
        public void reportTooManySelections(final int selected, final int allowed);

        public void refreshTotalOrderCost(final double optionCost);

    }

    private final List<BeverageVO> mOrderedBeverages = new ArrayList<>();
    private final Map<String, BeverageOptionsVO> mBeverageOptionMap = new TreeMap<>();
    private final Map<String, ArrayAdapter<String>> mBeverageOptionNameMap = new TreeMap<>();
    private final Map<String, Double> mBeverageOptionPrices = new TreeMap<>();
    private final Optionable mOptionable;

    public BeverageSubmitOrderAdapter(final Activity context, final List<BeverageVO> orderedBeverages, final Map<String, BeverageOptionsVO> beverageOptionMap, final Map<String, ArrayAdapter<String>> beverageOptionNameMap, final Map<String, Double> beverageOptionPrices) {
        this.mOptionable = (Optionable) context;

        this.mOrderedBeverages.clear();
        this.mOrderedBeverages.addAll(orderedBeverages);

        this.mBeverageOptionMap.clear();
        this.mBeverageOptionMap.putAll(beverageOptionMap);

        this.mBeverageOptionNameMap.clear();
        this.mBeverageOptionNameMap.putAll(beverageOptionNameMap);

        this.mBeverageOptionPrices.clear();
        this.mBeverageOptionPrices.putAll(beverageOptionPrices);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Log.v(LOG_TAG, "onCreateViewHolder");

        final View beverageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.submit_order_item, parent, false);
        return new BeverageViewHolder(beverageView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Log.v(LOG_TAG, "onBindViewHolder");

        final BeverageVO beverageVO = mOrderedBeverages.get(position);

        final BeverageViewHolder beverageViewHolder = (BeverageViewHolder) holder;
        beverageViewHolder.beverageName.setText(beverageVO.getBeverageName());
        beverageViewHolder.beveragePrice.setText(String.format("%.2f", beverageVO.getBeveragePrice()));

        final String beverageType = beverageVO.getBeverageType();
        final ArrayAdapter<String> adapter = mBeverageOptionNameMap.get(beverageType);
        final OptionMultiSpinnerListener optionMultiSpinnerListener = new OptionMultiSpinnerListener(beverageVO, beverageViewHolder.beverageOptions, mBeverageOptionMap.get(beverageType));

        beverageViewHolder.beverageOptions.setAdapter(adapter, false, optionMultiSpinnerListener);

        if (beverageVO.getSelected() == null) {
            // INTENTIONALLY LEFT BLANK
        } else {
            beverageViewHolder.beverageOptions.setSelected(beverageVO.getSelected());
        }

    }

    /**
     *
     */
    private class OptionMultiSpinnerListener implements MultiSpinner.MultiSpinnerListener {

        private final BeverageVO mBeverageVO;
        private final MultiSpinner mMultiSpinner;
        private final BeverageOptionsVO mBeverageOptionsVO;

        public OptionMultiSpinnerListener(final BeverageVO beverageVO, final MultiSpinner multiSpinner, final BeverageOptionsVO beverageOptionsVO) {
            this.mBeverageVO = beverageVO;
            this.mMultiSpinner = multiSpinner;
            this.mBeverageOptionsVO = beverageOptionsVO;
        }

        /**
         * @param selected
         */
        public void onItemsSelected(boolean[] selected) {

            Log.v(LOG_TAG, "onItemsSelected mPreviousOptionCost = " + mBeverageVO.getPreviousOptionCost());
            int selectedCount = 0;
            int selectedIndex = 0;
            double currentCost = 0;

            mBeverageVO.setCurrentOptionCost(0);

            for (final boolean isSelected : selected) {
                if (isSelected) {
                    currentCost += mBeverageOptionPrices.get(mBeverageOptionsVO.getOptions().get(selectedIndex));
                    selectedCount++;
                }
                selectedIndex++;
            }

            mBeverageVO.setCurrentOptionCost(currentCost);

            mOptionable.refreshTotalOrderCost(mBeverageVO.getPreviousOptionCost());

            if (selectedCount > mBeverageOptionsVO.getMaxSelectableOptions()) {
                final boolean[] resetSelected = new boolean[selected.length];
                mMultiSpinner.setSelected(resetSelected);
                this.mBeverageVO.setSelected(null);
                mOptionable.reportTooManySelections(selectedCount, mBeverageOptionsVO.getMaxSelectableOptions());
            } else {
                mOptionable.refreshTotalOrderCost(mBeverageVO.getCurrentOptionCost());
                mBeverageVO.setPreviousOptionCost(-1 * mBeverageVO.getCurrentOptionCost());
                this.mBeverageVO.setSelected(selected);
            }
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mOrderedBeverages.size();
    }

    /**
     * @return List<BeverageVO>
     */
    public List<BeverageVO> getOrderedBeverages() {
        return mOrderedBeverages;
    }

    /**
     * View Holder for "Real" data, e.g. a Beverage
     */
    public class BeverageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.beverage_name)
        public TextView beverageName;

        @BindView(R.id.beverage_price)
        public TextView beveragePrice;

        @BindView(R.id.beverage_options_spinner)
        public MultiSpinner beverageOptions;

        public BeverageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}