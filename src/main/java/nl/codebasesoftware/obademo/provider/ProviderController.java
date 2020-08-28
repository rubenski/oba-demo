package nl.codebasesoftware.obademo.provider;


import com.obaccelerator.sdk.Oba;
import com.obaccelerator.sdk.countrydataprovider.CountryDataProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProviderController {

    private final Oba oba;

    public ProviderController(Oba oba) {
        this.oba = oba;
    }

    @GetMapping("/providers")
    public List<CountryDataProvider> findProviders() {
        return oba.findCountryDataProviders();
    }

    @GetMapping("/providers/{systemName}")
    public CountryDataProvider findProvider(@PathVariable String systemName) {
        return oba.findCountryDataProvider(systemName);
    }
}
