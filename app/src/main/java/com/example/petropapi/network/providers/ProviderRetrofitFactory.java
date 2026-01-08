package com.example.petropapi.network.providers;

import com.example.petropapi.data.ApiKeyProvider;
import com.example.petropapi.network.providers.ev.ChargeHubService;
import com.example.petropapi.network.providers.ev.HereChargePointsService;
import com.example.petropapi.network.providers.ev.MapboxChargeFinderService;
import com.example.petropapi.network.providers.ev.NrelAltFuelService;
import com.example.petropapi.network.providers.ev.OpenChargeMapService;
import com.example.petropapi.network.providers.ev.PlugShareService;
import com.example.petropapi.network.providers.ev.SmartcarService;
import com.example.petropapi.network.providers.ev.TomTomEvService;
import com.example.petropapi.network.providers.fuel.ApifyGasBuddyService;
import com.example.petropapi.network.providers.fuel.CollectApiFuelService;
import com.example.petropapi.network.providers.fuel.EiaFuelPriceService;
import com.example.petropapi.network.providers.fuel.EuWeeklyOilBulletinService;
import com.example.petropapi.network.providers.fuel.TomTomFuelPriceService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ProviderRetrofitFactory {
    private ProviderRetrofitFactory() {
    }

    private static Retrofit build(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NrelAltFuelService createNrelService() {
        return build("https://developer.nrel.gov/api/").create(NrelAltFuelService.class);
    }

    public static OpenChargeMapService createOpenChargeMapService() {
        return build("https://api.openchargemap.io/v3/").create(OpenChargeMapService.class);
    }

    public static TomTomEvService createTomTomEvService() {
        return build("https://api.tomtom.com/").create(TomTomEvService.class);
    }

    public static TomTomFuelPriceService createTomTomFuelService() {
        return build("https://api.tomtom.com/").create(TomTomFuelPriceService.class);
    }

    public static SmartcarService createSmartcarService() {
        return build("https://api.smartcar.com/v2.0/").create(SmartcarService.class);
    }

    public static PlugShareService createPlugShareService() {
        return build("https://api.plugshare.com/").create(PlugShareService.class);
    }

    public static HereChargePointsService createHereChargePointsService() {
        return build("https://chargepoints.search.hereapi.com/").create(HereChargePointsService.class);
    }

    public static ChargeHubService createChargeHubService() {
        return build("https://api.chargehub.com/").create(ChargeHubService.class);
    }

    public static MapboxChargeFinderService createMapboxChargeFinderService() {
        return build("https://api.mapbox.com/").create(MapboxChargeFinderService.class);
    }

    public static EiaFuelPriceService createEiaFuelPriceService() {
        return build("https://api.eia.gov/").create(EiaFuelPriceService.class);
    }

    public static CollectApiFuelService createCollectApiFuelService() {
        return build("https://api.collectapi.com/").create(CollectApiFuelService.class);
    }

    public static ApifyGasBuddyService createApifyGasBuddyService() {
        return build("https://api.apify.com/").create(ApifyGasBuddyService.class);
    }

    public static EuWeeklyOilBulletinService createEuWeeklyOilBulletinService() {
        return build("https://energy.ec.europa.eu/").create(EuWeeklyOilBulletinService.class);
    }

    public static String getNrelApiKey() {
        return ApiKeyProvider.getNrelApiKey();
    }

    public static String getOpenChargeMapApiKey() {
        return ApiKeyProvider.getOpenChargeMapApiKey();
    }

    public static String getTomTomApiKey() {
        return ApiKeyProvider.getTomTomApiKey();
    }

    public static String getSmartcarClientId() {
        return ApiKeyProvider.getSmartcarClientId();
    }

    public static String getSmartcarClientSecret() {
        return ApiKeyProvider.getSmartcarClientSecret();
    }

    public static String getHereApiKey() {
        return ApiKeyProvider.getHereApiKey();
    }

    public static String getChargeHubApiKey() {
        return ApiKeyProvider.getChargeHubApiKey();
    }

    public static String getMapboxApiKey() {
        return ApiKeyProvider.getMapboxApiKey();
    }

    public static String getEiaApiKey() {
        return ApiKeyProvider.getEiaApiKey();
    }

    public static String getCollectApiKey() {
        return ApiKeyProvider.getCollectApiKey();
    }

    public static String getApifyApiKey() {
        return ApiKeyProvider.getApifyApiKey();
    }
}
