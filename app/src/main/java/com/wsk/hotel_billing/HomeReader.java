package com.wsk.hotel_billing;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class HomeReader {
    public static JSONArray readJson(Context context) {
        try (InputStream is = context.getAssets().open("hotels.json")) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            return new JSONArray(new String(buffer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public static JSONObject readJsonDetails(Context context, String details) {
        try (InputStream is = context.getAssets().open("hotels_details." + details + ".json")) {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            return new JSONObject(new String(buffer));
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
