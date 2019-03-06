package pico.erp.production.order;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductionOrderQuery {

  Page<ProductionOrderView> retrieve(@NotNull ProductionOrderView.Filter filter,
    @NotNull Pageable pageable);

  Page<ProductionOrderAwaitExecutionView> retrieve(@NotNull ProductionOrderAwaitExecutionView.Filter filter,
    @NotNull Pageable pageable);

  Page<ProductionOrderAwaitAcceptView> retrieve(
    @NotNull ProductionOrderAwaitAcceptView.Filter filter, @NotNull Pageable pageable);


}
