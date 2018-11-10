package com.application.android.imageloader.util;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.application.android.imageloader.R;

public abstract class BaseActivity extends AppCompatActivity implements
        FragmentManager.OnBackStackChangedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    public <T extends Fragment> void showFragment(Class<T> fragmentClass, Bundle bundle,
                                                  boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                fragmentClass.getSimpleName());
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragment.setArguments(bundle);
            } catch (InstantiationException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("New Fragment should have been created", e);
            }
        }

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentPlaceHolder, fragment,
                fragmentClass.getSimpleName());


        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    public <T extends Fragment> void showFragment(Class<T> fragmentClass) {
        showFragment(fragmentClass, null, false);
    }

    public void popFragmentBackStack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void shouldShowActionBarUpButton() {
        if (getSupportActionBar() != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            popFragmentBackStack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackStackChanged() {
        shouldShowActionBarUpButton();
    }

}


