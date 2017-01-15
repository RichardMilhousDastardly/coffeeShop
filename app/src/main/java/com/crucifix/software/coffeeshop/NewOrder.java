package com.crucifix.software.coffeeshop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.crucifix.software.coffeeshop.adpapters.BeverageOrderAdapter;
import com.crucifix.software.coffeeshop.model.reference.Beverage;
import com.crucifix.software.coffeeshop.value.objects.BeverageVO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewOrder extends BaseActivity {

    private final List<BeverageVO> mAvailableBeverages = new ArrayList<>();

    @BindView(R.id.available_beverages_rv)
    public RecyclerView mRecyclerView;

    @BindView(R.id.done)
    public ImageButton mDoneImageButton;

    private LinearLayoutManager mLinearLayoutManager;
    private BeverageOrderAdapter mBeverageOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_order);
        ButterKnife.bind(this);

        populateRecyclerView();

        mDoneImageButton.setOnClickListener(manufactureDoneOnclickListener());

    }

    /**
     * The user has completed their basic order
     *
     * @return
     */
    private View.OnClickListener manufactureDoneOnclickListener() {

        return new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(final View v) {

                final List<BeverageVO> availableBeverages = mBeverageOrderAdapter.getAvailableBeverages();

                final int totalOrderQuantity = calculateTotalOrderQuantity(availableBeverages);

                if (totalOrderQuantity == 0) {
                    displayDialog(getResources().getString(R.string.empty_order_title), getResources().getString(R.string.empty_order_message), false);
                } else {
                    final Intent intent = new Intent(NewOrder.this, SubmitOrder.class);
                    populateIntentExtras(intent, totalOrderQuantity, availableBeverages);
                    startActivity(intent);
                }
            }
        };

    }

    /**
     * Pass the correct number of beverage keys
     *
     * @param intent
     * @param totalOrderQuantity
     * @param availableBeverages
     */
    private void populateIntentExtras(final Intent intent, final int totalOrderQuantity, final List<BeverageVO> availableBeverages) {

        final ArrayList<String> beverageKeys = new ArrayList<>();

        for (final BeverageVO beverage : availableBeverages) {
            final int beverageQuantity = beverage.getQuantity();

            if (beverageQuantity > 0) {
                storeBeverageKey(beverageKeys, beverageQuantity, beverage.getId());
            }
        }

        intent.putStringArrayListExtra(EXTRA_ORDERED_BEVERAGE_KEYS, beverageKeys);

        for (final String key : beverageKeys) {
            Log.v(LOG_TAG, "Extra Keys " + key);
        }

    }

    /**
     * Store the correct number of Beverage keys
     *
     * @param beverageKeys
     * @param beverageQuantity
     * @param id
     */
    private void storeBeverageKey(final List<String> beverageKeys, final int beverageQuantity, final String id) {
        for (int keyIndex = 0; keyIndex < beverageQuantity; keyIndex++) {
            beverageKeys.add(id);
        }
    }

    /**
     * How many beverages have been ordered
     *
     * @param availableBeverages
     * @return int
     */
    private int calculateTotalOrderQuantity(final List<BeverageVO> availableBeverages) {

        int totalOrderQuantity = 0;

        for (final BeverageVO beverage : mBeverageOrderAdapter.getAvailableBeverages()) {
            totalOrderQuantity += beverage.getQuantity();
        }

        return totalOrderQuantity;

    }

    /**
     * Display all the available beverages
     */
    private void populateRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.BLACK).build());

        mAvailableBeverages.clear();

        for (final Beverage beverage : mKekhaneApplication.getBeverages()) {

            final BeverageVO beverageVO = new BeverageVO.BeverageVOBuilder()
                    .id(beverage.getId())
                    .beverageName(beverage.getName())
                    .beveragePrice(beverage.getPrice())
                    .beverageType(beverage.getType()).build();
            mAvailableBeverages.add(beverageVO);
        }

        mBeverageOrderAdapter = new BeverageOrderAdapter(mAvailableBeverages);
        mRecyclerView.setAdapter(mBeverageOrderAdapter);

    }
}
