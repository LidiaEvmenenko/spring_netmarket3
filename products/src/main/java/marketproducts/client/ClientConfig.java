package marketproducts.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class ClientConfig {

    @Bean
    public Retrofit retrofit(@Value("${marketproducts.order-service.url}") String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Bean
    public OrderClientApi orderClientApi(Retrofit retrofit) {
        return retrofit.create(OrderClientApi.class);
    }

}
