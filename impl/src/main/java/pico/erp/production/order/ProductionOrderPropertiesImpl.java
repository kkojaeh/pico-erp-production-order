package pico.erp.production.order;

import kkojaeh.spring.boot.component.ComponentBean;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pico.erp.user.group.GroupData;

@ComponentBean
@Data
@Configuration
@ConfigurationProperties("production-order")
public class ProductionOrderPropertiesImpl implements ProductionOrderProperties {

  GroupData accepterGroup;

}
