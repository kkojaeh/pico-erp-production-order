package pico.erp.production.order;

import kkojaeh.spring.boot.component.Give;
import kkojaeh.spring.boot.component.SpringBootComponent;
import kkojaeh.spring.boot.component.SpringBootComponentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pico.erp.production.order.ProductionOrderApi.Roles;
import pico.erp.shared.SharedConfiguration;
import pico.erp.shared.data.Role;

@Slf4j
@SpringBootComponent("production-order")
@EntityScan
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
@Import(value = {
  SharedConfiguration.class
})
public class ProductionOrderApplication {

  public static void main(String[] args) {
    new SpringBootComponentBuilder()
      .component(ProductionOrderApplication.class)
      .run(args);
  }

  @Bean
  @Give
  public Role productionOrderAccepter() {
    return Roles.PRODUCTION_ORDER_ACCEPTER;
  }

  @Bean
  @Give
  public Role productionOrderManager() {
    return Roles.PRODUCTION_ORDER_MANAGER;
  }

  @Bean
  @Give
  public Role productionOrderer() {
    return Roles.PRODUCTION_ORDERER;
  }

}
