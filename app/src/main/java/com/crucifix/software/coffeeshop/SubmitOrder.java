package com.crucifix.software.coffeeshop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crucifix.software.coffeeshop.adpapters.BeverageSubmitOrderAdapter;
import com.crucifix.software.coffeeshop.model.reference.Beverage;
import com.crucifix.software.coffeeshop.model.reference.BeverageOption;
import com.crucifix.software.coffeeshop.model.reference.BeverageTopping;
import com.crucifix.software.coffeeshop.value.objects.BeverageOptionsVO;
import com.crucifix.software.coffeeshop.value.objects.BeverageToppingRO;
import com.crucifix.software.coffeeshop.value.objects.BeverageVO;
import com.crucifix.software.coffeeshop.value.objects.CoffeeBooleanRO;
import com.crucifix.software.coffeeshop.value.objects.OrderedBeverageRO;
import com.crucifix.software.coffeeshop.value.objects.SubmittedOrderRO;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

public class SubmitOrder extends BaseActivity implements BeverageSubmitOrderAdapter.Optionable {

    private final List<BeverageVO> mOrderedBeverages = new ArrayList<>();
    private final Map<String, BeverageOptionsVO> mBeverageOptionMap = new TreeMap<>();
    private final Map<String, ArrayAdapter<String>> mBeverageOptionNameMap = new TreeMap<>();
    private final Map<String, Double> mBeverageOptionPrices = new TreeMap<>();

    @BindView(R.id.submit_beverages_order_rv)
    public RecyclerView mRecyclerView;

    @BindView(R.id.submit)
    public ImageButton mSubmitImageButton;

    @BindView(R.id.save)
    public ImageButton mSaveImageButton;

    @BindView(R.id.order_cost)
    public TextView mTotalCostTextView;

    private LinearLayoutManager mLinearLayoutManager;
    private BeverageSubmitOrderAdapter mBeverageSubmitOrderAdapter;

    private double mTotalCost = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_order);
        ButterKnife.bind(this);

        manageOrderedBeverages();

        mTotalCostTextView.setText(String.format("%.2f", mTotalCost));

        mSubmitImageButton.setOnClickListener(manufactureSubmitOnclickListener());

        mSaveImageButton.setOnClickListener(manufactureSaveOnclickListener());

        populateRecyclerView();

    }

    /**
     * Save the current order to a database
     *
     * Only allow the user to click this button once
     *
     * @return View.OnClickListener
     */
    private View.OnClickListener manufactureSaveOnclickListener() {

        return new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(final View v) {
                mSaveImageButton.setEnabled(false);
                persistOrder();
                displayDialog(getResources().getString(R.string.order_saved_title), getResources().getString(R.string.order_saved_message), false);
            }
        };
    }

    /**
     * Archive this order
     */
    private void persistOrder() {
        final List<BeverageOption> beverageOptions = mKekhaneApplication.getBeverageOptions();

        final SubmittedOrderRO submittedOrderRO = new SubmittedOrderRO();
        submittedOrderRO.setCreatedTimestamp(new Date());

        final RealmList<OrderedBeverageRO> orderedBeverageROList = new RealmList<>();

        final List<BeverageVO> orderedBeverages = mBeverageSubmitOrderAdapter.getOrderedBeverages();

        for (final BeverageVO beverageVO : orderedBeverages) {
            final OrderedBeverageRO orderedBeverageRO = new OrderedBeverageRO();
            orderedBeverageRO.setBeverageId(beverageVO.getId());
            orderedBeverageRO.setBeverageName(beverageVO.getBeverageName());
            orderedBeverageRO.setBeverageType(beverageVO.getBeverageType());

            orderedBeverageRO.setSelected(wrapSelected(beverageVO.getSelected()));
            final RealmList<BeverageToppingRO> beverageToppingROs = new RealmList<>();
            if (beverageVO.getSelected() == null) {
                // INTENTIONALLY LEFT BLANK
            } else {
                for (final BeverageOption beverageOption : beverageOptions) {

                    if (beverageOption.getBeverageType().equals(beverageVO.getBeverageType())) {
                        final List<String> optionNames = beverageOption.getOptions();
                        int selectedIndex = 0;

                        for (final String optionName : optionNames) {
                            if (beverageVO.getSelected()[selectedIndex]) {
                                beverageToppingROs.add(new BeverageToppingRO(optionName));
                                Log.v(LOG_TAG, "persistOrder(beverageToppingRO) >" + optionName);
                            }
                            selectedIndex++;
                        }
                        break;
                    }
                }
            }

            orderedBeverageRO.setBeverageToppings(beverageToppingROs);
            orderedBeverageROList.add(orderedBeverageRO);
        }

        submittedOrderRO.setOrderedBeverageList(orderedBeverageROList);

        final Realm realm = Realm.getDefaultInstance();
        // Persist your data in a transaction
        realm.beginTransaction();
        final SubmittedOrderRO managedSubmittedOrder = realm.copyToRealm(submittedOrderRO); // Persist unmanaged objects
        realm.commitTransaction();

        Log.v(LOG_TAG, "Persisted " + managedSubmittedOrder);
    }

    /**
     * Realm cannot store a boolean array
     *
     * @param selected
     * @return RealmList<CoffeeBooleanRO>
     */
    private RealmList<CoffeeBooleanRO> wrapSelected(final boolean[] selected) {

        final RealmList<CoffeeBooleanRO> selectedRO = new RealmList<>();

        if (selected == null) {
            return selectedRO;
        }

        for (final boolean isSelected : selected) {
            selectedRO.add(new CoffeeBooleanRO(isSelected));
        }

        return selectedRO;
    }

    /**
     * Which beverages have been ordered and what is the cost of the total order
     */
    private void manageOrderedBeverages() {
        identifyOrderedBeverages();
        calculateTotalOrderCost();
    }

    /**
     * Which beverages have been ordered
     */
    private void identifyOrderedBeverages() {

        final ArrayList<String> beverageKeys = getIntent().getStringArrayListExtra(EXTRA_ORDERED_BEVERAGE_KEYS);

        for (final String beverageKey : beverageKeys) {
            final Beverage beverage = retrieveBeverageDetails(beverageKey);
            final BeverageVO beverageVO = new BeverageVO.BeverageVOBuilder()
                    .id(beverage.getId())
                    .beverageName(beverage.getName())
                    .beverageType(beverage.getType())
                    .beveragePrice(beverage.getPrice()).build();
            mOrderedBeverages.add(beverageVO);
        }
    }

    /**
     * Whats the initial order cost
     */
    private void calculateTotalOrderCost() {

        for (final BeverageVO beverageVO : mOrderedBeverages) {
            mTotalCost += beverageVO.getBeveragePrice();
        }

    }

    /**
     * Employ the pass beverage key to identify the required beverage details
     *
     * @param beverageKey
     * @return Beverage
     */
    private Beverage retrieveBeverageDetails(final String beverageKey) {

        for (final Beverage beverage : mKekhaneApplication.getBeverages()) {
            if (beverage.getId().equals(beverageKey)) {
                return beverage;
            }
        }

        return null;

    }

    /**
     * The user has completed their customised order
     *
     * @return View.OnClickListener
     */
    private View.OnClickListener manufactureSubmitOnclickListener() {

        return new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(final View v) {
                displayDialog(getResources().getString(R.string.order_submit_title), getResources().getString(R.string.order_submit_message), true);
            }
        };

    }

    /**
     * Display all the available beverages
     */
    private void populateRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(Color.BLACK).build());

        constructBeverageOptionNameArrayAdapters(mKekhaneApplication.getBeverageOptions());
        populatBevereageOptionsPrices(mKekhaneApplication.getBeverageToppings());

        mBeverageSubmitOrderAdapter = new BeverageSubmitOrderAdapter(this, mOrderedBeverages, mBeverageOptionMap, mBeverageOptionNameMap, mBeverageOptionPrices);
        mRecyclerView.setAdapter(mBeverageSubmitOrderAdapter);

    }

    /**
     * Populate a map to hold all beverage option prices, the key to the map is the Option name.
     *
     * @param beverageToppings
     */
    private void populatBevereageOptionsPrices(final List<BeverageTopping> beverageToppings) {
        mBeverageOptionPrices.clear();

        for (final BeverageTopping beverageTopping : beverageToppings) {
            final String optionName = beverageTopping.getName();
            final double optionPrice = beverageTopping.getPrice();
            mBeverageOptionPrices.put(optionName, optionPrice);
        }
    }

    /**
     * @param beverageOptions
     */
    private void constructBeverageOptionNameArrayAdapters(final List<BeverageOption> beverageOptions) {

        for (final BeverageOption beverageOption : beverageOptions) {

            final String beverageType = beverageOption.getBeverageType();
            final List<String> beverageOptionNames = beverageOption.getOptions();

            final BeverageOptionsVO beverageOptionsVO = new BeverageOptionsVO.BeverageOptionsVOBuilder()
                    .id(beverageOption.getId())
                    .beverageType(beverageType)
                    .maxSelectableOptions(beverageOption.getMaxOptions())
                    .options(beverageOptionNames)
                    .build();

            mBeverageOptionMap.put(beverageType, beverageOptionsVO);
            mBeverageOptionNameMap.put(beverageType, constructArrayAdapters(beverageOptionNames));

        }
    }

    /**
     * @param beverageOptionNames
     * @return
     */
    private ArrayAdapter<String> constructArrayAdapters(final List<String> beverageOptionNames) {

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.toppings_spinner_item);

        for (final String optionName : beverageOptionNames) {
            adapter.add(optionName);
        }

        return adapter;


    }

    /**
     * Too many beverage options have ben selected, so display a dialog message
     */
    @Override
    public void reportTooManySelections(final int selected, final int allowed) {

        final String too_many_options_message = getResources().getString(R.string.too_many_beverage_options_message_start) + selected +
                getResources().getQuantityString(R.plurals.too_many_beverage_options, selected) +
                getResources().getString(R.string.too_many_beverage_options_message_middle) + allowed +
                getResources().getString(R.string.too_many_beverage_options_message_end);

        displayDialog(getResources().getString(R.string.too_many_beverage_options_title), too_many_options_message, false);
    }

    /**
     *
     */
    @Override
    public void refreshTotalOrderCost(final double optionCost) {
        mTotalCost += optionCost;
        mTotalCostTextView.setText(String.format("%.2f", mTotalCost));
    }
}
