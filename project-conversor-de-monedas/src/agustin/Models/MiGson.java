package agustin.Models;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MiGson {
    private Gson gson = new GsonBuilder().
            setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
            setPrettyPrinting().
            create();

    public ConversorAPI ExtraerDatosConversion(String json){
        return gson.fromJson(json, ConversorAPI.class);
    }

}
