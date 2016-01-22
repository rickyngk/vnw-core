package vietnamworks.com.vnwcore.entities;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import R.helper.BindField;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;
import R.helper.Common;
import R.helper.EntityX;
import R.helper.LocalStorage;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 1/15/16.
 */
public class Configuration extends EntityX {
    public static Configuration instance;
    public Configuration() {
        super();
        instance = this;
    }


    @BindField("locations")
    ArrayList<Location> locations;

    @BindField("categories")
    ArrayList<Category> categories;

    @BindField("job_levels")
    ArrayList<JobLevel> jobLevels;

    @BindField("degree")
    ArrayList<Degree> degrees;

    @BindField("benefits")
    ArrayList<Benefit> benefits;

    @BindField("frequencies")
    ArrayList<Frequency> frequencies;

    @BindField("languages")
    ArrayList<Language> languages;

    private HashMap<String, Location> locationMapping = new HashMap<>();
    private HashMap<String, Category> categoryMapping = new HashMap<>();
    private HashMap<String, JobLevel> jobLevelMapping = new HashMap<>();
    private HashMap<String, Degree> degreeMapping = new HashMap<>();
    private HashMap<String, Benefit> benefitMapping = new HashMap<>();
    private HashMap<String, Frequency> frequencyMapping = new HashMap<>();
    private HashMap<String, Language> languageMapping = new HashMap<>();

    public static void load(final Context ctx, final Callback callback) {
        JSONObject config = LocalStorage.getJSON("vnw_api_config");
        long lastUpdate = LocalStorage.getLong("vnw_api_config_last_update", 0);
        boolean loadCache = false;
        if (config != null && Common.getMillis() - lastUpdate < Common.ONE_WORKING_WEEK) {
            try {
                new Configuration();
                instance.importFromJson(config);
                callback.onCompleted(ctx, new CallbackSuccess(instance));
                loadCache = true;
            }catch (Exception E) {
                loadCache = false;
            }
        }

        if (!loadCache) {
            VNWAPI.getConfiguration(ctx, new Callback() {
                @Override
                public void onCompleted(Context context, CallbackResult result) {
                    if (result.hasError()) {
                        callback.onCompleted(ctx, new CallbackResult(result.getError()));
                    } else {
                        try {
                            new Configuration();
                            JSONObject res = (JSONObject) result.getData();
                            JSONObject data = res.optJSONObject("data");
                            instance.importFromJson(data);
                            LocalStorage.set("vnw_api_config_last_update", Common.getMillis());
                            LocalStorage.set("vnw_api_config", instance);
                            callback.onCompleted(ctx, new CallbackSuccess(instance));
                        } catch (Exception E) {
                            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackErrorInfo(-1, E.getMessage())));
                        }
                    }
                }
            });
        }
    }

    public static Location findLocation(String id) {
        if (!instance.locationMapping.containsKey(id)) {
            for (Location l : instance.locations) {
                if (l.getId().compareTo(id) == 0) {
                    instance.locationMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.locationMapping.get(id);
    }

    public static Category findCategory(String id) {
        if (!instance.categoryMapping.containsKey(id)) {
            for (Category l : instance.categories) {
                if (l.getId().compareTo(id) == 0) {
                    instance.categoryMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.categoryMapping.get(id);
    }

    public static JobLevel findJobLevel(String id) {
        if (!instance.jobLevelMapping.containsKey(id)) {
            for (JobLevel l : instance.jobLevels) {
                if (l.getId().compareTo(id) == 0) {
                    instance.jobLevelMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.jobLevelMapping.get(id);
    }

    public static Degree findDegree(String id) {
        if (!instance.degreeMapping.containsKey(id)) {
            for (Degree l : instance.degrees) {
                if (l.getId().compareTo(id) == 0) {
                    instance.degreeMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.degreeMapping.get(id);
    }

    public static Benefit findBenefit(String id) {
        if (!instance.benefitMapping.containsKey(id)) {
            for (Benefit l : instance.benefits) {
                if (l.getId().compareTo(id) == 0) {
                    instance.benefitMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.benefitMapping.get(id);
    }

    public static Frequency findFrequency(String id) {
        if (!instance.frequencyMapping.containsKey(id)) {
            for (Frequency l : instance.frequencies) {
                if (l.getId().compareTo(id) == 0) {
                    instance.frequencyMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.frequencyMapping.get(id);
    }

    public static JobLevel findLevel(String id) {
        if (!instance.jobLevelMapping.containsKey(id)) {
            for (JobLevel l : instance.jobLevels) {
                if (l.getId().compareTo(id) == 0) {
                    instance.jobLevelMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.jobLevelMapping.get(id);
    }

    public static Language findLanguage(String id) {
        if (!instance.languageMapping.containsKey(id)) {
            for (Language l : instance.languages) {
                if (l.getId().compareTo(id) == 0) {
                    instance.languageMapping.put(id, l);
                    break;
                }
            }
        }
        return instance.languageMapping.get(id);
    }
}
