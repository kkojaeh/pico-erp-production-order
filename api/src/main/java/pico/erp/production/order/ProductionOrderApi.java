package pico.erp.production.order;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.data.Role;

public final class ProductionOrderApi {

  public final static ApplicationId ID = ApplicationId.from("production-order");

  @RequiredArgsConstructor
  public enum Roles implements Role {

    PRODUCTION_ORDERER,
    PRODUCTION_ORDER_ACCEPTER,
    PRODUCTION_ORDER_MANAGER;

    @Id
    @Getter
    private final String id = name();

  }
}
