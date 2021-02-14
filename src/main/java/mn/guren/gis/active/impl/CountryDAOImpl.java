package mn.guren.gis.active.impl;

import mn.guren.gis.model.DCountry;
import mn.guren.gis.model.query.QDCountry;

import java.util.List;

public class CountryDAOImpl implements CountryDAO {

    @Override
    public String list() {
        List<DCountry> countries = new QDCountry().findList();
        //TODO FIX Structured coded for list here
//        StructuredCodec<List<DCountry>> countryCodec = StructuredCodecs.ofList(countries);
        return "";
    }
}
