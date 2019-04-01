package pico.erp.production.order;

import kkojaeh.spring.boot.component.SpringBootComponentReadyEvent;
import kkojaeh.spring.boot.component.Take;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import pico.erp.user.group.GroupRequests;
import pico.erp.user.group.GroupService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class ProductionOrderInitializer implements
  ApplicationListener<SpringBootComponentReadyEvent> {

  @Take
  GroupService groupService;

  @Autowired
  ProductionOrderProperties properties;

  @Override
  public void onApplicationEvent(SpringBootComponentReadyEvent event) {
    val accepterGroup = properties.getAccepterGroup();
    if (!groupService.exists(accepterGroup.getId())) {
      groupService.create(
        GroupRequests.CreateRequest.builder()
          .id(accepterGroup.getId())
          .name(accepterGroup.getName())
          .build()
      );
    }
  }
}
