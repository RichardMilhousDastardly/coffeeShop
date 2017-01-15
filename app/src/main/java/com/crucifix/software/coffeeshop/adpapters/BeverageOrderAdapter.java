package com.crucifix.software.coffeeshop.adpapters;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.crucifix.software.coffeeshop.R;
import com.crucifix.software.coffeeshop.value.objects.BeverageVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BeverageOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<BeverageVO> mAvailableBeverages = new ArrayList<>();

    public BeverageOrderAdapter(final List<BeverageVO> availableBeverages) {
        this.mAvailableBeverages.clear();
        this.mAvailableBeverages.addAll(availableBeverages);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View beverageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_order_item, parent, false);
        return new BeverageViewHolder(beverageView, new BeverageQuantityTextListener());
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final BeverageVO beverageVO = mAvailableBeverages.get(position);

        final BeverageViewHolder beverageViewHolder = (BeverageViewHolder) holder;
        beverageViewHolder.beverageQuantityTextListener.setPosition(position);
        beverageViewHolder.beverageName.setText(beverageVO.getBeverageName());
        beverageViewHolder.beverageName.setOnClickListener(manufactureBeverageNameOnClickListener(beverageVO, beverageViewHolder.beverageQuantity));
    }

    /**
     * The user has incremented the beverage quantity
     *
     * @param beverageVO
     * @param beverageQuantity
     * @return View.OnClickListener
     */
    private View.OnClickListener manufactureBeverageNameOnClickListener(final BeverageVO beverageVO, final EditText beverageQuantity) {

        return new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(final View v) {
                final int quantity = beverageVO.getQuantity() + 1;
                beverageVO.setQuantity(quantity);
                beverageQuantity.setText(Integer.toString(quantity), TextView.BufferType.EDITABLE);
            }
        };
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mAvailableBeverages.size();
    }

    /**
     *
     * @return List<BeverageVO>
     */
    public List<BeverageVO> getAvailableBeverages() {
        return mAvailableBeverages;
    }

    /**
     * View Holder for "Real" data, e.g. an Article
     */
    public class BeverageViewHolder extends RecyclerView.ViewHolder {

        public final BeverageQuantityTextListener beverageQuantityTextListener;

        @BindView(R.id.beverage_name)
        public TextView beverageName;

        @BindView(R.id.beverage_quantity)
        public EditText beverageQuantity;

        public BeverageViewHolder(View view, final BeverageQuantityTextListener beverageQuantityTextListener) {
            super(view);
            ButterKnife.bind(this, view);
            this.beverageQuantityTextListener = beverageQuantityTextListener;
            beverageQuantity.addTextChangedListener(beverageQuantityTextListener);
        }
    }

    /**
     *
     */
    private class BeverageQuantityTextListener implements TextWatcher {
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        /**
         * This method is called to notify you that, within <code>s</code>,
         * the <code>count</code> characters beginning at <code>start</code>
         * are about to be replaced by new text with length <code>after</code>.
         * It is an error to attempt to make changes to <code>s</code> from
         * this callback.
         *
         * @param s
         * @param start
         * @param count
         * @param after
         */
        @Override
        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
            // INTENTIONALLY LEFT BLANK
        }

        /**
         * This method is called to notify you that, within <code>s</code>,
         * the <code>count</code> characters beginning at <code>start</code>
         * have just replaced old text that had length <code>before</code>.
         * It is an error to attempt to make changes to <code>s</code> from
         * this callback.
         *
         * @param charSequence
         * @param start
         * @param before
         * @param count
         */
        @Override
        public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {

            final BeverageVO beverageVO = mAvailableBeverages.get(position);
            final String quantityString = charSequence.toString().trim();

            if (TextUtils.isEmpty(quantityString)) {
                beverageVO.setQuantity(0);
            } else {
                final int quantityInt = Integer.parseInt(quantityString);
                beverageVO.setQuantity(quantityInt);
            }
        }

        /**
         * This method is called to notify you that, somewhere within
         * <code>s</code>, the text has been changed.
         * It is legitimate to make further changes to <code>s</code> from
         * this callback, but be careful not to get yourself into an infinite
         * loop, because any changes you make will cause this method to be
         * called again recursively.
         * (You are not told where the change took place because other
         * afterTextChanged() methods may already have made other changes
         * and invalidated the offsets.  But if you need to know here,
         * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
         * to mark your place and then look up from here where the span
         * ended up.
         *
         * @param s
         */
        @Override
        public void afterTextChanged(final Editable s) {
            // INTENTIONALLY LEFT BLANK
        }
    }

}
