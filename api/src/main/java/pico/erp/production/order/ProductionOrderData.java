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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductionOrderData {

  ProductionOrderId id;

  ProductionOrderCode code;

  ItemId itemId;

  ProcessId processId;

  ItemSpecCode itemSpecCode;

  BigDecimal quantity;

  BigDecimal spareQuantity;

  UnitKind unit;

  UserId ordererId;

  UserId accepterId;

  ProjectId projectId;

  String rejectedReason;

  OffsetDateTime dueDate;

  CompanyId receiverId;

  SiteId receiveSiteId;

  StationId receiveStationId;

  OffsetDateTime committedDate;

  OffsetDateTime completedDate;

  OffsetDateTime acceptedDate;

  OffsetDateTime rejectedDate;

  OffsetDateTime canceledDate;

  ProductionOrderStatusKind status;

  String remark;

  boolean cancelable;

  boolean completable;

  boolean acceptable;

  boolean progressable;

  boolean rejectable;

  boolean committable;

  boolean updatable;

  boolean plannable;

  boolean progressCancelable;

}
