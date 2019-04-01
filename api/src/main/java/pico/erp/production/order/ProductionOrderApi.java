package pico.erp.production.order;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

public final class ProductionOrderApi {

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
