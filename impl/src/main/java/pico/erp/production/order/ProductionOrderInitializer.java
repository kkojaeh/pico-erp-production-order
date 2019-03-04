package pico.erp.production.order;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.user.group.GroupRequests;
import pico.erp.user.group.GroupService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class ProductionOrderInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  GroupService groupService;

  @Autowired
  ProductionOrderProperties properties;

  @Override
  public void initialize() {
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