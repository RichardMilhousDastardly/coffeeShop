package com.crucifix.software.coffeeshop;

import android.app.Application;
import android.util.Log;

import com.crucifix.software.coffeeshop.model.reference.Beverage;
import com.crucifix.software.coffeeshop.model.reference.BeverageOption;
import com.crucifix.software.coffeeshop.model.reference.BeverageTopping;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class KekhaneApplication extends Application {

    private final List<BeverageOption> mBeverageOptions = new ArrayList<>();
    private final List<Beverage> mBeverages = new ArrayList<>();
    private final List<BeverageTopping> mBeverageToppings = new ArrayList<>();

    private final static String LOG_TAG = "KEKHANE";
    private FirebaseDatabase mFirebaseDatabase = null;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Realm-io
        Realm.init(getApplicationContext());
        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);


        // Initializes the default FirebaseApp instance using string resource values
        // populated from google-services.json
        FirebaseApp.initializeApp(getApplicationContext());
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        loadToppings();
        loadBeverages();
        loadBeverageOptions();
    }

    /**
     * Allow access to the Firebase database associated with this coffee shop app
     *
     * @return FirebaseDatabase
     */
    public FirebaseDatabase getFirebaseDatabase() {
        return mFirebaseDatabase;
    }


    /**
     * Retrieve the beverage options reference data from Firebase.
     */
    public void loadBeverageOptions() {

        final DatabaseReference beverageOptionsDatabaseReference = mFirebaseDatabase.getReference("BeverageOptions");

        beverageOptionsDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mBeverageOptions.clear();

                for (final DataSnapshot beverageOptionDS : dataSnapshot.getChildren()) {
                    final BeverageOption beverageOption = beverageOptionDS.getValue(BeverageOption.class);
                    mBeverageOptions.add(beverageOption);

                    Log.v(LOG_TAG, "DataSnapshot options = " + beverageOption);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.e(LOG_TAG, "BeverageOptions onCancelled" + databaseError);
            }
        });

    }

    /**
     * Retrieve the beverages reference data from Firebase.
     */
    public void loadBeverages() {

        //
        final DatabaseReference mainBeveragesDatabaseReference = mFirebaseDatabase.getReference("MainBeverages");

        mainBeveragesDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                mBeverages.clear();

                for (final DataSnapshot beverageDS : dataSnapshot.getChildren()) {
                    final Beverage beverage = beverageDS.getValue(Beverage.class);
                    mBeverages.add(beverage);

                    Log.v(LOG_TAG, "DataSnapshot Beverage = " + beverage);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.e(LOG_TAG, "MainBeverages onCancelled" + databaseError);
            }
        });


    }

    /**
     * Retrieve the beverage toppings reference data from Firebase.
     */
    public void loadToppings() {

        final DatabaseReference toppingsDatabaseReference = mFirebaseDatabase.getReference("Toppings");

        toppingsDatabaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mBeverageToppings.clear();

                for (final DataSnapshot beverageToppingDS : dataSnapshot.getChildren()) {
                    final BeverageTopping beverageTopping = beverageToppingDS.getValue(BeverageTopping.class);
                    mBeverageToppings.add(beverageTopping);

                    Log.v(LOG_TAG, "DataSnapshot toppings = " + beverageTopping);

                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.e(LOG_TAG, "Toppings onCancelled" + databaseError);
            }
        });

    }

    public List<BeverageOption> getBeverageOptions() {
        return mBeverageOptions;
    }

    public List<Beverage> getBeverages() {
        return mBeverages;
    }

    public List<BeverageTopping> getBeverageToppings() {
        return mBeverageToppings;
    }

}
