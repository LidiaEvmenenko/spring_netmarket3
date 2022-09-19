package marketproducts.service;


import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import marketapi.ApiOrderItem;
import marketapi.ApiProductView;
import marketproducts.client.OrderClientApi;
import marketproducts.entity.Order;
import marketproducts.entity.OrderItem;
import marketproducts.model.ProductDto;
import marketproducts.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final OrderClientApi api;

    @KafkaListener(topics = "order-request", groupId = "order-message")
    public void onReceiveProductList(String productsJson) throws JsonProcessingException {
        List<ProductDto> products = objectMapper.readTree(productsJson, new
                TypeReference<>() {});
        Order order = new Order();
        List<OrderItem> items = products.stream()
                .map(p -> OrderItem.builder()
                .order(order)
                .name(p.getTitle())
                .itemPrice(p.getPrice())
                .count(1)
                .build())
                .collect(Collectors.toList());
        order.setItems(items);
        order.setPrice(items.stream().map(OrderItem::getItemPrice).reduce(0. ,Double::sum));
        Order save = orderRepository.save(order);
        kafkaTemplate.send("order-response", String.valueOf(save.getId()));
    }
    public ApiOrderItem sendProductsToOrder(List<ProductDto> products) {
        List<ApiProductView> views = products.stream()
                .map(p -> new ApiProductView(p.getTitle(), p.getPrice()))
                .toList();

        return execute(api.create(views));
    }


}























