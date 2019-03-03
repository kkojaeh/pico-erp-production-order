package pico.erp.production.order;

import java.time.OffsetDateTime;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionOrderRepository {

  long countCreatedBetween(OffsetDateTime begin, OffsetDateTime end);

  ProductionOrder create(@NotNull ProductionOrder orderAcceptance);

  void deleteBy(@NotNull ProductionOrderId id);

  boolean exists(@NotNull ProductionOrderId id);

  Optional<ProductionOrder> findBy(@NotNull ProductionOrderId id);

  void update(@NotNull ProductionOrder orderAcceptance);

}
