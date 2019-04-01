package pico.erp.production.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.company.CompanyId;
import pico.erp.item.ItemId;
import pico.erp.item.spec.ItemSpecCode;
import pico.erp.process.ProcessId;
import pico.erp.project.ProjectId;
import pico.erp.shared.data.UnitKind;
import pico.erp.user.UserId;
import pico.erp.warehouse.location.site.SiteId;
import pico.erp.warehouse.location.station.StationId;

@Data
public class ProductionOrderView {

  ProductionOrderId id;

  ProductionOrderCode code;

  ItemId itemId;

  ProcessId processId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  BigDecimal spareQuantity;

  BigDecimal progressedQuantity;

  BigDecimal erroredQuantity;

  UnitKind unit;

  UserId ordererId;

  UserId accepterId;

  ProjectId projectId;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  LocalDateTime dueDate;

  LocalDateTime committedDate;

  LocalDateTime completedDate;

  LocalDateTime acceptedDate;

  LocalDateTime rejectedDate;

  LocalDateTime canceledDate;

  ProductionOrderStatusKind status;

  LocalDateTime estimatedPreparedDate;

  LocalDateTime preparedDate;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    String code;

    CompanyId receiverId;

    UserId ordererId;

    UserId accepterId;

    ProjectId projectId;

    ItemId itemId;

    Set<ProductionOrderStatusKind> statuses;

    LocalDateTime startDueDate;

    LocalDateTime endDueDate;

  }

}
