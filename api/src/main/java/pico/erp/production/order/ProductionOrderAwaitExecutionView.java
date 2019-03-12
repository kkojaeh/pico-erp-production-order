package pico.erp.production.order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
public class ProductionOrderAwaitExecutionView {

  ProductionOrderId id;

  ProductionOrderCode code;

  ItemId itemId;

  ProcessId processId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  BigDecimal spareQuantity;

  UnitKind unit;

  UserId ordererId;

  ProjectId projectId;

  CompanyId supplierId;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  OffsetDateTime committedDate;

  OffsetDateTime acceptedDate;

  OffsetDateTime dueDate;

  OffsetDateTime estimatedPreparedDate;

  OffsetDateTime preparedDate;


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    CompanyId receiverId;

    UserId ordererId;

    ProjectId projectId;

    ItemId itemId;

    OffsetDateTime startDueDate;

    OffsetDateTime endDueDate;

  }

}
