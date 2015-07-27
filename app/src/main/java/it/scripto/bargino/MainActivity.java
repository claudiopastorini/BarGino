package it.scripto.bargino;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import it.scripto.models.Coffee;
import it.scripto.util.BaseActivity;


public class MainActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final boolean TOOLBAR_IS_STICKY = false;

    public static final String EXTRA_COFFEE = "Coffee";

    private Toolbar toolbar;
    private View imageView;
    private View overlayView;
    private ObservableScrollView scrollView;
    private TextView titleView;
    private View fab;
    private View milkView;
    private View liquorView;
    private RadioGroup milkRadioGroup;
    private RadioGroup liquorRadioGroup;
    private RadioButton cupChoiceRadioButton;
    private RadioButton glassChoiceRadioButton;
    private RadioButton caneChoiceRadioButton;
    private RadioButton bitterChoiceRadioButton;
    private RadioButton sweetChoiceRadioButton;
    private RadioButton sweetenerChoiceRadioButton;
    private RadioButton milkChoiceRadioButton;
    private RadioButton liquorChoiceRadioButton;
    private RadioButton nothingChoiceRadioButton;
    private RadioButton coldMilkChoiceRadioButton;
    private RadioButton hotMilkChoiceRadioButton;
    private RadioButton grappaChoiceRadioButton;
    private RadioButton sambucaChoiceRadioButton;
    private RadioButton cognacChoiceRadioButton;
    private RadioButton shortChoiceRadioButton;
    private RadioButton normalChoiceRadioButton;
    private RadioButton longChoiceRadioButton;
    private RadioButton doubleChoiceRadioButton;

    private int actionBarSize;
    private int flexibleSpaceShowFabOffset;
    private int flexibleSpaceImageHeight;
    private int fabMargin;
    private int toolbarColor;
    private boolean fabIsShown;

    private Coffee coffee;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Toolbar and set as ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get and set a lot of things for cool action bar
        flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        flexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        actionBarSize = getActionBarSize();
        toolbarColor = getResources().getColor(R.color.primary);

        toolbar.setBackgroundColor(Color.TRANSPARENT);

        imageView = findViewById(R.id.image);
        overlayView = findViewById(R.id.overlay);

        scrollView = (ObservableScrollView) findViewById(R.id.scroll);
        scrollView.setScrollViewCallbacks(this);

        titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getTitle());
        setTitle(null);

        ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
            @Override
            public void run() {
                //scrollView.scrollTo(0, flexibleSpaceImageHeight - actionBarSize);

                // If you'd like to start from scrollY == 0, don't write like this:
                //scrollView.scrollTo(0, 0);
                // The initial scrollY is 0, so it won't invoke onScrollChanged().
                // To do this, use the following:
                //onScrollChanged(0, false, false);

                // You can also achieve it with the following codes.
                // This causes scroll change from 1 to 0.
                scrollView.scrollTo(0, 1);
                scrollView.scrollTo(0, 0);
            }
        });

        // Get FAB and set onClickListener
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "FAB is clicked");
                Intent intent = new Intent(getBaseContext(), SummaryActivity.class);
                intent.putExtra(EXTRA_COFFEE, coffee);
                startActivity(intent);
            }
        });

        fabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        ViewHelper.setScaleX(fab, 0);
        ViewHelper.setScaleY(fab, 0);

        // Get others views
        milkView = findViewById(R.id.include_milk);
        liquorView = findViewById(R.id.include_liquor);

        milkRadioGroup = (RadioGroup) findViewById(R.id.milk_radio_group);
        liquorRadioGroup = (RadioGroup) findViewById(R.id.liquor_radio_group);

        cupChoiceRadioButton = (RadioButton) findViewById(R.id.cup_choice);
        glassChoiceRadioButton = (RadioButton) findViewById(R.id.glass_choice);

        caneChoiceRadioButton = (RadioButton) findViewById(R.id.cane_choice);
        bitterChoiceRadioButton = (RadioButton) findViewById(R.id.bitter_choice);
        sweetChoiceRadioButton = (RadioButton) findViewById(R.id.sweet_choice);
        sweetenerChoiceRadioButton = (RadioButton) findViewById(R.id.sweetener_choice);

        milkChoiceRadioButton = (RadioButton) findViewById(R.id.milk_choice);
        liquorChoiceRadioButton = (RadioButton) findViewById(R.id.liquor_choice);
        nothingChoiceRadioButton = (RadioButton) findViewById(R.id.nothing_choice);

        coldMilkChoiceRadioButton = (RadioButton) findViewById(R.id.cold_milk_choice);
        hotMilkChoiceRadioButton = (RadioButton) findViewById(R.id.hot_milk_choice);

        grappaChoiceRadioButton = (RadioButton) findViewById(R.id.grappa_choice);
        sambucaChoiceRadioButton = (RadioButton) findViewById(R.id.sambuca_choice);
        cognacChoiceRadioButton = (RadioButton) findViewById(R.id.cognac_choice);

        shortChoiceRadioButton = (RadioButton) findViewById(R.id.short_choice);
        normalChoiceRadioButton = (RadioButton) findViewById(R.id.normal_choice);
        longChoiceRadioButton = (RadioButton) findViewById(R.id.long_choice);
        doubleChoiceRadioButton = (RadioButton) findViewById(R.id.double_choice);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore coffee from saved instance
            coffee = savedInstanceState.getParcelable(EXTRA_COFFEE);

            // Show liquor or milk view if set
            if (coffee.hasLiquor()) {
                liquorView.setVisibility(View.VISIBLE);
            } else if (coffee.hasMilk()) {
                milkView.setVisibility(View.VISIBLE);
            }

            Log.i(TAG, "RESTORED: " + coffee.toString());
        } else {
            // Create new coffee
            coffee = new Coffee();
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Translate overlay and image
        float flexibleRange = flexibleSpaceImageHeight - actionBarSize;
        int minOverlayTransitionY = actionBarSize - overlayView.getHeight();
        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(titleView, 0);
        ViewHelper.setPivotY(titleView, 0);
        ViewHelper.setScaleX(titleView, scale);
        ViewHelper.setScaleY(titleView, scale);

        // Translate title text
        int maxTitleTranslationY = (int) (flexibleSpaceImageHeight - titleView.getHeight() * scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        if (TOOLBAR_IS_STICKY) {
            titleTranslationY = Math.max(0, titleTranslationY);
        }
        ViewHelper.setTranslationY(titleView, titleTranslationY);

        // Translate FAB
        int maxFabTranslationY = flexibleSpaceImageHeight - fab.getHeight() / 2;
        float fabTranslationY = ScrollUtils.getFloat(
                -scrollY + flexibleSpaceImageHeight - fab.getHeight() / 2,
                actionBarSize - fab.getHeight() / 2,
                maxFabTranslationY);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) fab.getLayoutParams();
            lp.leftMargin = overlayView.getWidth() - fabMargin - fab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            fab.requestLayout();
        } else {
            ViewHelper.setTranslationX(fab, overlayView.getWidth() - fabMargin / 2 - fab.getWidth());
        }

        // Show/hide FAB
        if (fabTranslationY < flexibleSpaceShowFabOffset) {
            //hideFab();
            ViewHelper.setTranslationY(fab, scrollView.getHeight() - fabMargin / 2 - fab.getHeight());
        } else {
            showFab();
            ViewHelper.setTranslationY(fab, fabTranslationY);
        }

        if (TOOLBAR_IS_STICKY) {
            // Change alpha of toolbar background
            if (-scrollY + flexibleSpaceImageHeight <= actionBarSize) {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(1, toolbarColor));
            } else {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, toolbarColor));
            }
        } else {
            // Translate Toolbar
            if (scrollY < flexibleSpaceImageHeight) {
                ViewHelper.setTranslationY(toolbar, 0);
            } else {
                ViewHelper.setTranslationY(toolbar, -scrollY);
            }
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void showFab() {
        if (!fabIsShown) {
            ViewPropertyAnimator.animate(fab).cancel();
            ViewPropertyAnimator.animate(fab).scaleX(1).scaleY(1).setDuration(200).start();
            fabIsShown = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO: reshow menu
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current coffee
        savedInstanceState.putParcelable(EXTRA_COFFEE, coffee);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        if (BuildConfig.DEBUG) Log.v(TAG, "onSaveInstanceState");
    }

    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore coffee from saved instance
        coffee = savedInstanceState.getParcelable(EXTRA_COFFEE);
        Log.i(TAG, "RESTORED: " + coffee.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Set all checkbox with right value

        // Set type
        switch (coffee.getType()) {
            case 0:
                glassChoiceRadioButton.setChecked(true);
                break;
            case 1:
                cupChoiceRadioButton.setChecked(true);
                break;
            default:
                Log.e(TAG, "There is not a choice..");
        }

        // Set sugar
        switch (coffee.getSugar()) {
            case 0:
                caneChoiceRadioButton.setChecked(true);
                break;
            case 1:
                bitterChoiceRadioButton.setChecked(true);
                break;
            case 2:
                sweetChoiceRadioButton.setChecked(true);
                break;
            case 3:
                sweetenerChoiceRadioButton.setChecked(true);
                break;
            default:
                Log.e(TAG, "There is not a choice..");
        }

        // Set something else
        if (coffee.hasMilk()) {
            milkChoiceRadioButton.setChecked(true);
        } else if (coffee.hasLiquor()) {
            liquorChoiceRadioButton.setChecked(true);
        } else {
            nothingChoiceRadioButton.setChecked(true);
        }

        // Set milk
        switch (coffee.getMilk()) {
            case 0:
                hotMilkChoiceRadioButton.setChecked(true);
                break;
            case 1:
                coldMilkChoiceRadioButton.setChecked(true);
                break;
            default:
                Log.e(TAG, "There is not a choice..");
        }

        // Set liquor
        String liquor = coffee.getLiquor();
        if (liquor != null) {
            switch (liquor) {
                case Coffee.GRAPPA:
                    grappaChoiceRadioButton.setChecked(true);
                    break;
                case Coffee.SAMBUCA:
                    sambucaChoiceRadioButton.setChecked(true);
                    break;
                case Coffee.COGNAC:
                    cognacChoiceRadioButton.setChecked(true);
                    break;
                default:
                    Log.e(TAG, "There is not a choice..");
            }
        }

        // Set quantity
        switch (coffee.getQuantity()) {
            case 0:
                shortChoiceRadioButton.setChecked(true);
                break;
            case 1:
                normalChoiceRadioButton.setChecked(true);
                break;
            case 2:
                longChoiceRadioButton.setChecked(true);
                break;
            case 3:
                doubleChoiceRadioButton.setChecked(true);
                break;
            default:
                Log.e(TAG, "There is not a choice..");
        }
    }

    public void onRadioButtonClicked(View view) throws Exception {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cup_choice:
                if (checked) {
                    coffee.setType(Coffee.CUP);
                }
                break;
            case R.id.glass_choice:
                if (checked) {
                    coffee.setType(Coffee.GLASS);
                }
                break;
            case R.id.cane_choice:
                if (checked) {
                    coffee.setSugar(Coffee.CANE);
                }
                break;
            case R.id.bitter_choice:
                if (checked) {
                    coffee.setSugar(Coffee.BITTER);
                }
                break;
            case R.id.sweet_choice:
                if (checked) {
                    coffee.setSugar(Coffee.SWEET);
                }
                break;
            case R.id.sweetener_choice:
                if (checked) {
                    coffee.setSugar(Coffee.SWEETENER);
                }
                break;
            case R.id.milk_choice:
                if (checked) {
                    // Show milk and remove liquor view
                    milkView.setVisibility(View.VISIBLE);
                    liquorView.setVisibility(View.GONE);
                    // Remove liquor if it is present
                    if (coffee.hasLiquor()) {
                        coffee.removeLiquor();
                        liquorRadioGroup.clearCheck();
                    }
                    // Set default milk
                    coffee.addMilk(Coffee.COLD_MILK);
                    coldMilkChoiceRadioButton.setChecked(true);
                }
                break;
            case R.id.liquor_choice:
                if (checked) {
                    // Show liquor and remove milk view
                    liquorView.setVisibility(View.VISIBLE);
                    milkView.setVisibility(View.GONE);
                    // Remove milk if it is present
                    if (coffee.hasMilk()) {
                        coffee.removeMilk();
                        milkRadioGroup.clearCheck();
                    }
                    // Set default liquor
                    coffee.addLiquor(Coffee.GRAPPA);
                    grappaChoiceRadioButton.setChecked(true);
                }
                break;
            case R.id.nothing_choice:
                if (checked) {
                    // Remove milk and liquor view
                    liquorView.setVisibility(View.GONE);
                    milkView.setVisibility(View.GONE);
                    // Clear radio groups
                    milkRadioGroup.clearCheck();
                    liquorRadioGroup.clearCheck();
                    // Remove milk or liquor from the coffee
                    if (coffee.hasLiquor()) {
                        coffee.removeLiquor();
                    } else if (coffee.hasMilk()) {
                        coffee.removeMilk();
                    }
                }
                break;
            case R.id.cold_milk_choice:

                if (checked) {
                    // Remove milk if it is present
                    if (coffee.hasMilk()) {
                        coffee.removeMilk();
                    }
                    coffee.addMilk(Coffee.COLD_MILK);
                }
                break;
            case R.id.hot_milk_choice:
                if (checked) {
                    // Remove milk if it is present
                    if (coffee.hasMilk()) {
                        coffee.removeMilk();
                    }
                    coffee.addMilk(Coffee.HOT_MILK);
                }
                break;
            case R.id.grappa_choice:
                if (checked) {
                    // Remove liquor if it is present
                    if (coffee.hasLiquor()) {
                        coffee.removeLiquor();
                    }
                    coffee.addLiquor(Coffee.GRAPPA);
                }
                break;
            case R.id.sambuca_choice:
                if (checked) {
                    // Remove liquor if it is present
                    if (coffee.hasLiquor()) {
                        coffee.removeLiquor();
                    }
                    coffee.addLiquor(Coffee.SAMBUCA);
                }
                break;
            case R.id.cognac_choice:
                if (checked) {
                    // Remove liquor if it is present
                    if (coffee.hasLiquor()) {
                        coffee.removeLiquor();
                    }
                    coffee.addLiquor(Coffee.COGNAC);
                }
                break;
            case R.id.short_choice:
                if (checked) {
                    coffee.setQuantity(Coffee.SHORT);
                }
                break;
            case R.id.normal_choice:
                if (checked) {
                    coffee.setQuantity(Coffee.NORMAL);
                }
                break;
            case R.id.long_choice:
                if (checked) {
                    coffee.setQuantity(Coffee.LONG);
                }
                break;
            case R.id.double_choice:
                if (checked) {
                    coffee.setQuantity(Coffee.DOUBLE);
                }
                break;
        }
    }

    private int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }
}
