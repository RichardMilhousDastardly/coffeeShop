package com.crucifix.software.coffeeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.new_order)
    ImageButton mNewOrderImageButton;

    @BindView(R.id.new_order_literal)
    TextView mNewOrderLiteral;

    @BindView(R.id.old_order)
    ImageButton mOldOrderImageButton;

    @BindView(R.id.same_again_literal)
    TextView mOldOrderLiteral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        mNewOrderImageButton.setOnClickListener(manufactureNewOrderOnClickListener());
        mNewOrderLiteral.setOnClickListener(manufactureNewOrderOnClickListener());

        mOldOrderImageButton.setOnClickListener(manufactureOldOrderOnClickListener());
        mOldOrderLiteral.setOnClickListener(manufactureOldOrderOnClickListener());

    }

    /**
     * Transfer to the Create new order screen
     *
     * @return View.OnClickListener
     */
    private View.OnClickListener manufactureNewOrderOnClickListener() {

        return new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(final View v) {

                if (mKekhaneApplication.getBeverages().isEmpty()) {
                    displayDialog(getResources().getString(R.string.firebase_data_title), getResources().getString(R.string.firebase_data_message), false);
                } else {
                    final Intent intent = new Intent(MainActivity.this, NewOrder.class);
                    startActivity(intent);
                }
            }
        };

    }

    /**
     * Transfer to the Submit Old order screen
     *
     * @return View.OnClickListener
     */
    private View.OnClickListener manufactureOldOrderOnClickListener() {

        return new View.OnClickListener() {

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(final View v) {

                final Intent intent = new Intent(MainActivity.this, OldOrder.class);
                startActivity(intent);
            }
        };

    }
}
