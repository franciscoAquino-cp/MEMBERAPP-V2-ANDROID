package modules.base.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.medicard.member.R;
import com.medicard.member.core.utilities.TransitionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * <p>
 * App Activity Configuration to all app may be initialize here
 * </p>
 *
 * @author John Paul Cas
 * @since 4/11/2017
 */
public abstract class BaseActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnBack)
    FancyButton btnBack;

    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    private Unbinder unbinder;

    protected Context context;

    // disabled the key enter listener
    protected View.OnKeyListener onKeyDisableEnterListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyView();
    }

    /**
     * <p>
     * Initialize all Object/component(s) here, <br />
     * All view is must initialize using {@link BindView}
     * </p>
     */
    protected void initViews() {
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);

        context = this;
    }

    /**
     * <p>
     * Detach activity View or Components here
     * </p>
     */
    protected void destroyView() {
        unbinder.unbind();
    }

    /**
     * <p>
     * Set customable toolbar title
     * </p>
     *
     * @param title The Toolbar Title
     */
    protected void setToolbarCustomableTitle(String title) {
        tvToolbarTitle.setText(title);
    }

    /**
     * Handle Back navigation event
     */

    @OnClick(R.id.btnBack)
    public void backNavigation() {
        /*Visibility returnTransition = buildReturnTransition();
        getWindow().setReturnTransition(returnTransition);*/
        finishAfterTransition();
    }

    protected FancyButton getBackView() {
        return btnBack;
    }

    @SuppressWarnings("unchecked")
    protected void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }

    protected void transitionToResult(Intent i, int code) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivityForResult(i, code, transitionActivityOptions.toBundle());
    }

    protected void setupWindowAnimations() {
        Transition transition = buildEnterTransition();
        getWindow().setEnterTransition(transition);
    }

    protected Visibility buildEnterTransition() {
        Slide enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        enterTransition.setSlideEdge(Gravity.RIGHT);
        return enterTransition;
    }

    private Visibility buildReturnTransition() {
        Visibility enterTransition = new Slide();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        return enterTransition;
    }

    /**
     * <p>
     * Set the layout resource ID if extend the base activity,
     * This will be the equivalent to {@link android.app.Activity#setContentView(int)}
     * </p>
     *
     * @return The Layout Resource ID
     */
    protected abstract int getLayoutResource();

}
