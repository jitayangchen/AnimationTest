package cn.com.findfine.anrtest;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Button btnAnimation;
    private ImageView btnTarget;
    private Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAnimation = findViewById(R.id.btn_animation);
        btnTarget = findViewById(R.id.btn_target);
        btnAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, AnrActivity.class));

//                app-debug.apk
//                ObjectAnimator set = (ObjectAnimator) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.scale_test);

                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.animation_demo);
                set.setTarget(btnTarget);
                set.start();


//                loadAnimRes();
            }
        });
    }

    private void loadAnimRes() {
        String animatorRes = "/mnt/sdcard/animator_xml.apk";
        loadSkinRes(this, animatorRes);
        if (mResources != null) {
            try {
                Class<?> animatorInflaterClazz = Class.forName("android.animation.AnimatorInflater");
                Method loadAnimatorMethod = animatorInflaterClazz.getMethod("loadAnimator", Resources.class, Resources.Theme.class, int.class);
                AnimatorSet set = (AnimatorSet) loadAnimatorMethod.invoke(null, mResources, getTheme(),
                        mResources.getIdentifier("animator_test", "animator", "cn.com.findfine.animator_res"));
                set.setTarget(btnTarget);
                set.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mResources, getTheme(), R.animator.animator_test);
//            set.setTarget(btnTarget);
//            set.start();
        }
    }

    public void loadSkinRes(Context context, String skinFilePath) {
        if (TextUtils.isEmpty(skinFilePath)) {
            return ;
        }
        try {
            AssetManager assetManager = createAssetManager(skinFilePath);
            mResources = createResources(context, assetManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AssetManager createAssetManager(String skinFilePath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinFilePath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Resources createResources(Context context, AssetManager assetManager) {
        Resources superRes = context.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }
}
