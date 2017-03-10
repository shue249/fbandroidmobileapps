package shueching.mobileapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import shueching.mobileapp.dataobject.ProductLineItem;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shueching on 1/15/17.
 */
public class FacebookAppEventsController {
    private static final String TAG = FacebookAppEventsController.class.getName();

    private static FacebookAppEventsController facebookAppEventsController = null;

    private AppEventsLogger logger;

    private FacebookAppEventsController(Context context) {
    }

    public static FacebookAppEventsController getInstance(Context context) {
        if (facebookAppEventsController == null) {
            long start = System.currentTimeMillis();
            FacebookSdk.sdkInitialize(context.getApplicationContext());
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
            long end = System.currentTimeMillis();
            Log.d(TAG, "SDK Load time = "+(end-start));

            facebookAppEventsController = new FacebookAppEventsController(context);
        }
        facebookAppEventsController.createLogger(context);
        return facebookAppEventsController;
    }

    public void activateApp(Context context) {
        AppEventsLogger.activateApp(context);
    }

    private void createLogger(Context context) {
        logger = AppEventsLogger.newLogger(context);
    }

    public void logViewContent(ProductLineItem productLineItem) {
        List<ProductLineItem> productLineItems = new ArrayList<>();
        productLineItems.add(productLineItem);
        log(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, productLineItems);
    }

    public void logAddToCart(ProductLineItem productLineItem) {
        List<ProductLineItem> productLineItems = new ArrayList<>();
        productLineItems.add(productLineItem);
        log(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, productLineItems);
    }

    public void logPurchase(ProductLineItem productLineItem) {
        List<ProductLineItem> productLineItems = new ArrayList<>();
        productLineItems.add(productLineItem);
        log(AppEventsConstants.EVENT_NAME_PURCHASED, productLineItems);
    }

    public void logViewContent(List<ProductLineItem> productLineItems) {
        log(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, productLineItems);
    }

    public void logAddToCart(List<ProductLineItem> productLineItems) {
        log(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, productLineItems);
    }

    public void logPurchase(List<ProductLineItem> productLineItems) {
        log(AppEventsConstants.EVENT_NAME_PURCHASED, productLineItems);
    }

    public void logViewCategory(List<ProductLineItem> productLineItems) {
        log("ViewCategory", productLineItems);
    }

    private void log(String eventName, List<ProductLineItem> productLineItems) {
        if (productLineItems.size() == 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        double value = productLineItems.get(0).qty * productLineItems.get(0).product.price;
        sb.append("'" + productLineItems.get(0).product.id + "'");
        for (int i = 1; i < productLineItems.size(); i++) {
            value += productLineItems.get(i).qty * productLineItems.get(i).product.price;
            sb.append(",'" + productLineItems.get(i).product.id + "'");
        }
        Bundle parameters = new Bundle();
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, "[" + sb.toString() + "]");
        parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD");
        logger.logEvent(eventName, value, parameters);
    }

    public void logCustom(String eventName, double value, Bundle parameters) {
        logger.logEvent(eventName, value, parameters);
    }

    public void getDeferredDeeplink(Context context, AppLinkData.CompletionHandler completionHandler) {
        AppLinkData.fetchDeferredAppLinkData(context, completionHandler);
    }

    public String getIdFromURI(Uri data) {
        if (data == null) {
            return null;
        }
        return data.getQueryParameter("id");
    }
}
